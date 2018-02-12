package cn.saturn.web.center.dao;

import java.util.Date;
import java.util.List;

import cn.saturn.web.controllers.menu.dao.MenuModel;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog="")
public interface DaylineDAO {
	
	public static final String TABLE = "dayline";
	public static final String KEYS = "gamever,ttime,devicereg,newRegisterUser,absNewUser,dau,payUser1,paySum,arpu,arppu1,dauarpu,payRate,newPay,newPPayCount,oldPaySum,oldPayCount,retained1,sendtime,rectime";
	
	@ReturnGeneratedKeys
	@SQL("insert into `" + TABLE + "` (" + KEYS + ") values(:1.gamever,:1.ttime,:1.devicereg,:1.newRegisterUser,:1.absNewUser,:1.dau,:1.payUser1,:1.paySum,:1.arpu,:1.arppu1,:1.dauarpu,:1.payRate,:1.newPay,:1.newPPayCount,:1.oldPaySum,:1.oldPayCount,:1.retained1,:1.sendtime,:1.rectime) ")
	public int insert(DaylineModel dayline);
	
	@SQL("select id," + KEYS + " from `" + TABLE+"` where gamever=:gamever order by ttime desc limit 1")
	public DaylineModel getDayline(@SQLParam("gamever") String gamever);

	@SQL("select id," + KEYS + " from `" + TABLE+"` ")
	public List<DaylineModel> getList();

	
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.gamever,:1.ttime,:1.devicereg,:1.newRegisterUser,:1.absNewUser,:1.dau,:1.payUser1,:1.paySum,:1.arpu,:1.arppu1,:1.dauarpu,:1.payRate,:1.newPay,:1.newPPayCount,:1.oldPaySum,:1.oldPayCount,:1.retained1,:1.sendtime,:1.rectime) ")
	public int insertOrUpdate(DaylineModel dayline);
	

}
