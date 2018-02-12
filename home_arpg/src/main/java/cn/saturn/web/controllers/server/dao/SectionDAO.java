package cn.saturn.web.controllers.server.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

@DAO
public interface SectionDAO {
    String TABLE = "section";
    String KEYS = "`name`,`state`, recommend, `tag`, `platforms`";

    @SQL("select id," + KEYS + " from `" + TABLE + "` ")
    List<Section> getList();

    @SQL("replace into `" + TABLE + "` (`id`," + KEYS + ") values(:1.id,:1.name,:1.state,:1.recommend,:1.tag,:1.platforms) ")
    void insertOrUpdate(Section model);

    @SQL("delete from `" + TABLE + "` where id=:1 limit 1")
    void remove(long id);

    // 读取最大ID
    @SQL("select max(id) from `" + TABLE + "` ")
    Long getMaxId();
}
