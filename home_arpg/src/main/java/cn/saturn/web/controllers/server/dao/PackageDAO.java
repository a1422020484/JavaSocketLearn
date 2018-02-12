package cn.saturn.web.controllers.server.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

@DAO
public interface PackageDAO {
    String TABLE = "package";
    String KEYS = "platform, version, resversion, resurl, notice,type";

    @SQL("select id," + KEYS + " from `" + TABLE + "` ")
    List<Package> getList();

    @SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.platform,:1.version,:1.resversion,:1.resurl,:1.notice,:1.type) ")
    void insertOrUpdate(Package model);

    @SQL("delete from `" + TABLE + "` where id=:1 limit 1")
    void remove(long id);

    // 读取最大ID
    @SQL("select max(id) from `" + TABLE + "` ")
    Long getMaxId();
}
