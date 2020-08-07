package redis.redission;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import com.alibaba.fastjson.JSON;

import redis.redission.userpojo.User;

public class RedisObjectJson {
	
	public static final RedissonClient redissionClient = RedissionUtils.getInstence().getRedissionClient();
	
	public static void setJsonString(String key, User object) {
		String jsonObject = JSON.toJSONString(object);
		RBucket<Object> keyObject = redissionClient.getBucket(String.valueOf(object.getId()));
		keyObject.set(jsonObject);
	}

	public static Object getJsonObject(String key, User user) {
		RBucket<String> ObjectJson = redissionClient.getBucket(key);
		return JSON.parseObject(ObjectJson.get(), user.getClass());
	}
	
	

}
