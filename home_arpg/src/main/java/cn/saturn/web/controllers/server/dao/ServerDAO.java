package cn.saturn.web.controllers.server.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

@DAO
public interface ServerDAO {
    String TABLE = "server";
    String KEYS = "`name`, `url`, `state`, `remark`, `section`, `recommend`, `priority`, `operate`, maintainText, platforms, openTime, `open_time`";

    @SQL("select id," + KEYS + " from `" + TABLE + "` ")
    List<Server> getList();

    @SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1 limit 1")
    Server get(int id);

    @SQL("replace into `" + TABLE + "` (`id`," + KEYS + " ) values(:1.id,:1.name,:1.url,:1.state,:1.remark, :1.section, :1.recommend, :1.priority, :1.operate, :1.maintainText, :1.platforms, :1.openTime, :1.open_time) ")
    void insertOrUpdate(Server server);

    @SQL("delete from `server` where id=:1 limit 1")
    void remove(int id);

    // 读取最大ID
    @SQL("select max(id) from `" + TABLE + "` ")
    Long getMaxId();
}
