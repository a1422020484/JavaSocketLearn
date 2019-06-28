package zookeeperTest.atguigu;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class TestZookeeper {

	String connectString = "192.168.96.133:2181,192.168.96.133:2182,192.168.96.133:2183";
	int sesssionTimeout = 1000;
	private ZooKeeper zkClinet;

	@Before
	public void init() throws IOException {
		zkClinet = new ZooKeeper(connectString, sesssionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {

			}

		});
	}

	@Test
	public void createNode() throws KeeperException, InterruptedException {

		String path = zkClinet.create("/atguigu", "yangxpppp".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		System.out.println(path);

	}

	@Test
	public void getStat() throws KeeperException, InterruptedException {
		Stat stat = zkClinet.exists("/atguigu1", new Watcher() {

			@Override
			public void process(WatchedEvent event) {

			}

		});
		System.out.println(stat.getDataLength());
	}
}
