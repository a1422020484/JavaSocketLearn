package cn.saturn.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

/**
 * 文件工具<br>
 * 
 * @author rodking<br>
 *
 */
public class FileUtils {

	/**
	 * 获得目录下的所有文件名称 <br>
	 * 
	 * @param folder
	 * @return
	 */
	public static List<String> getFileNames2Direct(String folder) {
		File file = new File(folder);
		List<String> fileNames = new ArrayList<>();
		if (file.isDirectory()) {
			File[] fs = file.listFiles();
			for (File f : fs)
				fileNames.add(f.getName());
		}
		return fileNames;
	}

	/**
	 * 删除文件夹下的所有文件 <br>
	 * 
	 * @param floder
	 * @return
	 */
	public static boolean delAllFile2Direct(File dir) {
		if (dir.isDirectory()) {
			String[] childrens = dir.list();

			// 递归下面的目录
			for (String children : childrens) {
				boolean result = delAllFile2Direct(new File(dir, children));
				if (!result)
					return false;
			}
		}
		return dir.delete();
	}

	/**
	 * 创建目录 <br>
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean createDir(File dir) {
		return dir.mkdir();
	}

	/**
	 * 将文件列表下的文件拷贝到指定目录下<br>
	 * 
	 * @param srcFileNames
	 * @param srcFolder
	 * @param dst
	 * @return
	 */
	public static boolean copyFile2Dir(String[] srcFileNames, String srcFolder, File dst) {
		InputStream in = null;
		OutputStream out = null;
		for (String srcFileName : srcFileNames) {
			String srcFilePath = srcFolder + srcFileName;
			try {
				in = new FileInputStream(new File(srcFilePath));
				out = new FileOutputStream(dst);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();

			} catch (IOException e) {
			} finally {
				try {
					if (in != null)
						in.close();
					if (out != null)
						out.close();
				} catch (Exception e) {
				}
			}
		}

		return true;
	}

	public final static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";

	@SuppressWarnings("finally")
	public static boolean writeXmlFile(XStream xs, String path, List list) {
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(new File(path)), "UTF-8");
			String body = xs.toXML(list);
			body = XML_HEAD + body.replace("__", "_");
			out.write(body.toCharArray());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}
	}
}
