package cn.saturn.web.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import cn.saturn.web.utils.Config;
import cn.saturn.web.utils.ResultMsg;

public class ftpTools {
	private static final String cdnIp = Config.val("cdn.ip");
	private static final int cdnPort = Config.intVal("cdn.port");
	private static final String cdnName = Config.val("cdn.name");
	private static final String cdnPwd = Config.val("cdn.pwd");

	public static synchronized List<ResultMsg> exec(File file) {
		List<ResultMsg> result = new ArrayList<>();

		// TODO Auto-generated method stub
		JSch jsch = new JSch();
		try {

			Session session = jsch.getSession(cdnName, cdnIp, cdnPort);
			session.setPassword(cdnPwd);

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			session.connect(10000);
			session.disconnect();

			upFile(session, file, "/");

			result.add(new ResultMsg(ResultMsg.fail, file.getName() + " cdn 上传成功!"));

		} catch (JSchException e) {
			// System.out.println(e.getMessage());
			result.add(new ResultMsg(ResultMsg.fail, file.getName() + "cdn 连接失败!"));
		} catch (Exception e) {
			result.add(new ResultMsg(ResultMsg.fail, file.getName() + "cdn 上传文件失败!"));
		}

		return result;
	}

	public static void upFile(Session session, File _lfile, String rfile) throws Exception {

		// exec 'scp -t rfile' remotely
		String command = "scp -Cp -t " + rfile;
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(command);

		// get I/O streams for remote scp
		OutputStream out = channel.getOutputStream();
		InputStream in = channel.getInputStream();

		channel.connect();

		if (checkAck(in) != 0) {
			throw new RuntimeException();
		}

		String lfile = _lfile.getPath();
		String fileName = _lfile.getName();

		// send "C0644 filesize filename", where filename should not include
		// '/'
		long filesize = _lfile.length();
		command = "C0644 " + filesize + " " + fileName + "\n";
		out.write(command.getBytes());
		out.flush();
		checkAck(in);

		// send a content of lfile
		FileInputStream fis = new FileInputStream(lfile);
		byte[] buf = new byte[1024];
		while (true) {
			int len = fis.read(buf, 0, buf.length);
			if (len <= 0)
				break;
			out.write(buf, 0, len); // out.flush();
		}
		fis.close();
		fis = null;
		// send '\0'
		buf[0] = 0;
		out.write(buf, 0, 1);
		out.flush();

		checkAck(in);
		out.close();
		channel.disconnect();
	}

	public static int checkAck(InputStream in) throws Exception {
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if (b == 0)
			return b;
		if (b == -1) {
			return b;
		}

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				throw new RuntimeException(sb.toString());
			}
			if (b == 2) { // fatal error
				throw new RuntimeException(sb.toString());
			}
		}
		return b;
	}
}
