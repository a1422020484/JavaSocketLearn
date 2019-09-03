package zookeeperTest.watchT;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkWatchUtil {
	public static String config = "";

	public static void main(String[] args) {
		ZkClient zk = new ZkClient("192.168.96.133:2181");
		Object obj = (Object)zk.readData("/testW1");
		System.out.println("更改前 " + obj);
		zk.subscribeDataChanges("/testW1", new IZkDataListener() {
			@Override
			public void handleDataChange(String arg0, Object arg1) throws Exception {
				config = arg1.toString();
				System.out.println(arg0.toString() + " " + config);
			}

			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				System.out.println("监听到配置文件被删除");
			}

		});
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		zk.close();
	}
}
