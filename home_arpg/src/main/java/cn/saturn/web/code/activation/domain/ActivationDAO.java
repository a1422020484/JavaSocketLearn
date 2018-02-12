package cn.saturn.web.code.activation.domain;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * @author qiuweimin
 */
@DAO
public interface ActivationDAO {

    String TABLE = "activationcode";
    String FIELDS = "`code`,closeTime,account,invaild";

    @SQL("select " + FIELDS + " from " + TABLE + " where `code`=:1 limit 1")
    Activation get(String code);

    @SQL("replace into " + TABLE + " (" + FIELDS + ") values(:1.code,:1.closeTime,:1.account,:1.invaild)")
    Integer insertOrUpdate(Activation model);

    @SQL("replace into " + TABLE + " (" + FIELDS + ") values(:1.code,:1.closeTime,:1.account,:1.invaild)")
    void insertOrUpdate(List<Activation> list);
}
