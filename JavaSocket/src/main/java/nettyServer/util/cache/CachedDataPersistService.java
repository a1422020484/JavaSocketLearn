package nettyServer.util.cache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import nettyServer.listener.ServerCloseListener;
import nettyServer.listener.ServerStartupListener;
import nettyServer.util.CoreConfig;
import nettyServer.util.DateUtil;
import nettyServer.util.GameConfig;
import nettyServer.util.GameExplorer;
import nettyServer.util.GameThreadFactory;
import nettyServer.util.GameUtils;

/**
 * 缓存数据持久化服务
 * 
 * @author yangxp
 */
@Component
public class CachedDataPersistService implements ServerCloseListener, ServerStartupListener {
	
	static final Logger log = LoggerFactory.getLogger("cache-bak");
	
	static AtomicBoolean saveFlag = new AtomicBoolean(false);
	
	private static LinkedHashMap<Integer, Entities> unsavePEQueue = new LinkedHashMap<>();
	
	/**
	 * @param en
	 */
	static void addToUnsaveQueue(Entities en) {
//		System.out.println("添加到未处理队列:" + en.getPlayerId());
		synchronized(unsavePEQueue) {
			unsavePEQueue.put(en.getPlayerId(), en);
		}
	}
	
	/**
	 * 从未保存的实体队列中拿出
	 * @param playerId
	 * @return 
	 */
	static Entities takeFromUnsaveQueue(int playerId) {
		synchronized(unsavePEQueue) {
			Entities en = unsavePEQueue.remove(playerId);
//			if (en != null) {
//				System.out.println("从未处理队列拿出:" + playerId);
//			}
			return en;
		}
	}
	
	/**
	 * 在执行保存后,检查未处理队列,将队列中没有变化的数据删除
	 */
	static void checkAndCleanUnsaveQueue() {
		synchronized(unsavePEQueue) {
			Iterator<Entry<Integer, Entities>> iter = unsavePEQueue.entrySet().iterator();
			while (iter.hasNext()) {
				Entities en = iter.next().getValue();
				boolean isChanged = false;
				for (CachedDataManager<?> manager : CachedDataManagers.sortedList) {
					PersistEntity pe = en.getEntityFromCache(manager.getParameterizedType());
					if (pe != null && pe.isChanged()) {
						isChanged = true;
						break;
					}
				}
				if (!isChanged) {
					// 没有实体改变,从队列删除
					iter.remove();
//					System.out.println("从未处理队列删除:" + en.getPlayerId());
				}
			}
		}
	}
	
	/**
	 * 一次保存单个人,适合在CachedDataPersistStrategy.strategy == 1时使用(测试时)
	 * 不管这个玩家是否存在,unsavePEQueue中的缓存数据都会保存
	 * @param playerId
	 */
	public static void saveSingle(int playerId) {
		List<Entities> tList = null;
		synchronized (unsavePEQueue) {
			int size = unsavePEQueue.size();
			tList = new ArrayList<>(size + 1);
			if (size > 0) {
				tList.addAll(unsavePEQueue.values());
			}
		}
		Entities en = GameExplorer.getEntitiesIfExisting(playerId);
		if (en != null) {
			tList.add(en);
		}
		doSave(tList, false);
		checkAndCleanUnsaveQueue();
	}
	
	/**
	 * 直接持久化所有已改变的数据
	 * @param errorToFile 保存失败的数据是否写入到备份文件
	 */
	public static void save(boolean errorToFile) {
		saveFlag.set(true);
		GameUtils.RuntimeLog.info("开始执行数据持久化.");
		try {
			int batchSizeN = GameConfig.intVal("DB_BATCH_SIZE_N");
			long startTime = System.currentTimeMillis();
			List<Entities> all = new ArrayList<>(CoreConfig.intValue("CachedPlayerNums") + 512);
			synchronized (unsavePEQueue) {
				all.addAll(unsavePEQueue.values());
			}
			all.addAll(GameExplorer.getAllEntities());
			List<Entities> tList = new ArrayList<>(batchSizeN);
			for (int i = 0; i < all.size(); i ++) {
				tList.add(all.get(i));
				if (tList.size() == batchSizeN || (i == all.size() - 1 && tList.size() > 0)) {
					doSave(tList, errorToFile);
					tList.clear();
				}
			}
			long endTime = System.currentTimeMillis();
			GameUtils.RuntimeLog.info("数据持久化完成,用时{}ms", (endTime - startTime));
			checkAndCleanUnsaveQueue();
		} finally {
			saveFlag.set(false);
		}
	}
	
