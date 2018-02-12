package cn.saturn.web.controllers.adv.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface AdvDAO {
	
	String TABLE = "advmodel";
	String KEYS = "games, adPlatform, subPlatform, version, device, ts, callback_url, callback_param, aid,cid,mac_sum,mac_sum1,androidid_sum,ua,os,lbs,ip,createtime,advType";
	
	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
    public  List<AdvModel> getList();

    @SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1 limit 1")
    public AdvModel get(int id);
    
    @SQL("select id," + KEYS + " from `" + TABLE + "` where os=:1 and device=:2 limit 1")
    public AdvModel getAdvByOsDev(String os,String device);
    
    @SQL("select id," + KEYS + " from `" + TABLE + "` where  BINARY  device=:1  and createtime>=:2 order by  id  desc limit 1 ")
    public AdvModel getAdvByDev(String device,Date date);
    
    @ReturnGeneratedKeys
    @SQL("insert into `" + TABLE + "` (`id`," + KEYS + " ) values(:1.id,:1.games, :1.adPlatform,:1.subPlatform, :1.version, :1.device, :1.ts,:1.callback_url,:1.callback_param,:1.aid,:1.cid,:1.mac_sum,:1.mac_sum1,:1.androidid_sum,:1.ua,:1.os,:1.lbs,:1.ip,:1.createtime,:1.advType)")
    public int insertOrUpdate(AdvModel adv);
    
    
    @SQL("replace into `" + TABLE + "` (`id`," + KEYS + " ) values(:1.id,:1.games, :1.adPlatform,:1.subPlatform, :1.version, :1.device, :1.ts,:1.callback_url,:1.callback_param,:1.aid,:1.cid,:1.mac_sum,:1.mac_sum1,:1.androidid_sum,:1.ua,:1.os,:1.lbs,:1.ip,:1.createtime,:1.advType)")
    public int Update(AdvModel adv);
    
	 
}
