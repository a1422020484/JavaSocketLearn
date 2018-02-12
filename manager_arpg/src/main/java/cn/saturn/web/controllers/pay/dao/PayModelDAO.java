package cn.saturn.web.controllers.pay.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;


@DAO(catalog = "cn.saturn.web.code")
public interface PayModelDAO {
	
	String TABLE = "`payModel`";
	String FIELDS = "id, payaddr,ext";
	
	@SQL("select " + FIELDS + " from " + TABLE )
	public List<PayModel> getPayModelList();
	

}
