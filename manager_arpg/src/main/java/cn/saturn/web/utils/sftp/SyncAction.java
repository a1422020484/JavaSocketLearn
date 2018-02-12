package cn.saturn.web.utils.sftp;

import java.io.File;
import java.net.URL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.LogType;
import cn.saturn.web.utils.ResultMsg;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import xzj.core.util.CoreConfig;
import xzj.core.util.resource.ResourceMonitor;

public class SyncAction extends AsyncAction {

	private final Logger log = LoggerFactory.getLogger(LogType.xftp);

	private String syncPath = "";

	@Override
	public ServerTask createTask(ServerXFtpModel s, String folder) {
		return new SyncTask(s, folder, this);
	}

	/**
	 * 压缩文件
	 */
	public boolean zipFile(String folder) {
		log.info("[ ZIP ] start socket.zip file.... ");
		// zip 压缩文件
		URL url = ResourceMonitor.class.getResource(CoreConfig.stringValue("ResourceFolder"));
		syncPath = url.getPath() + folder + "/socket/";
		File socketFile = new File(syncPath);
		if (!socketFile.exists()) {
			return false;
		}
		if (!socketFile.isDirectory()) {
			return false;
		}

		File socketZipFile = null;
		try {
			// 打包成 socket.zip
			String socketZipFilePath = syncPath + "socket.zip";
			socketZipFile = new File(socketZipFilePath);
			
			if (!socketZipFile.exists()) {
				ZipFile zipFile = new ZipFile(syncPath + "socket.zip");

				File[] files = socketFile.listFiles();
				for (File file : files) {
					if (file.exists()) {
						if (file.isDirectory()) {
							ZipParameters parameters = new ZipParameters();
							parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
							parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
							zipFile.addFolder(file, parameters);
						} else if (file.isFile()) {

							ZipParameters parameters = new ZipParameters();
							parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
							parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
							zipFile.addFile(file, parameters);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			socketZipFile.delete();
			socketZipFile.deleteOnExit();
			this.add(new ResultMsg(ResultMsg.fail, "压缩失败"));
			return false;
		}

		log.info("[ ZIP ] end socket.zip file.... ");
		return true;
	}

	public void beginRun(List<ServerXFtpModel> models, String folder) {
		if (zipFile(folder)) {
			beforeRun();
			AsyncAction action = null;
			try {
				for (ServerXFtpModel model : models) {
					try {
						ServerTask task = createTask(model, folder);
						task.run();
						if(action == null)
							action = task.getAction();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(action!=null)
				action.add(new ResultMsg(ResultMsg.succ, "发送完成!"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				afterRun();
			}
		}
	}

	@Override
	public void afterRun() {
		super.afterRun();
		if (!syncPath.endsWith("/")) {
			syncPath += "/";
		}
		File zipFile = new File(syncPath + "socket.zip");
		System.out.println("afterRun zipFile exists->"+zipFile.exists());
        if(zipFile.exists()){
        	zipFile.delete();
        	zipFile.deleteOnExit();
        }
		log.info("[ ZIP ] delete socket.zip file.... ");
	}
}
