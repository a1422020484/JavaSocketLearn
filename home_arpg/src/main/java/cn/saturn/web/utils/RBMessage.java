package cn.saturn.web.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xiezuojie
 * 
 * 国际化消息
 * Resource Bundle Message
 */
@Component
public class RBMessage implements ApplicationContextAware {
	
	private static ResourceBundle bundle;
	
	/**
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		try {
			return bundle.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
			return "Unknown Message Key: " + key;
		}
	}
	
	/**
	 * @param key
	 * @param params
	 * @return 格式化后的内容
	 */
	public static String get(String key, Object ...params) {
		try {
			String msg = bundle.getString(key);
			return MessageFormat.format(msg, params);
		} catch (Exception e) {
			e.printStackTrace();
			return "Unknown Message key: " + key;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		bundle = ResourceBundle.getBundle("Message", Config.Locale);
	}
//	URL url = ResourceMonitor.class.getResource(CoreConfig.stringValue("ResourceFolder"));
//	if (url != null) {
//		File rootFile = new File(url.getPath());
//		if (rootFile.isDirectory()) {
//			resourcePath = rootFile.getPath();
//			init(rootFile);
//			prepare();
//		} else {
//			resourcePath = null;
//			log.error("the ResourceFolder({}) is not exists, but a same name file exists!", resourcePath);
//		}
//	} else {
//		log.error("the ResourceFolder({}) is not exists!", resourcePath);
//	}
}
