package cn.saturn.web.controllers.server.dao;

import java.util.List;
import org.springframework.stereotype.Component;
import zyt.spring.component.ComponentManager;
import cn.saturn.web.code.update.ServerTimer;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;

@Component
public class ServerOpenTimer extends ServerTimer {

	public ServerOpenTimer() {
		super(0, "", 60 * 1000);
	}

	@Override
	protected void update(int c) {

		long nowTimeL = System.currentTimeMillis();
		int nowTime = (int) (nowTimeL * 0.001);
		boolean update = false;

		// 遍历服务器列表
		ServerComponent serverComponent = ComponentManager.getComponent(ServerComponent.class);
		List<ServerModel> list = serverComponent.getList();
		int count = (list != null) ? list.size() : 0;
		for (int i = 0; i < count; i++) {
			ServerModel model = list.get(i);
			int openTime = model.getOpenTime();
			if (openTime <= 0) {
				continue; // 跳过
			}

			// 检测是否到了时间
			if (nowTime < openTime) {
				continue; // 还没到
			}

			// 开启
			model.setOpenTime(0); // 去掉时间
			// int state = model.getState();
			// if (state == ServerModel.srvState_Hide || state ==
			// ServerModel.srvState_Close) {
			model.setState(ServerModel.srvState_Normal);
			// }

			// 保存
			serverComponent.update(model);
			update = true;
		}

		if (update)
			RedisUtils.del(RedisKeys.K_SERVER);

	}
}
