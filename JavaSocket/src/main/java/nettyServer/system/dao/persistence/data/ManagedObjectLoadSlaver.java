/**
 * 
 */
package nettyServer.system.dao.persistence.data;

import java.util.Collection;


public interface ManagedObjectLoadSlaver<T extends ManagedObject> {
	public Collection<T> load();
}
