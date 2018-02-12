package cn.saturn.web.center.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog="")
public interface GamesDAO {
	
	public static final String TABLE = "gamesmodel";
	public static final String KEYS ="games,times,rate";
	
	

	@SQL("select id," + KEYS + " from `" + TABLE+"` order by times  desc  limit 1")
	public GamesModel getGames();
	
	@SQL("replace into `" + TABLE + "` (`id`," + KEYS + " ) values(:1.id,:1.games,:1.times,:1.rate) ")
	public void insertOrUpdate(GamesModel gamesModel);
}
