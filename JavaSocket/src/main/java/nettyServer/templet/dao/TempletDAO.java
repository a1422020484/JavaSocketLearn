package nettyServer.templet.dao;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import nettyServer.templet.entity.Templet;

/**
 * 
 * @author yangxp
 * 这个类必须以DAO结尾 必须是大写  
 */

@DAO
public interface TempletDAO {
	String TABLE = "templet";
	String FIELDS = "player_id,templet_name,templet_address,friend_list,create_time,create_time_l,friend_map";

	@SQL("SELECT " + FIELDS + " FROM templet WHERE player_id=:1")
	Templet get(int playerId);
	
	@SQL("SELECT " + FIELDS + " FROM templet WHERE player_id=:1")
	Templet findAll();

	@SQL("replace into "+ TABLE +" (" + FIELDS + ") values("
		            + ":1.playerId,"
		            + ":1.templetName,"
		            + ":1.templetAddress,"
		            + ":1.friendList,"
		            + ":1.createTime,"
		            + ":1.createTimeL,"
		            + ":1.friendMap"
		            + ")")
	void insertOrUpdate(List<Templet> templetList);
}
