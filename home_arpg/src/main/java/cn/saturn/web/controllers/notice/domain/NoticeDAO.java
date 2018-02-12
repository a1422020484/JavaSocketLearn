package cn.saturn.web.controllers.notice.domain;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface NoticeDAO {
    String TABLE = "notice";
    String FIELDS = "`id`,`s_id`,`notice`,`enable`,`imgs`";

    @SQL("select "+ FIELDS +" from `" + TABLE + "` where id=:1 limit 1")
    Notice get(int id);
    
    @SQL("select "+ FIELDS +" from `" + TABLE + "` where `enable` like '1'")
    List<Notice> getEnables();

}
