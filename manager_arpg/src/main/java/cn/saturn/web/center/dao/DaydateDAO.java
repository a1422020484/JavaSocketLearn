package cn.saturn.web.center.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="cn.saturn.dataSource")
public interface DaydateDAO {
	
	//id,gamever,ttime,devicereg,newRegisterUser,absNewUser,dau,acu,pcu,payUser1,paySum,arpu,arppu1,dauarp,payRate,newPay,newPPayCount,rrppu,rrate,oldPaySum,oldPayCount,retained1,retained3,retained7,retained14, retained30,sendtime, rectime
	
	public static final String TABLE = "daydate";
	public static final String KEYS = "gamever,ttime,devicereg,newRegisterUser,absNewUser,dau,acu,pcu,payUser1,paySum,arpu,arppu1,dauarp,payRate,newPay,newPPayCount,rrppu,rrate,oldPaySum,oldPayCount,retained1,retained3,retained7,retained14, retained30,sendtime, rectime";
	
	@ReturnGeneratedKeys
	@SQL("insert into `" + TABLE + "` (" + KEYS + ") values(:1.gamever,:1.ttime,:1.devicereg,:1.newRegisterUser,:1.absNewUser,:1.dau,:1.acu,:1.pcu,:1.payUser1,:1.paySum,:1.arpu,:1.arppu1,:1.dauarp,:1.payRate,:1.newPay,:1.newPPayCount,:1.rrppu,:1.rrate,:1.oldPaySum,:1.oldPayCount,:1.retained1,:1.retained3,:1.retained7,:1.retained14, :1.retained30,:1.sendtime, :1.rectime) ")
	public int insert(DaydateModel daydate);
	
	@SQL("select id," + KEYS + " from `" + TABLE+"` where gamever=:gamever and ttime=:ttime desc limit 1")
	public DaydateModel getDaydate(@SQLParam("gamever") String gamever,@SQLParam("ttime") Date ttime);

	@SQL("select id," + KEYS + " from `" + TABLE+"` ")
	public List<DaydateModel> getList();

	
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.gamever,:1.ttime,:1.devicereg,:1.newRegisterUser,:1.absNewUser,:1.dau,:1.acu,:1.pcu,:1.payUser1,:1.paySum,:1.arpu,:1.arppu1,:1.dauarp,:1.payRate,:1.newPay,:1.newPPayCount,:1.rrppu,:1.rrate,:1.oldPaySum,:1.oldPayCount,:1.retained1,:1.retained3,:1.retained7,:1.retained14, :1.retained30,:1.sendtime, :1.rectime) ")
	public void insertOrUpdate(DaydateModel dayline);

}
