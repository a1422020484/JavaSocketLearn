package nettyServer.util.cache;

import org.springframework.stereotype.Component;

import nettyServer.dispatch.ActionFilter;
import nettyServer.dispatch.Request;

/**
 * 缓存数据持久化过滤器
 * 
 * @author xiezuojie
 */
@Component
public class CachedDataPersistActionFilter implements ActionFilter {
	
	@Override
	public void before(Request req) {
		
	}

	@Override
	public void after(Integer playerId, Request req, Object resp) {
		if (CachedDataPersistStrategy.strategy == 1) {
			Integer pId = playerId;
			if (pId != null) {
				CachedDataPersistService.saveSingle(pId);
			}
		}
	}

}
