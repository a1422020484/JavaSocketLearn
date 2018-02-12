package cn.saturn.web.controllers.server.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

@DAO
public interface VersionDAO {
    String TABLE = "version";
    String KEYS = "platform,version, url, notice";

    @SQL("select id," + KEYS + " from `" + TABLE + "` ")
    List<Version> getList();

    @SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.platform,:1.version,:1.url,:1.notice) ")
    void insertOrUpdate(Version model);

    // @SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.platform,:1.version,:1.url,:1.notice) ")
    // public void insert(VersionModel model);

    @SQL("delete from `" + TABLE + "` where id=:1 limit 1")
    void remove(long id);

    // 读取最大ID
    @SQL("select max(id) from `" + TABLE + "` ")
    Long getMaxId();
}
