package nettyServer.templet.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import nettyServer.templet.dao.TempletDAO;
import nettyServer.templet.entity.Templet;
import nettyServer.util.cache.CachedDataManager;


@Component
public class TempletManager extends CachedDataManager<Templet> {

	@Resource
	TempletDAO templetDao;
	
	
	@Override
	protected Templet getFromDB(int playerId) {
		Templet templet = templetDao.get(playerId);
		if(templet == null) {
			templet = new Templet(playerId);
		}
		return templet;
	}

	@Override
	protected void updatePartOfBatch(List<Templet> partList) {
		templetDao.insertOrUpdate(partList);
	}

}
