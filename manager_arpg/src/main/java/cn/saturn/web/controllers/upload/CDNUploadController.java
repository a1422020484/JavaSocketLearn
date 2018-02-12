package cn.saturn.web.controllers.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.saturn.web.utils.ResultMsg;
import cn.saturn.web.utils.ResultMsgUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.ftp.ftpTools;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("cdn")
public class CDNUploadController {
	static final String folder = Utils.resourceFolder + "cdn/";

	@Get("")
	public String index(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "config", "");
		Utils.setAttributeValue(inv, "upLoadConfig", Utils.url(inv, "/upload/cdn/uploadConfig"));
		Utils.setAttributeValue(inv, "send", Utils.url(inv, "/upload/send"));
		return "cdn_list";
	}

	@Post("uploadConfig")
	public String upload(MultipartFile file) throws Throwable {
		if (file == null)
			return "@ file not found";

		List<ResultMsg> result = null;
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

			result = ftpTools.exec(new File(newPath));
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
		}

		String resultStr = ResultMsgUtils.getResult2Msg(result);
		return "@" + resultStr;
	}
}
