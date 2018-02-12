package cn.saturn.web.controllers.device.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface DeviceDAO {
    String TABLE = "device_reg";
    String KEYS = "deviceUI,OS,deviceModel,systemInfo,reg_time,platform,subPlatform,games,adPlatform,adSubPlatform,adVersion,idfa,advType";

    @SQL("replace into `" + TABLE + "` (" + KEYS
            + ") values(:1.id,:1.deviceUI,:1.OS,:1.deviceModel,:1.systemInfo,:1.reg_time,:1.platform,:1.subPlatform,:1.games,:1.adPlatform,:1.adSubPlatform,:1.adVersion,:1.idfa,:1.advType) ")
    void insertOrUpdate(DeviceModel model);

    @SQL("insert into " + TABLE + "(" + KEYS + ") values(:1.deviceUI,:1.OS,:1.deviceModel,:1.systemInfo,NOW(),:1.platform,:1.subPlatform,:1.games,:1.adPlatform,:1.adSubPlatform,:1.adVersion,:1.idfa,:1.advType) ")
    void insert(DeviceModel model);

    // 读取最大ID
    @SQL("select max(id) from `" + TABLE + "` ")
    Long getMaxId();
}
