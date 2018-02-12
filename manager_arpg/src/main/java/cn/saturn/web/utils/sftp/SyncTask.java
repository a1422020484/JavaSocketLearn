package cn.saturn.web.utils.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import cn.saturn.web.controllers.server.dao.ServerXFtpModel;
import cn.saturn.web.utils.LogType;

import com.jcraft.jsch.ChannelExec;
import xzj.core.util.CoreConfig;
import xzj.core.util.resource.ResourceMonitor;

public class SyncTask extends ExecTask {
	private final Logger log = LoggerFactory.getLogger(LogType.xftp);

	private String syncPath;
	private String gamePath;

	private Session session;

	public SyncTask(ServerXFtpModel xftp,String folder, AsyncAction a) {
		super(xftp, a);

		URL url = ResourceMonitor.class.getResource(CoreConfig.stringValue("ResourceFolder"));
		syncPath = url.getPath() + folder + "/socket/";
		gamePath = xftp.getGame_path() + "socket/";
	}

	@Override
	protected void _run(Session session) {
		this.session = session;
		File socketFile = new File(syncPath);
		if (!socketFile.exists()) {
			return;
		}
		if (!socketFile.isDirectory()) {
			return;
		}

		File socketZipFile = null;
		String socketZipFilePath = syncPath + "socket.zip";
		log.info("[ Send ] {}", socketZipFilePath);

		socketZipFile = new File(socketZipFilePath);
		try {
			upFile(socketZipFile, gamePath);
		} catch (Exception e) {
			e.printStackTrace();
//			socketZipFile.delete();
//			socketZipFile.deleteOnExit();
			return;
		}

		exec(session, "unzip -o " + gamePath + "socket.zip -d " + gamePath);
		exec(session, "rm -f " + gamePath + "socket.zip");
		log.info("[ Send ] command {}  {} ", "unzip", "rm");

//		this.action.add(new ResultMsg(ResultMsg.succ, xftp.getS_id() + "  发送成功!"));
	}

	void upFile(File _lfile, String rfile) throws Exception {
		if (_lfile.isDirectory()) {
			File[] files = _lfile.listFiles();
			for (File f : files) {
				if (f.getName().startsWith(".")) {
					continue;
				}
				rfile = gamePath + f.getPath().substring(syncPath.length());
				if (f.isDirectory()) {
					if (!f.getPath().endsWith("/")) {
						rfile += "/";
					}
				}
				upFile(f, rfile);
			}
			return;
		}

		// exec 'scp -t rfile' remotely
		String command = "scp -Cp -t " + rfile;
		Channel channel = session.openChannel("exec");
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(channel != null && channel.isConnected())
				channel.disconnect();
		}
	}

	static int checkAck(InputStream in) throws Exception {
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

	@Override
	protected String[] getCommand() {
		return null;
	}

}
