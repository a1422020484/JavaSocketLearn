package cn.saturn.web.controllers.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import cn.saturn.web.controllers.poke.dao.PokeHunterModelManager;
import cn.saturn.web.controllers.server.dao.ServerXFtpDAO;
import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.FileUtils;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ServerUtils;
import cn.saturn.web.utils.StringExtUtil;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.sftp.SftpTools;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("")
public class UploadController {
	static final String folder = Utils.resourceFolder + "game/";
	/** 拷贝文件目录 */
	static final String copyFolder = Utils.resourceFolder + "send1/conf/resource/";

	@Resource
	ServerXFtpDAO serverXFtpDao;
	@Resource
	PokeHunterModelManager pokeHunterModelManager;

	@Get("")
	public String index(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "config", "");
		Utils.setAttributeValue(inv, "upLoadConfig", Utils.url(inv, "/upload/uploadConfig"));
		//Utils.setAttributeValue(inv, "send", Utils.url(inv, "/upload/send"));
		return "list";
	}

	@Post("uploadConfig")
	public String upload(MultipartFile file) throws Throwable {
		if (file == null)
			return "@ file not found";

		Reader reader = null;
		Writer writer = null;
		try {
			String newPath = folder + file.getOriginalFilename();
			// 一次读多个字符
			int tempchar;
			reader = new InputStreamReader(file.getInputStream());
			writer = new OutputStreamWriter(new FileOutputStream(new File(newPath)), "UTF-8");

			while ((tempchar = reader.read()) != -1) {
				writer.write(tempchar);
			}

			reader.close();
			writer.close();
			System.out.println(newPath);
		} catch (Exception e) {

		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
			}
			try {
				if (writer != null)
					writer.close();
			} catch (Exception e) {
			}
			
			pokeHunterModelManager.load();
		}

		return "@ upload ok!";
	}

	@Get("send")
	public String send(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "toSend", Utils.url(inv, "/upload/toSend"));
		// 获得目录下面的所有文件
		List<String> fileNames = FileUtils.getFileNames2Direct(folder);
		// 过滤指定文件
		fileNames = StringExtUtil.filter(fileNames, "chest.xml", "pokeHunter.xml");
		Utils.setAttributeValue(inv, "files", fileNames);

		return "send";
	}

	@Post("toSend")
	public String toSend(Invocation inv, @Param("file") String[] file, @Param("srvIds") String srvIdStrs)
			throws Throwable {

		// 删除目标文件中的内容
		File desFolder = new File(copyFolder);
		FileUtils.delAllFile2Direct(desFolder);
		FileUtils.createDir(desFolder);
		FileUtils.copyFile2Dir(file, folder, desFolder);

		// 服务器ID
		int[] srvIds0 = ServerUtils.getSrvIds(srvIdStrs);
		final int[] srvIds = (srvIds0 != null) ? srvIds0 : new int[0];

		List<ResultMsg> sConfMsg = new ArrayList<>();
		List<ServerXFtpModel> sModel = new ArrayList<>();
		// 要发送的服务器
		for (int srvId : srvIds) {
			ServerXFtpModel model = serverXFtpDao.getBySid(srvId);
			if (model != null)
				sModel.add(model);
			else
				sConfMsg.add(new ResultMsg(ResultMsg.fail, "服务器： " + srvId + " 没有配置"));
		}

		StringBuilder sb = new StringBuilder();
		List<ResultMsg> msgs = SftpTools.exec(sModel, "send1");
		// 发送的消息
		for (ResultMsg msg : msgs) {
			sb.append(msg.toHtmlString());
		}
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, sb.toString());
	}
}
