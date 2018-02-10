/**
 * 
 */
package nettyServer.system.dao.persistence.data;

import java.util.concurrent.Future;

import nettyServer.system.dao.persistence.data.ObjectManager.SaveMode;



public interface SaveableObjectSaveSlaver<T extends SaveableObject> extends ManagedObjectLoadSlaver<T>{
	public Future<ObjectManager.SaveState> save(SaveMode saveMode, T... saveableObjects) throws Exception;
	
	public SaveJob<T> drainAndSaveCurrentSaveableObjects();
}
