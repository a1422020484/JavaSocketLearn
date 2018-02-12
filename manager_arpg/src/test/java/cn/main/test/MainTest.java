package cn.main.test;

import proto.Protocol.PlayerInfo;
import proto.ProtocolWeb;
import proto.ProtocolWeb.LoginCheckWCS;
import proto.ProtocolWeb.LoginCheckWSC;
import proto.ProtocolWeb.VersionWSC;
import xzj.core.client.GameClient;
import xzj.core.client.GameClient.Response;
import xzj.core.client.GameClient.ResponseHandlerAdaptor;
import cn.saturn.web.code.Head;

import com.google.protobuf.InvalidProtocolBufferException;

public class MainTest {

	// public final static String url = "119.29.101.49:8080/home/Code?port=";
	public final static String url = "127.0.0.1:8080/home/Code?port=";
//	 public final static String url = "login2.saturngamebox.com/home/Code?port=";
//	 public final static String url = "loginqq.saturngamebox.com/home/Code?port=";
	public static GameClient c = new GameClient(url, 8080);

	public static void main(String[] args) {

		// ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		c.setErrHandler(new ErrPrintHandler());
		c.addHandler(new ErrPrintHandler());
		c.addHandler(TempHandle.staticHandle(Head.H17001, ProtocolWeb.GetCDKeyWSC.class));
		c.addHandler(TempHandle.staticHandle(Head.H17002, ProtocolWeb.UploadCDKeyWSC.class));
		// c.addHandler(TempHandle.systemHandle(Head.H17002, ProtocolWeb.UploadCDKeyWCS.class));
		// c.addHandler(TempHandle.staticHandle(Head.H10001, ProtocolWeb.ServerListWSC.class));
		
		c.addHandler(TempHandle.staticHandle(Head.H20002, ProtocolWeb.UploadActivationCodeWSC.class));
		c.addHandler(TempHandle.staticHandle(16001, VersionWSC.class));
		c.addHandler(new H10001Handler());
		c.addHandler(new H10002Handler());

		register();
//		login();
		// cdkey();
//		 serverList();
		// uploadkeys();
//		version();
		
//		uploadActivationCode();
	}
	
	public static void register() {
		ProtocolWeb.RegisterCS.Builder pslb = ProtocolWeb.RegisterCS.newBuilder();
		pslb.setAccount("2016041803");
		pslb.setPassword("1");
		pslb.setPlatform("Saturn");
		pslb.setActivationCode("641053");
		// pslb.setPassword("Saturn");

		c.send(10007, pslb.build());

	}
	public static void login() {
		// ProtocolWeb.LoginWCS.Builder pslb = ProtocolWeb.LoginWCS.newBuilder();
		// // int i = (int) (Math.random() * 10000);
		// // pslb.setAccount("" + i);
		// pslb.setAccount("123456");
		// pslb.setPassword("123");
		// // pslb.setRapid(true);
		// //pslb.setPlatform("Saturn");
		// pslb.setExt("pt=Saturn&account=wocaowori@127.com&password=123");
		// c.send(10002, pslb.build());

		ProtocolWeb.LoginWCS.Builder pslb = ProtocolWeb.LoginWCS.newBuilder();
		pslb.setAccount("");
		pslb.setPassword("");
		// pslb.setExt("pt=uc&sid=sst1game9ba240170b304d7f8d12b568e200d8b8106411");
		pslb.setVersion("1.0.2");
//		 pslb.setExt("pt=Saturn&account=123&password=123");
//		pslb.setExt("pt=Saturn&account=@712&password=Saturn2015");
//		pslb.setExt("pt=Saturn&account=@123&password=Saturn2015");	//本服测试
		pslb.setExt("pt=Saturn_manle&account=12345622224&password=1235");	//本服测试
		// pslb.setSystemInfo("deviceModel=Xiaomi HM NOTE 1LTE&deviceName=<unknown>&deviceUI=c7567ebbf9edf1b76ff8b25a8f9d9803&gpuID=0&gpuName=Adreno (TM) 305&gpuVID=0&gpuVer=OpenGL ES 3.0 V@66.0 AU@04.04.04.154.004 (CL@)&gpuMem=182&gpuPFR=-1&gpuSL=30&cpuCount=4&sysMem=1866&OS=Android OS 4.4.4 / API-19 (KTU84P/V7.0.5.0.KHICNCI)&dpi=320&screenSize=1280x720");
		c.send(10002, pslb.build());
	}
	
