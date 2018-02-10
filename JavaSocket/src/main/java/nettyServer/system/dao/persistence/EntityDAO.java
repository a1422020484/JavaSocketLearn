package nettyServer.system.dao.persistence;

import java.sql.Connection;

public abstract class EntityDAO {

	protected final Connection getConnection() {
		return ConnectionManager.getConnection();
	}
	
}
