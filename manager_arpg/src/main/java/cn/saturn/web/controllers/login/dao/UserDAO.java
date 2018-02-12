package cn.saturn.web.controllers.login.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

//@DAO(catalog="cn.saturn")
@DAO(catalog="cn.saturn.web.code")
public interface UserDAO {
	String TABLE = "`user`";
	String FIELDS = "id, username, authority, remark";

	// @SQL("select * from " + TABLE + " where username=:username and password=md5(:password) limit 1")
	// public UserModel get(@SQLParam("username") String username, @SQLParam("password") String password);

	@SQL("select " + FIELDS + " from " + TABLE + " where username=:username and password=md5(:password) limit 1")
	public UserModel get(@SQLParam("username") String username, @SQLParam("password") String password);

	@ReturnGeneratedKeys
	@SQL("select " + FIELDS + " from " + TABLE + " where username=:username limit 1")
	public UserModel get(@SQLParam("username") String username);

	@SQL("select " + FIELDS + " from " + TABLE + " where id=:id limit 1")
	public UserModel get(@SQLParam("id") int id);

	@SQL("select " + FIELDS + " from " + TABLE + " where authority<>" + UserModel.authority_admin)
	public List<UserModel> get();

	@SQL("update " + TABLE + " set username=:1.username, authority=:1.authority, remark=:1.remark where id=:1.id")
	public int update(UserModel userModel);

	@ReturnGeneratedKeys
	@SQL("insert into " + TABLE + " (username, `password`, authority, remark)  values(:1.username, md5(:2),:1.authority,:1.remark)")
	public Integer insert(UserModel userModel, String password);

	@SQL("update " + TABLE + " set `password`=MD5(:2) where id=:1 limit 1")
	public int changePwd(int userId, String password);

	@SQL("delete from " + TABLE + " where id=:1 limit 1")
	public int delete(int id);

}
