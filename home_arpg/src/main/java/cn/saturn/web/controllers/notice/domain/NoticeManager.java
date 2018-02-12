package cn.saturn.web.controllers.notice.domain;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class NoticeManager implements ApplicationContextAware {

	private static NoticeDAO noticeDAO;

	/**
	 *
	 * @return {@link Notice}
	 */
	public static synchronized Notice getNoticeModel(String srvId) {
		Notice notice = null;
		if (RedisUtils.RedisNotice) {
			// 获取所有的 notice
			Map<String, String> map = RedisUtils.hgetAll(RedisKeys.K_NOTICE);
			// 如果 map 中为空,启动到数据库中加载
			if (map == null || map.isEmpty()) {
				List<Notice> models = noticeDAO.getEnables();
				if (models == null || models.isEmpty())
					return null;

				for (int i = 0; i < models.size(); i++) {
					Notice model = models.get(i);
					String[] srvStrs = model.getSrvs();

					if (srvStrs != null) {
						for (int j = 0; j < srvStrs.length; j++) {
							RedisUtils.hset(RedisKeys.K_NOTICE, srvStrs[j], JSON.toJSONString(model));
						}
					}
				}
			}

			// 获取 redis 中的数据
			String json = RedisUtils.hget(RedisKeys.K_NOTICE, srvId);
			

			try {
				notice = JSON.parseObject(json, Notice.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
           List<Notice> notices = noticeDAO.getEnables();
           Map<String, Notice> noticeMap=new HashMap<String,Notice>();
           int  noticesCount=(noticeDAO.getEnables() !=null)?noticeDAO.getEnables().size():0;
            if (noticesCount!=0) {
                for(Notice noticemodel:notices){
                	String[] srvs=noticemodel.getSrvs();
                	 
                	
                	if(srvs.length==0){
                		return null;
                	}
                	for(int i=0;i<srvs.length;i++){
                		noticeMap.put(srvs[i],noticemodel);
                	}
                }
            }
            notice=noticeMap.get(srvId); 
        }
		
		return notice;
	}

	/**
	 * 获取公告
	 *
	 * @return
	 */
	public static Notice getNotice(String srvId) {
		Notice notice = NoticeManager.getNoticeModel(srvId);
		if(null == notice ) return null;
		
		return notice;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		noticeDAO = applicationContext.getBean(NoticeDAO.class);
	}
}
