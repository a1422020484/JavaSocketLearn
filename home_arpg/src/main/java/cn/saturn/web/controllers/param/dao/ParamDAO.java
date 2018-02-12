package cn.saturn.web.controllers.param.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

@DAO
public interface ParamDAO {
    String TABLE = "param";
    String KEYS = "type, info";

    @SQL("select id," + KEYS + " from `" + TABLE + "` ")
    List<Param> getList();

    @SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1")
    Param get(long id);

    @SQL("select id," + KEYS + " from `" + TABLE + "` where id=:1 and type=:2")
    Param get(long id, String type);

    @SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.type,:1.info) ")
    void insertOrUpdate(Param model);

    @SQL("delete from `" + TABLE + "` where id=:1 limit 1")
    void remove(long id);

    // 读取最大ID
    @SQL("select max(id) from `" + TABLE + "` ")
    Long getMaxId();

}
