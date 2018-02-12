package nettyServer;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import nettyServer.dispatch.ActionTaskService;
import nettyServer.dispatch.HttpServerHandler;
import nettyServer.dispatch.SessionManager;
import nettyServer.listener.ServerCloseListener;
import nettyServer.listener.ServerStartupListener;
import nettyServer.listener.SessionCloseListener;
import nettyServer.util.CoreConfig;
import nettyServer.util.LogKey;
import nettyServer.util.resource.ResourceMonitor;

/**
 * @author yangxp
 */
public class GameServer {
	private static final Logger log = LoggerFactory.getLogger(LogKey.CORE);

	private ApplicationContext context;
	private NioEventLoopGroup bossGroup = new NioEventLoopGroup();
	private NioEventLoopGroup workerGroup = new NioEventLoopGroup();

	private static GameServer gameServer = new GameServer();

	/**
	 * 启动服务
	 */
	public static void start() {
		try {
			gameServer.doStart();
		} catch (Exception e) {
			e.printStackTrace();
			stop();
		}
	}

	/**
	 * 关闭服务
	 */
	public static void stop() {
		System.exit(0);
	}

	private GameServer() {
		context = new ClassPathXmlApplicationContext("default.xml");
		context.getBean(ResourceMonitor.class).monitoring();
		Map<String, ServerStartupListener> listeners = context.getBeansOfType(ServerStartupListener.class);
		listeners = orderStartupListener(listeners);
		for (ServerStartupListener listener : listeners.values()) {
			listener.started();
		}
	}

	private void doStart() throws Exception {
		// tcp
		int tcpPort = CoreConfig.intValue("game.tcp.port");
		ServerBootstrap tcpBootstrap = new ServerBootstrap();
		try {
			tcpBootstrap.group(bossGroup, workerGroup);
			tcpBootstrap.channel(NioServerSocketChannel.class);
			tcpBootstrap.childHandler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel channel) throws Exception {
					channel.pipeline().addLast(
							new HttpRequestDecoder(),
							new HttpResponseEncoder(),
							new HttpObjectAggregator(CoreConfig.MaxDataLength),
//							new HttpContentCompressor(),
							new HttpServerHandler()
							);
				}
			});
			tcpBootstrap.childOption(ChannelOption.TCP_NODELAY, CoreConfig.booleanValue("TCP_NODELAY"));
			tcpBootstrap.childOption(ChannelOption.SO_KEEPALIVE, CoreConfig.booleanValue("SO_KEEPALIVE"));
			tcpBootstrap.childOption(ChannelOption.SO_REUSEADDR, CoreConfig.booleanValue("SO_REUSEADDR"));
			Runtime.getRuntime().addShutdownHook(new ShutdownHook());
			log.info("server started.");
			ChannelFuture future = tcpBootstrap.bind(tcpPort).sync();
			future.channel().closeFuture().sync();
			log.info("netty unsync!");
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	/**
	 * 停止服务器
	 */
	private void doStop() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		try {
			// 等待让其它线程执行
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
		}
		SessionManager.stopMonitor();
		// 执行监听器
		Collection<SessionCloseListener> ccls = context.getBeansOfType(SessionCloseListener.class).values();
		for (Integer pId : SessionManager.onlines()) {
			for (SessionCloseListener scl : ccls) {
				try {
					scl.close(pId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		SessionManager.removeAll();
		
		// 服务器关闭监听
		Map<String, ServerCloseListener> scls = context.getBeansOfType(ServerCloseListener.class);
		scls = orderCloseListener(scls);
		for (ServerCloseListener listener : scls.values()) {
			if (listener.invokeStrategy() == 1) {
				listener.close();
			}
		}
		// 关闭任务处理器
		ActionTaskService.stop();
		for (ServerCloseListener listener : scls.values()) {
			if (listener.invokeStrategy() == 2) {
				listener.close();
			}
		}
		
		log.info("server stopped .");
	}

	private class ShutdownHook extends Thread {
		@Override
		public void run() {
			GameServer.gameServer.doStop();
		}
	}

	private Map<String, ServerStartupListener> orderStartupListener(Map<String, ServerStartupListener> listeners) {
		String serverStartupListenerOrder = CoreConfig.stringValue("ServerStartupListenerOrder");
		if (serverStartupListenerOrder == null || serverStartupListenerOrder.trim().length() == 0) {
			return new HashMap<>(listeners);
		}
		String[] arr = serverStartupListenerOrder.split(",");
		if (arr.length == 0) {
			return new HashMap<>(listeners);
		}

		Map<String, ServerStartupListener> copy = new HashMap<>(listeners);
		Map<String, ServerStartupListener> map = new LinkedHashMap<>();
		for (String s : arr) {
			ServerStartupListener ssl = copy.remove(s);
			if (ssl == null) {
				throw new RuntimeException("ServerStartupListenerOrder配置错误: " + s);
			}
			map.put(s, ssl);
		}
		map.putAll(copy);
		return map;
	}

	private Map<String, ServerCloseListener> orderCloseListener(Map<String, ServerCloseListener> listeners) {
		String serverStartupListenerOrder = CoreConfig.stringValue("ServerCloseListenerOrder");
		if (serverStartupListenerOrder == null || serverStartupListenerOrder.trim().length() == 0) {
			return new HashMap<>(listeners);
		}
		String[] arr = serverStartupListenerOrder.split(",");
		if (arr.length == 0) {
			return new HashMap<>(listeners);
		}

		Map<String, ServerCloseListener> copy = new HashMap<>(listeners);
		Map<String, ServerCloseListener> map = new LinkedHashMap<>();
		for (String s : arr) {
			ServerCloseListener scl = copy.remove(s);
			if (scl == null) {
				throw new RuntimeException("ServerCloseListenerOrder配置错误: " + s);
			}
			map.put(s, scl);
		}
		map.putAll(copy);
		return map;
	}
}