	public static void version() {
		ProtocolWeb.VersionWCS.Builder pslb = ProtocolWeb.VersionWCS.newBuilder();
		pslb.setPlatform("zhuoyi");
		pslb.setVersion("1.0.9");
		pslb.setResplatform("Android");
		pslb.setResversion("0");

		// VersionWSC b = null;
		// VersionItem ver = b.getVer();

		c.send(16001, pslb.build());
	}
	
	
	

	public static void cdkey() {
		ProtocolWeb.GetCDKeyWCS.Builder pslb = ProtocolWeb.GetCDKeyWCS.newBuilder();
		pslb.setKey("YWA4JZ5NKATSY4FS");
		c.send(Head.H17001, pslb.build());
	}

	public static void serverList() {
		ProtocolWeb.ServerListWCS.Builder pslb = ProtocolWeb.ServerListWCS.newBuilder();

		pslb.setPlatform("uc");
//		pslb.setPlatform("zhuoyi");
		pslb.setVersion("1.0.9");
		pslb.setAccountId(712);
		c.send(10001, pslb.build());
	}

	public static void uploadkeys() {
		final int maxcount = 70; // 最多70个
		ProtocolWeb.UploadCDKeyWCS.Builder b = ProtocolWeb.UploadCDKeyWCS.newBuilder();
		for (int i = 0; i < maxcount; i++) {
			ProtocolWeb.CDKey.Builder b0 = ProtocolWeb.CDKey.newBuilder();
			b0.setAward("1233");
			b0.setKey(String.valueOf(i + 30));
			ProtocolWeb.CDKey cdkey = b0.build();
			b.addKey(cdkey);
		}
		ProtocolWeb.UploadCDKeyWCS sendMsg = b.build();
		c.send(Head.H17002, sendMsg);
	}
	
	//上传激活码
	public static void uploadActivationCode(){
		int count = 100;
		ProtocolWeb.UpLoadActivationCodeWCS.Builder b = ProtocolWeb.UpLoadActivationCodeWCS.newBuilder();
		for( int i = 0; i < count; i++ ){
			ProtocolWeb.ActivationCode.Builder b0 = ProtocolWeb.ActivationCode.newBuilder();
			b0.setCode(i + "1053");
			b.addCode(b0.build());
		}
		c.send(Head.H20002, b.build());
	}

}

class H10001Handler extends ResponseHandlerAdaptor {
	@Override
	public int getHeadId() {
		return 10001;
	}

	@Override
	public void handle(Response r) throws InvalidProtocolBufferException {
		ProtocolWeb.ServerListWSC msg = ProtocolWeb.ServerListWSC.parseFrom(r.protobufData);
		String msgStr = TempHandle.msgStr(msg);
		System.out.println("* " + getHeadId() + " * : ");

		// 服务器列表
		int count = msg.getSrvListCount();
		for (int i = 0; i < count; i++) {
			ProtocolWeb.ServerItem serverItem = msg.getSrvList(i);
			System.out.println(i + ":" + show(serverItem));
		}

		// 服务器区数量
		System.out.println("------区列表------");
		int secCount = msg.getSectionListCount();
		for (int i = 0; i < secCount; i++) {
			ProtocolWeb.ServerSection sectionItem = msg.getSectionList(i);
			System.out.println(i + ":" + showSrv2(sectionItem));
		}
	}

