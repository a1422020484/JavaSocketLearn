package zookeeperTest.atguigu;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 扩展Lock接口实现分布式锁
 *
 * @author Dongguabai
 * @date 2018/10/30 11:36
 */
public class DistributedLock implements Lock, Watcher {

	private static final Logger log = LoggerFactory.getLogger("DistributedLock");

	private ZooKeeper zk = null;

	/**
	 * 定义根节点
	 */
	private String ROOT_LOCK = "/locks";

	/**
	 * 表示等待前一个锁
	 */
	private String WAIT_LOCK;

	/**
	 * 表示当前锁
	 */
	private String CURRENT_LOCK;

	/**
	 * 主要用作控制
	 */
	private CountDownLatch countDownLatch;

	public DistributedLock() {
		try {
			zk = new ZooKeeper("192.168.96.133:2181,192.168.96.133:2182,192.168.96.133:2183", 4000, this);
			// 为false就不去注册当前的事件
			Stat stat = zk.exists(ROOT_LOCK, false);
			// 判断当前根节点是否存在
			if (stat == null) {
				// 创建持久化节点
				zk.create(ROOT_LOCK, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (Exception e) {
			log.error("初始化分布式锁异常！！", e);
		}
	}

	@Override
	public void lock() {
		if (tryLock()) {
			// 如果获得锁成功
			log.info(Thread.currentThread().getName() + "-->" + CURRENT_LOCK + "|获得锁成功！恭喜！");
			return;
		}
		// 如果没有获得锁，那么就继续监听，等待获得锁
		try {
			waitForLock(WAIT_LOCK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 持续阻塞获得锁的过程
	 * 
	 * @param prev
	 *            当前节点的前一个等待节点
	 * @return
	 */
	private boolean waitForLock(String prev) throws KeeperException, InterruptedException {
		// 等待锁需要监听上一个节点，设置Watcher为true，即每一个有序节点都去监听它的上一个节点
		Stat stat = zk.exists(prev, true);
		if (stat != null) {
			// 即如果上一个节点依然存在的话
			log.info(Thread.currentThread().getName() + "-->等待锁 " + ROOT_LOCK + "/" + prev + "释放。");
			countDownLatch = new CountDownLatch(1);
			countDownLatch.await();
			log.info(Thread.currentThread().getName() + "-->" + "等待后获得锁成功！");
		}
		return true;
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public boolean tryLock() {
		try {
			// 创建临时有序节点（节点会自动递增）-当前锁
			CURRENT_LOCK = zk.create(ROOT_LOCK + "/", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			log.info(Thread.currentThread().getName() + "-->" + CURRENT_LOCK + "|尝试竞争锁！");
			// 获取根节点下所有的子节点，不注册监听
			List<String> children = zk.getChildren(ROOT_LOCK, false);
			// 排序
			SortedSet<String> sortedSet = new TreeSet<>();
			children.forEach(child -> {
				sortedSet.add(ROOT_LOCK + "/" + child);
			});
			// 获取当前子节点中最小的节点
			String firstNode = sortedSet.first();
			if (StringUtils.equals(firstNode, CURRENT_LOCK)) {
				// 将当前节点和最小节点进行比较，如果相等，则获得锁成功
				return true;
			}
			// 获取当前节点中所有比自己更小的节点
			SortedSet<String> lessThenMe = sortedSet.headSet(CURRENT_LOCK);
			// 如果当前所有节点中有比自己更小的节点
			if (!lessThenMe.isEmpty()) {
				// 获取比自己小的节点中的最后一个节点，设置为等待锁
				WAIT_LOCK = lessThenMe.last();
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void unlock() {
		log.info(Thread.currentThread().getName() + "-->释放锁 " + CURRENT_LOCK);
		try {
			// -1强制删除
			zk.delete(CURRENT_LOCK, -1);
			CURRENT_LOCK = null;
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Condition newCondition() {
		return null;
	}

	/**
	 * 监听事件
	 * 
	 * @param event
	 */
	@Override
	public void process(WatchedEvent event) {
		if (this.countDownLatch != null) {
			// 如果不为null说明存在这样的监听
			this.countDownLatch.countDown();
		}
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	public static void main(String[] args) throws IOException {
		for (int j = 0; j < 10; j++) {

			CountDownLatch countDownLatch = new CountDownLatch(10);
			for (int i = 1; i <= 10; i++) {
				new Thread(() -> {
					try {
						countDownLatch.await();
						Thread.sleep(100);
						DistributedLock lock = new DistributedLock();
						lock.lock();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}, "Thread<" + i + ">").start();
				countDownLatch.countDown();
			}
			System.in.read();
		}

	}
}
