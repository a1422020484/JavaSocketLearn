package zookeeperTest;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkGetConfigClient {

	private Config config;

	public Config getConfig() {
		ZkClient zk = new ZkClient("192.168.96.133:2181");
		config = (Config) zk.readData("/zkConfig");
		System.out.println("加载到配置：" + config.toString());

		// 监听配置文件修改
		zk.subscribeDataChanges("/zkConfig", new IZkDataListener() {
			@Override
			public void handleDataChange(String arg0, Object arg1) throws Exception {
				config = (Config) arg1;
				System.out.println("监听到配置文件被修改：" + config.toString());
			}

			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				config = null;
				System.out.println("监听到配置文件被删除");
			}

		});
		return config;
	}

	public static void main(String[] args) {
		ZkGetConfigClient client = new ZkGetConfigClient();
		client.getConfig();
		System.out.println(client.config.toString());
		for (int i = 0; i < 100; i++) {
			System.out.println(client.config.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
