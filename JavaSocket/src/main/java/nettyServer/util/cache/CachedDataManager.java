package nettyServer.util.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nettyServer.util.GameConfig;
import nettyServer.util.GameUtils;

/**
 * 缓存实体管理类
 * 
 * @author xiezuojie
 */
public abstract class CachedDataManager<T extends PersistEntity> {
	
	/**
	 * 加载数据到缓存
	 * @param playerId
	 */
	T initCache(int playerId) {
		final T t = getFromDB(playerId);
		return t;
	}
	
	protected abstract T getFromDB(int playerId);
	
//	/**
//	 * 如果是新数据,应执行插入新数据
//	 * <br>
//	 * 如果是旧数据并有改变,应执行更新数据
//	 * <br>
//	 * 将使用{@link #update(List)}
//	 * 
//	 * @param data
//	 */
//	@Deprecated
//	public abstract void update(T data);
	
	/**
	 * 批量保存实体
	 * 一次保存实体的一部分,这部分的大小由{@link #getBatchSize()}定义
	 * @param partList
	 */
	protected abstract void updatePartOfBatch(List<T> partList);
	
	/**
	 * 批量保存实体
	 * <br>
	 * 设提交数据到数据库的packet最大为8M,根本每个实体的大小计算出每次提交的数据条数(范围),提交后记录提交标记(成功/失败)
	 * @see PersistEntity#saveFlag
	 * @param dataList 克隆的实体列表,获取克隆源:{@link PersistEntity#cloneSource}
	 * @param ignoreCloneSource 是否忽略克隆源,忽略克隆源也表示这个克隆源可能不存在(从备份文件恢复)
	 * @throws Exception
	 */
	protected void updateBatch(List<T> dataList, boolean ignoreCloneSource) throws Exception {
		int batchSize = getBatchSize();
		List<T> tList = new ArrayList<>(batchSize);
		for (int i = 0; i < dataList.size(); i ++) {
			tList.add(dataList.get(i));
			if (tList.size() == batchSize || (i == dataList.size() - 1 && tList.size() > 0)) {
				try {
					updatePartOfBatch(tList);
					// 如果发生异常,不改变保存标记
					for (T t2 : tList) {
						t2.saveFlag = 1;
						if (!ignoreCloneSource) {
							PersistEntity sourcePE = t2.cloneSource;
							if (sourcePE != null) {
								sourcePE.sav = t2.mod;
							} else {
								GameUtils.RuntimeLog.debug("CachedDataManager.updateBatch>克隆的实体没有指定克隆源,{}", t2.getClass());
								continue;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				tList.clear();
			}
		}
	}
	
	/**
	 * @return 单次批量保存的数量,默认为Config.properties#DB_BATCH_SIZE_N
	 */
	protected int getBatchSize() {
		return GameConfig.intVal("DB_BATCH_SIZE_N");
	}
	
	/**
	 * @return 执行优先级,数值越小越优先,数值相同时以类名自然顺序排序,默认是10000.
	 */
	protected int getPriority() {
		return 10000;
	}
	
	Class<T> parameterizedType;
	
	/**
	 * @return 泛型类型
	 */
	@SuppressWarnings("unchecked")
	Class<T> getParameterizedType() {
		if (parameterizedType == null) {
			Type genType = this.getClass().getGenericSuperclass();
			Type[] types = ((ParameterizedType) genType).getActualTypeArguments();
			parameterizedType =  (Class<T>) types[0];
		}
		return parameterizedType;
	}
}
