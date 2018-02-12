package cn.saturn.operation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ListRowMapper implements RowMapper<String[]> {

	private String[] titleWorld;
	private String[] content;

	public ListRowMapper(String[] world) {
		this.titleWorld = world;
	}

	@Override
	public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (null == titleWorld)
			return null;
		content = new String[titleWorld.length];

		for (int i = 0; i < titleWorld.length; i++)
			content[i] = rs.getString(titleWorld[i]);

		return content;
	}

	public static String getValue(String[] title,String[] content,String titleName)
	{
		if(null==titleName) return "";
		
		for(int i=0;i<title.length;i++)
		{
			if(title[i].equals(titleName))
				return content[i];
		}
		
		return "";
	}

}
