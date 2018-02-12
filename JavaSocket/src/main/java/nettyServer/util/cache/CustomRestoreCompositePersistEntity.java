package nettyServer.util.cache;

/**
 * 可自定义备份/还原内容的实体
 * <br>
 * 备份时使用自定义的数据结构进行保存,在恢复时将数据还原
 * 
 * @author yangxp
 */
public abstract class CustomRestoreCompositePersistEntity<T extends PersistEntity> extends CompositePersistEntity<T> {
	
	/**
	 * @return 备份的数据,只支持字符串形势,在没有数据可返回时允许返回null.
	 */
	public abstract String toBackupString();
	
	/**
	 * 从备份的字符串还原
	 * @param backupString 备份的数据字符串,字符串长度可能是0,但不是null.
	 */
	public abstract void restoreFromString(String backupString);
}
