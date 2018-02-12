package cn.saturn.web.controllers.power;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * @author xiezuojie
 */
@DAO
public interface VindicatorIpDAO {
    String TABLE = "vindicator_ip";
    String FIELDS = "ip,note";

    @SQL("select ip from vindicator_ip")
    List<String> getList();
}
