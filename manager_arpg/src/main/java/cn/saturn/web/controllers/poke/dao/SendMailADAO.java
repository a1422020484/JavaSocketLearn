package cn.saturn.web.controllers.poke.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "")
public interface SendMailADAO {
	
	@SQL("SELECT   id,presetid,preset1,num1,preset2,num2,preset3,num3,preset4,num4 FROM  temp_preset" )
	public List<TempPreset> getTempPreset();

}
