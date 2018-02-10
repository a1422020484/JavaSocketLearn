package nettyServer.system.dao.commonDao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nettyServer.system.dao.annotation.Primarykey;
import nettyServer.system.dao.persistence.EntityDAO;

public class PersistenceDAO extends EntityDAO{
	
	private static final PersistenceDAO instantce = new PersistenceDAO();
	
	public static PersistenceDAO getInstantce(){
		return instantce;
	}
	
	private static final Logger logger = LogManager.getLogger(PersistenceDAO.class.getName());

	public void delete(List<Object> deleteList) throws Exception{
		if(deleteList.isEmpty()){
			return;
		}
		Connection conn = getConnection();
		try {
			Object o = deleteList.get(0);
			Class<?> entityC = o.getClass();
			String sql = getDeleteSql(entityC);
			logger.info("【PersistenceDAO delete】:"+sql);
			PreparedStatement ps = null;
			for (Object entity : deleteList) {
				try{
					ps = conn.prepareStatement(sql);
					Class<?> eveC = entity.getClass();
					Field[] fs = eveC.getDeclaredFields();
					int i = 1;
					for(Field field:fs){
						field.setAccessible(true);
						if(field.isAnnotationPresent(Primarykey.class)){
							String typeName = field.getType().getSimpleName();
							Object param = field.get(entity);
							setParameter(i++, ps, typeName, param);
						}
					}
					ps.executeUpdate();
					logger.info("execute sql=["+ps.toString()+"]");
				}catch (Exception e) {
					logger.error(e.getMessage(),e);
					throw new Exception();//抛出异常,用于调用处理
				}finally{
					if (ps != null) {
						try {
							ps.close();
						} catch (SQLException e) {
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new Exception();//抛出异常,用于调用处理
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public void margeData(List<?> notifyList) throws Exception{
		if(notifyList.isEmpty()){
			return;
		}
		Connection conn = getConnection();
		try {
			Object o = notifyList.get(0);
			Class<?> entityC = o.getClass();
			String sql = getSql(entityC);
			logger.info("【PersistenceDAO margeData】:"+sql);
			PreparedStatement ps = null;
			for (Object entity : notifyList) {
				try{
					ps = conn.prepareStatement(sql);
					Class<?> eveC = entity.getClass();
					Field[] fs = eveC.getDeclaredFields();
					int i = 1;
					for(Field field:fs){
						field.setAccessible(true);
						String typeName = field.getType().getSimpleName();
						Object param = field.get(entity);
						setParameter(i++, ps, typeName, param);
					}
					logger.info("execute sql=["+ps.toString()+"]");
					ps.executeUpdate();
				}catch (Exception e) {
					logger.error(e.getMessage(),e);
					throw new Exception();//抛出异常,用于调用处理
				}finally{
					if (ps != null) {
						try {
							ps.close();
						} catch (SQLException e) {
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception();//抛出异常,用于调用处理
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	private String getDeleteSql(Class<?> entityC) throws Exception{
		String tableName = entityC.getSimpleName();
		tableName = tableName.toLowerCase();
		
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(tableName);
		sb.append(" where ");
		
		Field[] fs = entityC.getDeclaredFields();
		int size = fs.length;
		int n = 0;
		for (int i = 0; i < size; i++) {
			Field fi = fs[i];
			if(fi.isAnnotationPresent(Primarykey.class)){
				n++;
				sb.append(fi.getName() + "=? ");
				sb.append("and ");
			}
		}
		if(n == 0){
			logger.error(" class:" + tableName + "  not sign @Primarykey ");
			throw new Exception();
		}
		sb.delete(sb.length() - 4, sb.length());
		return sb.toString();
	}
	
	private String getSql(Class<?> entityC) throws Exception{
		String tableName = entityC.getSimpleName();
		tableName = tableName.toLowerCase();
		
		StringBuilder sb = new StringBuilder();
		sb.append("replace into ");
		sb.append(tableName);
		sb.append("(");
		
		Field[] fs = entityC.getDeclaredFields();
		int size = fs.length;
		
		for (int i = 0; i < size; i++) {
			if(i < size - 1){
				sb.append(fs[i].getName());
				sb.append(",");
			} else {
				sb.append(fs[i].getName());
			}
		}
		sb.append(") values(");
		for (int i = 0; i < size; i++){
			if(i < size - 1){
				sb.append("?");
				sb.append(",");
			} else {
				sb.append("?");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	private void setParameter(int num,PreparedStatement ps, String typeName, Object o) throws Exception{
		switch (typeName) {
		case "String":
			ps.setString(num, (String)o);
			break;
		case "byte":
			ps.setByte(num, (byte)o);
			break;
		case "byte[]":
			ps.setBytes(num, (byte[])o);
			break;
		case "int":
			ps.setInt(num, (int)o);
			break;
		case "short":
			ps.setShort(num, (short)o);
			break;
		case "long":
			ps.setLong(num, (long)o);
			break;
		case "Date":
			ps.setDate(num, new java.sql.Date(((java.util.Date)o).getTime()));
			break;
		default:
			break;
		}
	}
	
}
