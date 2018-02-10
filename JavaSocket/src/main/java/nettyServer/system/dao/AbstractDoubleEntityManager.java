package nettyServer.system.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nettyServer.system.dao.commonDao.PersistenceDAO;
import nettyServer.util.Constant;

/**
 * 
 * @author 王亮亮   2017.5
 *
 * @param <T>
 * @param <D>
 */
public abstract class AbstractDoubleEntityManager <T, D extends AbstractDoubleEntity<?, ?>> extends EntityManager{
	
	private static Logger logger = LogManager.getLogger(AbstractTwoKeyEntityManager.class);
	//缓存
	private final ConcurrentMap<T, D> doubleMap = new ConcurrentHashMap<T, D>();
	//需要被删除的缓存
	private final ConcurrentMap<T, D> deleteDoubleMap = new ConcurrentHashMap<T, D>();
	
	public ConcurrentMap<T, D> getMap() {
		return doubleMap;
	}
	
	public void setDeleteT(T t){
		D d = doubleMap.remove(t);
		if(d != null){
			deleteDoubleMap.put(t, d);
		}
	}
	
	protected abstract D get(T t);
	
	public final D getValue(T t){
		D d = doubleMap.get(t);
		if(d == null){
			d = get(t);
			if(d == null){
				logger.error("[AbstractTwoKeyEntityManager] get(t) null t=" + t);
				return null;
			}
			doubleMap.putIfAbsent(t, d);
		}
		d.setLastSelectTime((int)(System.currentTimeMillis()/1000L));
		return d;
	}
	
	/**
	 * 删除数据
	 */
	private void deleteD(){
		List<Object> deleteList = new ArrayList<Object>();
		//检测要被整个删除的D
		if(!deleteDoubleMap.isEmpty()){
			for(ConcurrentMap.Entry<T, D> entityT:deleteDoubleMap.entrySet()){
				entityT.getValue().getAllDelete(deleteList);
			}
		}
		//检测D中需要被删除的部分
		if(!doubleMap.isEmpty()){
			for(ConcurrentMap.Entry<T, D> entityT:doubleMap.entrySet()){
				entityT.getValue().getDelete(deleteList);
			}
		}
		try{
			PersistenceDAO.getInstantce().delete(deleteList);
		} catch (Exception e){
			logger.error(e.getMessage(),e);
			//数据删除失败,需要恢复未被删除的脏数据,等待下次再次执行
			if(!doubleMap.isEmpty()){
				for(ConcurrentMap.Entry<T, D> entityT:doubleMap.entrySet()){
					entityT.getValue().recoverDelete();
				}
			}
			return;
		}
		//如果删除失败则不需要清空deleteDoubleMap,等待下次执行
		deleteDoubleMap.clear();
	}
	
	/**
	 * 删除数据
	 */
	private void deleteD(T t){
		List<Object> deleteList = new ArrayList<Object>();
		D deleteD = deleteDoubleMap.get(t);
		if(deleteD != null){
			//检测要被整个删除的D
			deleteD.getAllDelete(deleteList);
		}
		//检测D中需要被删除的部分
		D d = doubleMap.get(t);
		if(d != null){
			d.getDelete(deleteList);
		}
		try{
			PersistenceDAO.getInstantce().delete(deleteList);
		} catch (Exception e){
			logger.error(e.getMessage(),e);
			//数据删除失败,需要恢复未被删除的脏数据,等待下次再次执行
			if(d != null){
				d.recoverDelete();
			}
			return;
		}
		//如果删除失败则不需要清空deleteDoubleMap,等待下次执行
		deleteDoubleMap.remove(t);
	}
	
	/**
	 * 保存数据
	 */
	private void saveD(T t){
		D d = doubleMap.get(t);
		if(d == null){
			return;
		}
		List<Object> notifyList = new ArrayList<Object>();
		d.getNotifyData(notifyList);
		try {
			PersistenceDAO.getInstantce().margeData(notifyList);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			//如果sql执行失败则由try catch处理,重新把这部分数据状态改为notify状态
			d.recoverNotifyData();
		}
	}
	
	/**
	 * 保存数据
	 */
	private void saveD(){
		if(!doubleMap.isEmpty()){
			List<Object> notifyList = new ArrayList<Object>();
			for(ConcurrentMap.Entry<T, D> entityT:doubleMap.entrySet()){
				entityT.getValue().getNotifyData(notifyList);
			}
			try {
				PersistenceDAO.getInstantce().margeData(notifyList);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				//如果sql执行失败则由try catch处理,重新把这部分数据状态改为notify状态
				for(ConcurrentMap.Entry<T, D> entityT:doubleMap.entrySet()){
					entityT.getValue().recoverNotifyData();
				}
			}
		}
	}
	
	/**
	 * 检测过期数据
	 */
	private void checkOverdueD(T t){
		D d = doubleMap.get(t);
		if(d == null){
			return;
		}
		ReadWriteLock rwlock = d.getRwlock();
		rwlock.writeLock().lock();
		try {
			if(d.getLastSelectTime() != 0 && System.currentTimeMillis()/1000 - d.getLastSelectTime() >= Constant.CACHE_CONTINUE_TIME){//超过CACHE_CONTINUE_TIME时间,则把该对象删除
				doubleMap.remove(t);
			}
		} finally {
			rwlock.writeLock().unlock();
		}
	}
	
	/**
	 * 检测过期数据
	 */
	private void checkOverdueD(){
		if(!doubleMap.isEmpty()){
			for(ConcurrentMap.Entry<T, D> entityT:doubleMap.entrySet()){
				ReadWriteLock rwlock = entityT.getValue().getRwlock();
				rwlock.writeLock().lock();
				try {
					if(entityT.getValue().getLastSelectTime() != 0 && System.currentTimeMillis()/1000 - entityT.getValue().getLastSelectTime() >= Constant.CACHE_CONTINUE_TIME){//超过CACHE_CONTINUE_TIME时间,则把该对象删除
						doubleMap.remove(entityT.getKey());
					}
				} finally {
					rwlock.writeLock().unlock();
				}
			}
		}
	}
	
	public void save(T t){
		try {
			deleteD(t);
			saveD(t);
			checkOverdueD(t);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void save() throws Exception{
		synchronized (this) {
			deleteD();
			saveD();
			checkOverdueD();
		}
	}
	
	@Override
	public void save(ExecutorService executorService) throws Exception{//保存数据时用一个单独线程去跑,防止前一个数据的保存影响到后一个
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					save();
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
		});
	}
	
}
