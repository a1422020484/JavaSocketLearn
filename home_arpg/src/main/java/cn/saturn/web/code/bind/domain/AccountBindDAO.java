package cn.saturn.web.code.bind.domain;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

@DAO
public interface AccountBindDAO {
    String TABLE = "account_bind";
    String FIELDS = "account_id, srv_id, player_id, player_name, player_lv, create_time, iconUrl, iconFrame, fightingCapacity, vipLv";

    @SQL("select * from " + TABLE + " where account_id=:1")
    List<AccountBind> getList(long accountId);

    @SQL("select * from " + TABLE + " where account_id=:1 and srv_id=:2")
    AccountBind get(long accountId, int srvId);
    
    @SQL("select * from " + TABLE + " where player_id=:1 and srv_id=:2")
    AccountBind getByPlayerId(int playerId, int srvId);

    @SQL("replace into " + TABLE + " (`id`, " + FIELDS + ") values(:1.id,:1.accountId,:1.srvId,:1.playerId,:1.playerName,:1.playerLv,:1.createTime, :1.iconUrl, :1.iconFrame, :1.fightingCapacity, :1.vipLv) ")
    int insertOrUpdate(AccountBind model);

    @ReturnGeneratedKeys
    @SQL("insert into " + TABLE + " (" + FIELDS + ")  values(:1.accountId,:1.srvId,:1.playerId,:1.playerName,:1.playerLv,:1.createTime, :1.iconUrl, :1.iconFrame, :1.fightingCapacity, :1.vipLv) ")
    Long insert(AccountBind model);

}
