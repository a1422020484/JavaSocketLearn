package cn.saturn.web.code.activation.domain;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

/**
 * 
 * @author qiuweimin
 */
@DAO
public interface ActivationDAO {

	String TABLE = "activationcode";
	String FIELDS = "`code`,closeTime,account,invaild";
	
	@SQL("select " + FIELDS + " from " + TABLE + " where `code`=:1 limit 1")
	public ActivationModel get( String code );
	
	@SQL("replace into " + TABLE + " (" + FIELDS + ") values(:1.code,:1.closeTime,:1.account,:1.invaild)")
	public Integer insertOrUpdate( ActivationModel model );
	
	@SQL( "replace into " + TABLE + " (" + FIELDS + ") values(:1.code,:1.closeTime,:1.account,:1.invaild)")
	public void insertOrUpdate( List<ActivationModel>  list );
}
