package nettyServer.util.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import nettyServer.util.CoreConfig;
import nettyServer.util.LogKey;

/**
 * @author yangxp
 */
public class ResourceMonitor implements ApplicationContextAware {
	private static final Logger log = LoggerFactory.getLogger(LogKey.CORE);

	/**
	 * 资源文件后缀
	 */
	private String[] resourceFileSuffix = CoreConfig.stringValue("ResourceSuffix").split("\\,");

	/**
	 * 资源文件目录路径
	 */
	private String resourcePath;
	private Map<String, ResourceLoaderWrapper> loaders = new HashMap<String, ResourceLoaderWrapper>();

	private void init(File rootDirectory) {
		File[] files = rootDirectory.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				init(file);
			String name = file.getName();
			for (String suffix : resourceFileSuffix) {
				if (name.endsWith(suffix)) {
					String resource = name.substring(0, name.lastIndexOf("."));
					ResourceLoaderWrapper wrapper = loaders.get(resource);
					if (wrapper != null && wrapper.loader != null)
						wrapper.file = file;
					break;
				}
			}
		}
	}

	private void prepare() {
		List<ResourceLoaderWrapper> list = new ArrayList<>(loaders.values());
		Collections.sort(list, new Comparator<ResourceLoaderWrapper>() {
			@Override
			public int compare(ResourceLoaderWrapper o1, ResourceLoaderWrapper o2) {
				return o1.priority - o2.priority;
			}
		});
		for (ResourceLoaderWrapper wrapper : list) {
			InputStream is = null;
			try {
				if (wrapper.file == null)
					throw new RuntimeException(wrapper.resourceName + "对应的资源文件没有找到.");
				is = new FileInputStream(wrapper.file);
				wrapper.loader.load(is);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
			log.info("resource '{}' loaded.", wrapper.resourceName);
		}
	}

	/**
	 * 启动监听
	 */
	public void monitoring() {
		if (resourcePath == null) {
			log.info("resource monitoring failed, ResourceFolder({}) is not a valid directory!", resourcePath);
			return;
		}
		FileAlterationObserver observer = new FileAlterationObserver(resourcePath, FileFilterUtils.and(FileFilterUtils.fileFileFilter(), new SuffixFileFilter(resourceFileSuffix)), null);
		observer.addListener(new FileListener());
		FileAlterationMonitor monitor = new FileAlterationMonitor(CoreConfig.longValue("ResourcesMonitorInterval"), observer);
		try {
			monitor.start();
			log.info("resource monitoring started, path:{}", resourcePath);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("resource monitoring failed, msg:{}", e.getMessage());
		}
	}

	class FileListener extends FileAlterationListenerAdaptor {
		private void fireLoad(File file) {
			String name = file.getName();
			String resourceName = name;
			if (name.indexOf(".") > 0)
				resourceName = name.substring(0, name.lastIndexOf("."));
			ResourceLoaderWrapper wrapper = loaders.get(resourceName);
			if (wrapper != null) {
				wrapper.load();
				if (wrapper.loader.afterLoad() != null) {
					ResourceLoaderWrapper after = loaders.get(wrapper.loader.afterLoad());
					if (after != null) {
						after.load();
					}
				}
			}
		}

		@Override
		public void onFileCreate(File file) {
			fireLoad(file);
		}

		@Override
		public void onFileChange(File file) {
			fireLoad(file);
		}

		@Override
		public void onFileDelete(File file) {
		}
	}

	static class ResourceLoaderWrapper {
		ResourceLoader loader;
		File file;
		String resourceName;
		int priority;

		void load() {
			if (loader == null) {
				log.error("can't find binded ResourceLoader for resourceName: {}", resourceName);
				return;
			}
			// log.info("resource '{}' loading.", resourceName);
			InputStream is = null;
			try {
				is = new FileInputStream(file);
				loader.load(is);
				log.info("resource '{}' loaded.", resourceName);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("resource '{}' load error.", resourceName);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
		}

		@Override
		public String toString() {
			return "ResourceLoaderWrapper [loader=" + loader + ", file=" + file + ", resourceName=" + resourceName + ", priority=" + priority + "]";
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, ResourceLoader> loaderMap = applicationContext.getBeansOfType(ResourceLoader.class);
		for (ResourceLoader loader : loaderMap.values()) {
			ResourceLoaderWrapper wrapper = new ResourceLoaderWrapper();
			wrapper.loader = loader;
			wrapper.resourceName = loader.getResourceName();
			wrapper.priority = loader.getPriority();
			loaders.put(loader.getResourceName(), wrapper);
		}
		URL url = ResourceMonitor.class.getResource(CoreConfig.stringValue("ResourceFolder"));
		if (url != null) {
			File rootFile = new File(url.getPath());
			if (rootFile.isDirectory()) {
				resourcePath = rootFile.getPath();
				init(rootFile);
				prepare();
			} else {
				resourcePath = null;
				log.error("the ResourceFolder({}) is not exists, but a same name file exists!", resourcePath);
			}
		} else {
			log.error("the ResourceFolder({}) is not exists!", resourcePath);
		}
	}
}