	public static String showSrv2(ProtocolWeb.ServerSection sectionItem) {

		int id = sectionItem.getSectionId();
		String name = sectionItem.getName();
		String rstr = sectionItem.getRecommend() ? "推荐" : "";
		String sstr = (sectionItem.getState() == 1) ? "正常" : "新区";

		return "[" + id + "," + name + "] " + rstr + " - " + sstr;

	}

	public static String show(ProtocolWeb.ServerItem srvItem) {
		int id = srvItem.getSrvId();
		String name = srvItem.getSrvName();
		String txt = srvItem.getMaintainText();
		boolean recommend = srvItem.getRecommend();
		String recommendStr = (recommend) ? " 推荐" : "";

		// 玩家信息
		PlayerInfo playerInfo = srvItem.getPlayerInfo();
		if (playerInfo != null && playerInfo.getId() <= 0) {
			playerInfo = null;
		}
		// H5101Handler.playerStr(playerInfo)
		return "[id:" + id + " name:" + name + recommendStr + " " + " txt=" + txt + "]";
	}

	@Override
	public boolean isEncrypted() {
		return false;
	}

};

class H10002Handler extends ResponseHandlerAdaptor {

	@Override
	public int getHeadId() {
		return 10002;
	}

	@Override
	public boolean isEncrypted() {
		return false;
	}

	public static String showSrv2(ProtocolWeb.ServerSection sectionItem) {

		int id = sectionItem.getSectionId();
		String name = sectionItem.getName();
		String rstr = sectionItem.getRecommend() ? "推荐" : "";
		String sstr = (sectionItem.getState() == 1) ? "正常" : "新区";

		return "[" + id + "," + name + "] " + rstr + " - " + sstr;

	}

	@Override
	public void handle(Response r) throws InvalidProtocolBufferException {
		ProtocolWeb.LoginWSC msg = ProtocolWeb.LoginWSC.parseFrom(r.protobufData);
		String code = msg.getCode();

		System.out.println("code:" + code);

		// 服务器数量
		System.out.println("------服务器列表------");
		int srvCount = msg.getSrvListCount();
		for (int i = 0; i < srvCount; i++) {
			ProtocolWeb.ServerItem serverItem = msg.getSrvList(i);
			System.out.println(i + ":" + H10001Handler.show(serverItem));
		}

		// 服务器区数量
		System.out.println("------区列表------");
		int secCount = msg.getSectionListCount();
		for (int i = 0; i < secCount; i++) {
			ProtocolWeb.ServerSection sectionItem = msg.getSectionList(i);
			System.out.println(i + ":" + showSrv2(sectionItem));
		}

		// 公告
		String notice = msg.getNotice();
		if (notice != null && notice.length() > 0) {
			System.out.println("notice:" + notice);
		}

		// 主题
		int theme = msg.getTheme();
		System.out.println("theme:" + theme);

		// 主题
		String csInfo = msg.getCsIndo();
		System.out.println("csInfo:" + csInfo);

		// 发送验证
		System.out.println("--------------->发送登陆验证." + code.length());
		LoginCheckWCS.Builder b = LoginCheckWCS.newBuilder();
		b.setCode(code);
		b.setSrvId(3);
		LoginCheckWCS sendMsg = b.build();

		// WebTest.c.send(10003, sendMsg);
	}

};

class H10003Handler extends ResponseHandlerAdaptor {
	@Override
	public boolean isSystem() {
		return true;
	}

	@Override
	public int getHeadId() {
		return 10003;
	}

	@Override
	public void handle(Response r) throws InvalidProtocolBufferException {
		LoginCheckWSC msg = LoginCheckWSC.parseFrom(r.protobufData);
		// long accountId = msg.getAccountId();
		// String accountName = msg.getAccountName();

		// System.out.println("account:" + accountId + "," + accountName);
		System.out.println("msg:" + this.getHeadId() + " " + msg);
	}

};
