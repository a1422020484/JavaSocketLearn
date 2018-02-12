package cn.saturn.web.controllers.mail.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class ThemeType extends zyt.spring.component.resource.Resource {
	private static List<String> types = new ArrayList<>();

	public ThemeType() {
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

		// 读取数据
		List<String> types = JSON.parseArray(jsonStr, String.class);
		ThemeType.types.clear();
		ThemeType.types.addAll(types);
		// System.out.println("types:" + types);
	}

	@Override
	public String getResourceFile() {
		// return "/config.properties";
		return "/resource/themeType.json";
	}

	public List<String> getTypes() {
		return types;
	}

	public static String[] getTypes0() {
		return types.toArray(new String[] {});
	}

	// @Override
	// public void setApplicationContext(ApplicationContext context) throws BeansException {
	// this.reload();
	// }

}
