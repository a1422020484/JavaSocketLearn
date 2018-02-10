package nettyServer;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author zuojie.x
 */
public class Startup implements Daemon {

	public static void main(String[] args) {
		Startup st = new Startup();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					for (;;) {
						String str = br.readLine();
						if ("stop".equals(str)) {
							System.exit(0);
						} else {
							System.out.println("输入'stop'停止服务器!");
						}
					}
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();

		try {
			st.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
	}

	@Override
	public void start() throws Exception {
		// FIXME
		// 解决JDK7新排序规则可能在排序时报错问题,强制使用老排序规则
		// Comparison method violates its general contract!
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		// System.out.println(">>>>java.util.Arrays.useLegacyMergeSort:" +
		// System.getProperty("java.util.Arrays.useLegacyMergeSort"));
		GameServer.start();
	}

	@Override
	public void stop() throws Exception {
		GameServer.stop();
	}

	@Override
	public void destroy() {

	}

}