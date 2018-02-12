package cn.saturn.web.controllers.account.dao;
import java.util.List;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;


@DAO(catalog="cn.saturn.web.code")
public interface SettingAuthorityDAO {
	
    @SQL("select authority_id,authority_name from authority where is_enable='1'" )
	public List<AuthorityModel> getList();
    
    @SQL("select menu_id from authority_menu_rel  where authority_id=:1")
    public List<String> getSelectedMenu(String authority_id);
    
    @SQL("insert authority_menu_rel(authority_id,menu_id) values (:1,:2)")
   public int updateOrInsertAuthority(String authorityId,String menuID);
    
    @SQL("select count(*) from authority_menu_rel where authority_id=:1 and menu_id=:2 ")
	public int  getCount(String authorityId,String menuID);
	
    @SQL("delete  from authority_menu_rel where authority_id=:1 ")
   	public int  delete(String authorityId);
    
    
    @SQL("select count(*) from  authority  where authority_name=:1")
    public int  isExistSameCode(String authorityName);
    
    @SQL("insert into authority(authority_name) values (:1)")
    public int  insertAuthority(String authorityName);
    
    
    @SQL("select id,authority_id,menu_id from authority_menu_rel")
    public List<AuthorityToMenuModel>  getAuthorityToMenuList();
    
    @SQL("select authority_name from authority")
    public List<String>  getAuthorityNames();
    
    @SQL("select authority_name from authority where authority_id=:1")
    public List<String>  getAuthorityNamesById(String id);
    
}
