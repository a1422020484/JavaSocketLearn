package cn.saturn.web.code.login.domain;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;

@Component
public class ShieldSysManager implements ApplicationContextAware {

	private static ShieldSysDAO shieldSysDAO;

	/**
	 * @param version
	 * @return ShieldMode;
	 */
	public static ShieldModel getShieldSysList(String version) {

		// 查找全局 RedisKey 是否为空
		Map<String, String> obj = null;
		if (RedisUtils.RedisShieldSys) {
			obj = RedisUtils.hgetAll(RedisKeys.K_SHIELD_SYS);
		}

		// 查找 version 是否屏蔽
		ShieldModel model = null;
		if (RedisUtils.RedisShieldSys && obj != null && !obj.isEmpty()) {
			String json = obj.get(version);
			if (json != null) {
				try {
					model = JSON.parseObject(json, ShieldModel.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// 如果 redis 集合中没有数据,就到数据库中加载数据
		if (obj == null || obj.isEmpty()) {
			List<ShieldSysModel> listDAO = shieldSysDAO.getList();
			if (listDAO != null && listDAO.size() > 0) {
				ShieldModel modelTemp = null;
				// 改变为更简洁的数据结构
				for (int i = 0; i < listDAO.size(); i++) {
					modelTemp = ShieldModel.create(listDAO.get(i));
					if(RedisUtils.RedisShieldSys)
					RedisUtils.hset(RedisKeys.K_SHIELD_SYS, modelTemp.getVersion(), JSON.toJSONString(modelTemp));
					if (modelTemp.version.equals(version))
						model = modelTemp;
				}
			}
		}

		return model;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		shieldSysDAO = applicationContext.getBean(ShieldSysDAO.class);
	}

}