	private static ExecutorService saveServices= null;
	private static ExecutorCompletionService<Void> completionService = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void doSave(List<Entities> entities, boolean errorToFile) {
		/*
		 * 多线程执行保存,等待这批任务全部完成才返回
		 */
		int n = 0;
		for (CachedDataManager manager : CachedDataManagers.sortedList) {
			// 已改变的实体列表
			List<PersistEntity> typeEntityChangedCloneList = new ArrayList<>(entities.size());
			for (Entities en : entities) {
				PersistEntity clone = en.getChangedEntityClone(manager.getParameterizedType());
				if (clone != null) {
					typeEntityChangedCloneList.add(clone);
				}
			}
			if (typeEntityChangedCloneList.size() == 0) {
				continue;
			}
			completionService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						manager.updateBatch(typeEntityChangedCloneList, false); // do Save
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (errorToFile) {
						List<PersistEntity> errorList = null;
						for (PersistEntity peClone : typeEntityChangedCloneList) {
							/*
							 * 一次传给updateBatch实现者的一批数据允许部分成功部分失败的情况,
							 * 所以需要在updateBatch中自己标记saveFlag
							 */
							if (peClone.saveFlag == 0) {
								if (errorList == null) {
									errorList = new ArrayList<>();
								}
								errorList.add(peClone);
								log.info("{}|{}", peClone.actualClass().getName(), JSON.toJSONString(peClone));
							}
						}
						if (errorList != null) {
							// 保存到文件
							toFile(errorList);
						}
					}
				}
			}, null);
			n ++;
		}
		// 等待所有任务完成
		for (int i = 0; i < n; i ++) {
			try {
				completionService.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void toFile(List<PersistEntity> list) {
		synchronized (cacheBakFile) {
			try {
				checkFile();
				String dateStr = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
				FileWriter fw = new FileWriter(cacheBakFile, true);
				BufferedWriter writer = new BufferedWriter(fw);
				for (PersistEntity pe : list) {
					StringBuilder sb = new StringBuilder(256);
					sb.append(dateStr).append("|");
					sb.append(pe.actualClass().getName()).append("|");
					if (pe instanceof CustomRestoreCompositePersistEntity) {
						String backupStr = ((CustomRestoreCompositePersistEntity<?>) pe).toBackupString();
						if (backupStr != null) {
							sb.append(backupStr);
						} else {
							sb.append("");
						}
					} else {
						sb.append(JSON.toJSONString(pe));
					}
					writer.write(sb.toString());
					writer.newLine();
				}
				
				writer.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void fromFile() throws Exception {
		synchronized (cacheBakFile) {
			if (!cacheBakFile.exists()) {
				return;
			}
			FileReader fr = null;
			BufferedReader reader = null;
			List<String> allLine = new ArrayList<>(10240);
			try {
				fr = new FileReader(cacheBakFile);
				reader = new BufferedReader(fr);
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (StringUtils.isBlank(line)) {
						continue; // 可能是空行
					}
					allLine.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
					fr.close();
				} catch (IOException ignore) {
				}
			}
			// 解析数据
			Map<Class<?>, List<PersistEntity>> map = new HashMap<>();
			for (String line : allLine) {
				// 格式:时间戳|类完整名|具体数据(json)
				int begin = 0;
				int end = line.indexOf('|');
				// yyyy-MM-dd HH:mm:ss
				@SuppressWarnings("unused")
				String dateStr = line.substring(begin, end);
				begin = end + 1;
				end = line.indexOf('|', begin);
				String classStr = line.substring(begin, end);
				begin = end + 1;
				String backupStr = line.substring(begin);
				Class<PersistEntity> clazz = null;
				try {
					clazz = (Class<PersistEntity>) Class.forName(classStr);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new Error("persist entity class not found: " + classStr);
				}
				PersistEntity en = null;
				if (CustomRestoreCompositePersistEntity.class.isAssignableFrom(clazz)) {
					try {
						CustomRestoreCompositePersistEntity crpe = (CustomRestoreCompositePersistEntity) clazz.newInstance();
						crpe.restoreFromString(backupStr);
						en = crpe;
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					en = JSON.parseObject(backupStr, clazz);
				}
				List<PersistEntity> l = map.get(clazz);
				if (l == null) {
					l = new ArrayList<>(256);
					map.put(clazz, l);
				}
				l.add(en);
			}
			allLine.clear();
			// 保存数据
			for (Map.Entry<Class<?>, List<PersistEntity>> en : map.entrySet()) {
				CachedDataManager manager = CachedDataManagers.managersMap.get(en.getKey());
				if (manager == null) {
					throw new Error("entity manager not fount: " + en.getKey().toString());
				}
				List<PersistEntity> pelist = en.getValue();
				manager.updateBatch(pelist, true);
				// 检查pelist是否全部写入成功
                List<PersistEntity> errList = new ArrayList<>();
                for (PersistEntity pe : pelist) {
					if (pe.saveFlag == 0) {
                        errList.add(pe);
                    }
				}
                if (errList.size() > 0) {
                    StringBuilder output = new StringBuilder(1024 * 1024);
                    for (PersistEntity pe : errList) {
                        output.append(pe.toString());
                        output.append("\r\n");
                    }
                    GameUtils.RuntimeLog.error("恢复数据异常:\r\n{}", JSON.toJSONString(output));
                    throw new RuntimeException("persist entity update failure: " + en.getKey().getName());
                }
            }
			try {
				cacheBakFile.renameTo(new File(cacheBakFile.getParentFile(), cacheBakFileName + DateUtil.format(new Date(), "yyyyMMddHHmmss") + cacheBakFileSubfix));
			} catch (Exception e) {
			}
//			cacheBakFile.delete();
		}
	}

	private static void checkFile() {
		if (!cacheBakFile.exists()) {
			try {
				cacheBakFile.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("创建文件失败:" + cacheBakFile.getPath(), e);
			}
		} else {
			if (cacheBakFile.isDirectory()) {
				throw new RuntimeException("创建文件失败:" + cacheBakFile.getPath());
			}
		}
	}

	@Override
	public void close() {
		while (saveFlag.get()) {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			GameUtils.RuntimeLog.info("将要执行关服保存,正在等待上一次save()执行完成!");
		}
		save(true);
	}
	
	@Override
	public int invokeStrategy() {
		return 2;
	}
	
	@Override
	public void started() {
		// LogHome 配置在logback.xml中
		String logHome = System.getProperty("LogHome");
		if (StringUtils.isBlank(logHome)) {
			throw new RuntimeException("没有找到系统属性'LogHome',请检查logback.xml中的'LogHome'配置!");
		}
		if (!logHome.endsWith(File.separator)) {
			logHome += File.separator;
		}
		cacheBakFile = new File(logHome + cacheBakFileName + cacheBakFileSubfix);
		try {
			fromFile();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("从备份文件恢复数据失败! " + cacheBakFile.getAbsolutePath());
			System.exit(1);
		}
		saveServices = Executors.newFixedThreadPool(
				GameConfig.intVal("DB_BATCH_UPDATE_THREAD_SIZE"), new GameThreadFactory("Cached data persistent"));
		completionService = new ExecutorCompletionService<>(saveServices);
	}
	
	static String cacheBakFileName = "cachedata";
	static String cacheBakFileSubfix = ".txt";
	static File cacheBakFile;
}
