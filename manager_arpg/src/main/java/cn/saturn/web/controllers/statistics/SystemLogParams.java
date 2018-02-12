package cn.saturn.web.controllers.statistics;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class SystemLogParams extends zyt.spring.component.resource.Resource {
	protected static Map<String, String> map;
	protected static List<String> keys;

	public SystemLogParams() {
		super();
		this.reload();
	}

	@Override
	protected boolean reload0() {
		// boolean result = super.reload0();
		// return result;

		String resourceFile = this.getResourceFile();
		if (resourceFile == null) {
			return false;
		}
		resourceFile = "/" + resourceFile;

		// 加载文件
		try {
			InputStream is = null;
			try {
				is = this.getClass().getResourceAsStream(resourceFile);
				this.reload(is);
			} finally {
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	protected void reload(InputStream is) throws Exception {
		// 读取数据
		int size = is.available();
		byte[] buffer = new byte[size];
		size = is.read(buffer, 0, size);

		// 转化成字符串
		String jsonStr = new String(buffer, "UTF-8");
		// System.out.println("jsonStr:" + jsonStr);

		@SuppressWarnings("unchecked")
		Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
		SystemLogParams.map = Collections.unmodifiableMap(map);
		SystemLogParams.keys = Collections.unmodifiableList(new ArrayList<>(map.keySet()));
	}

	@Override
	public String getResourceFile() {
		// return "/config.properties";
		return "//resource/systemLogParams.json";
	}

	public static String getParams(String key) {
		return (map != null) ? map.get(key) : null;
	}

	public static String getParams(int index) {
		if (keys == null) {
			return null;
		}
		// 检测是否超过上限
		if (index < 0 || index >= keys.size()) {
			return null;
		}
		// 获取key
		String key = keys.get(index);
		if (key == null) {
			return null;
		}
		// 获取参数
		return getParams(key);
	}

	public static String[] getKeys() {
		return (keys != null) ? keys.toArray(new String[0]) : null;
	}
}
