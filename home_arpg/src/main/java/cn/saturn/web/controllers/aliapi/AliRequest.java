package cn.saturn.web.controllers.aliapi;


import java.util.List;

import cn.saturn.web.utils.Config;


public class AliRequest {
	
	private static String apikey = Config.val("apikey");
	
    private  long  id;
	private AlRequestGame game;
	private AlRequestData data;
	private String sign;
	
	public static final AliRequest EMPTY = new AliRequest();
	
	public  AliRequest() {
        id = 0;
        game = AlRequestGame.EMPTY;
        data=AlRequestData.EMPTY;
        sign = "";
    }
	
	
	public static class AlRequestGame{
		
		public static final AlRequestGame EMPTY= AlRequestGame.empty();
		
		private  int gameId;
		
		public AlRequestGame(){}
		
		public static AlRequestGame empty(){
			AlRequestGame game=new AlRequestGame();
			game.gameId=0;
			return game;
		}
	}
	
	public static class PrivilegeCodes{
		public List<String> privilegeCodes;

		public List<String> getPrivilegeCodes() {
			return privilegeCodes;
		}

		public void setPrivilegeCodes(List<String> privilegeCodes) {
			this.privilegeCodes = privilegeCodes;
		}
		
	}
	
	public static class AlRequestData{
		
		public static final AlRequestData EMPTY=AlRequestData.empty();
		
		public String accountId; //账号标识
		public String creator; //角色创建者
		public String privilegeInfo;  //特权码信息
		
		public AlRequestData(){}
		
		public static AlRequestData empty(){
		
			AlRequestData data=new AlRequestData();
			data.accountId ="";
			data.creator = "";
			data.privilegeInfo="";
			return data;
		}

		public String getAccountId() {
			return accountId;
		}

		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}

		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}

		public String getPrivilegeInfo() {
			return privilegeInfo;
		}

		public void setPrivilegeInfo(String privilegeInfo) {
			this.privilegeInfo = privilegeInfo;
		}
		
		
	}
	
	
}
