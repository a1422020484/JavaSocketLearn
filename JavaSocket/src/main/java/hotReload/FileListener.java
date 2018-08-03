package hotReload;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import hotReload.ResourceMonitor.ResourceLoaderWrapper;

public class FileListener extends FileAlterationListenerAdaptor {
	
	
	
	private void fireLoad(File file) {
		String name = file.getName();
		String resourceName = name;
		if (name.indexOf(".") > 0)
			resourceName = name.substring(0, name.lastIndexOf("."));
		ResourceLoaderWrapper wrapper = ResourceMonitor.loaders.get(resourceName);
		if (wrapper != null) {
			wrapper.load();
			if (wrapper.loader.afterLoad() != null) {
				ResourceLoaderWrapper after = ResourceMonitor.loaders.get(wrapper.loader.afterLoad());
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
