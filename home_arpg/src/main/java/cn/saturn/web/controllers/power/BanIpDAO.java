package cn.saturn.web.controllers.power;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * @author xiezuojie
 */
@DAO
public interface BanIpDAO {

    String TABLE = "ban_ip";
    String FIELDS = "ip,note";

    @SQL("select ip from ban_ip")
    List<String> getList();

    @SQL("select count(ip) from ban_ip where ip=:1")
    int count(String ip);

}
