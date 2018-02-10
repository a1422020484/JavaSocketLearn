package nettyServer.system.dao;


/**
 * 
 * @author 王亮亮   2016.12
 *
 * @param <M>
 */
public abstract class AbstractEntity<M> extends Entity{
	
	public abstract M getEntity() throws Exception;

}
