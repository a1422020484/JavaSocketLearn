package cn.saturn.web.controllers.statistics.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface SubChannelSummaryDAO {
	public static final String TABLE = "subchannelsummary";
	public static final String KEYS = "channelId,subChannelId,channelName,subChannelName,type,RU,AU,PU,amount,ARPU,ARPPU,statDate";

	@SQL("select " + KEYS + " from `" + TABLE + "` "+ "where subChannelId=:1 and statDate>=:2 and statDate<=:3")
	public List<SubChannelSummaryModel> getList(String subChannelId, Date startDate, Date endDate);
	
	@SQL("call ##(:1).stat_subChannel(null)")
	public void summary(String homemgr);
}
