package uploadServerTool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class Config {
	protected static final Locale locale = new Locale("zh", "CN");
	public static final Config instance = new Config("conf");
	protected Map<String, String> map = new HashMap();
	protected String filePath;
	protected boolean load = false;

	public Config() {
	}

	public Config(String filePath) {
		reload(filePath);
		this.load = true;
	}

	public boolean reload() {
		if (this.filePath == null) {
			return false;
		}
		if (!this.load) {
			reload(this.filePath);
			this.load = true;
		}
		return true;
	}

	public void release() {
		this.load = false;
	}

	public void putAll(Map<String, String> args) {
		this.map.putAll(args);
	}

	public boolean reload(String filePath) {
		this.filePath = filePath;
		this.map.clear();

		ResourceBundle bundle = null;
		try {
			bundle = ResourceBundle.getBundle(filePath, locale);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Iterator<String> iter = bundle.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = bundle.getString(key);
			this.map.put(key, value);
		}
		return true;
	}

	public <T> T get(String key, Class<T> clazz) {
		String value = (String) this.map.get(key);
		if (value == null) {
			return ObjectUtils.defualtValue(clazz);
		}
		return ObjectUtils.baseValue(value, clazz);
	}

	protected <T> T get0(String key, Class<T> clazz) {
		String value = (String) this.map.get(key);
		if (value == null) {
			return null;
		}
		return ObjectUtils.baseValue0(value, clazz);
	}

	public <T> T get(String key, T defualt) {
		Class<T> clazz = (Class<T>) defualt.getClass();

		T value = get0(key, clazz);
		if (value == null) {
			return defualt;
		}
		return value;
	}

	public short shortValue(String key) {
		return ((Short) get(key, Short.TYPE)).shortValue();
	}

	public int intValue(String key) {
		return ((Integer) get(key, Integer.TYPE)).intValue();
	}

	public long longValue(String key) {
		return ((Long) get(key, Long.TYPE)).longValue();
	}

	public float floatValue(String key) {
		return ((Float) get(key, Float.TYPE)).floatValue();
	}

	public double doubleValue(String key) {
		return ((Double) get(key, Double.TYPE)).doubleValue();
	}

	public boolean booleanValue(String key) {
		return ((Boolean) get(key, Boolean.TYPE)).booleanValue();
	}

	public String stringValue(String key) {
		return (String) this.map.get(key);
	}
}
