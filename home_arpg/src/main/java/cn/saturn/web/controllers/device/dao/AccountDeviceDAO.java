package cn.saturn.web.controllers.device.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface AccountDeviceDAO {
	
	String TABLE = "accountdevice_reg";
    String KEYS = "deviceUI,OS,deviceModel,systemInfo,reg_time,platform,subplatform";
	
	 @SQL("insert into " + TABLE + "(" + KEYS + ") values(:1.deviceUI,:1.OS,:1.deviceModel,:1.systemInfo,:1.reg_time,:1.platform,:1.subplatform) ")
	 void insert(AccountDeviceModel model);

}
