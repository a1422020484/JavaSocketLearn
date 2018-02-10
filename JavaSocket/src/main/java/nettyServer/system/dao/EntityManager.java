package nettyServer.system.dao;

import java.util.concurrent.ExecutorService;

/**
 * 
 * @author 王亮亮   2016.12
 *
 */
public abstract class EntityManager {
	
	public abstract void save(ExecutorService executorService) throws Exception;

	public void shutDownSave(ExecutorService executorService) throws Exception{
		save(executorService);
	}
	
}
