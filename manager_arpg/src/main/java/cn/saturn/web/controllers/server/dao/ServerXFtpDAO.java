package cn.saturn.web.controllers.server.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface ServerXFtpDAO {
	public static final String TABLE = "server_xftp";
	public static final String KEYS = "`s_id`, `host`, `port`, `user_name`, `password`, `game_path`";

	@SQL("select id," + KEYS + " from `" + TABLE + "` ")
	public List<ServerXFtpModel> getList();

	@SQL("select id," + KEYS + " from `" + TABLE + "` where s_id=:1 limit 1")
	public ServerXFtpModel getBySid(int id);

}
