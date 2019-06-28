package zookeeperTest;

import org.I0Itec.zkclient.ZkClient;

public class ZkConfigMsg {
	private Config config;

	public Config downLoadConfigFromDB() {
		config = new Config("nm", "pw");
		return config;
	}

	public void uploadConfigToDB(String nm, String pw) {
		if (config == null)
			config = new Config(nm, pw);
		config.setUserNm(nm);
		config.setUserPw(pw);
	}

	/**
	 * 配置文件上传到数据库
	 */
	public void upLoadConfigToDB(String nm, String pw) {
		if (config == null)
			config = new Config();
		config.setUserNm(nm);
		config.setUserPw(pw);
		// updateDB
	}

	/**
	 * 配置文件同步到zookeeper
	 */
	public void syncConfigToZk() {
		ZkClient zk = new ZkClient("192.168.96.133:2181");
		if (!zk.exists("/zkConfig")) {
			zk.createPersistent("/zkConfig", true);
		}
		zk.writeData("/zkConfig", config);
		zk.close();
	}

}
