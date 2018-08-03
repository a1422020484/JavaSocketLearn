package uploadServerTool;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

public class SyncTask
  extends ExecTask
{
  private String syncPath;
  private String gamePath;
  private Session session;
  
  public SyncTask(Server server)
  {
    super(server);
    
    String filePath = server.getLocalPath();
    if ((filePath != null) && (filePath.length() > 0)) {
      this.syncPath = filePath;
    } else {
      this.syncPath = ((String)Config.instance.get("syncPath", "./"));
    }
    if (!this.syncPath.endsWith("/")) {
      this.syncPath += "/";
    }
    this.gamePath = server.uploadPath;
    if (!this.gamePath.endsWith("/")) {
      this.gamePath += "/";
    }
  }
  
  protected void _run(Session session)
  {
    this.session = session;
    File socketFile = new File(this.syncPath);
    if (!socketFile.exists())
    {
      upMsgln(1, "path '" + this.syncPath + "' not exists!");
      return;
    }
    if (!socketFile.isDirectory())
    {
      upMsgln(1, "path '" + this.syncPath + "socket/" + "' isn't directory!");
      return;
    }
    String zipFileName = "socket.zip";
    File socketZipFile = null;
    try
    {
      String socketZipFilePath = this.syncPath + "socket.zip";
      socketZipFile = new File(socketZipFilePath);
      if (!socketZipFile.exists())
      {
        upMsg(0, "zip packing '" + this.syncPath + "socket.zip" + "'");
        ZipFile zipFile = new ZipFile(this.syncPath + "socket.zip");
        

        File[] files = socketFile.listFiles();
        for (File file : files) {
          if (file.exists()) {
            if (file.isDirectory())
            {
              ZipParameters parameters = new ZipParameters();
              parameters.setCompressionMethod(8);
              parameters.setCompressionLevel(5);
              zipFile.addFolder(file, parameters);
            }
            else if (file.isFile())
            {
              ZipParameters parameters = new ZipParameters();
              parameters.setCompressionMethod(8);
              parameters.setCompressionLevel(5);
              zipFile.addFile(file, parameters);
            }
          }
        }
        upMsgFinish();
      }
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(socketZipFile.lastModified());
      
      upMsgln(0, socketZipFilePath + " " + sdf.format(cal.getTime()));
      upMsg(0, "upload '" + socketZipFilePath + "' to " + this.gamePath);
      upFile(socketZipFile, this.gamePath);
      upMsgFinish();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      
      upMsgln(1, "\tfailure! " + e.toString());
      
      socketZipFile.delete();
      socketZipFile.deleteOnExit();
      return;
    }
    exec(session, "unzip -o " + this.gamePath + "socket.zip" + " -d " + this.gamePath);
    exec(session, "rm -f " + this.gamePath + "socket.zip");
    

    socketZipFile.delete();
    socketZipFile.deleteOnExit();
  }
  
  void upFile(File _lfile, String rfile)
    throws Exception
  {
    if (_lfile.isDirectory())
    {
      File[] files = _lfile.listFiles();
      for (File f : files) {
        if (!f.getName().startsWith("."))
        {
          rfile = this.gamePath + f.getPath().substring(this.syncPath.length());
          if ((f.isDirectory()) && 
            (!f.getPath().endsWith("/"))) {
            rfile = rfile + "/";
          }
          upFile(f, rfile);
        }
      }
      return;
    }
    String command = "scp -Cp -t " + rfile;
    Channel channel = this.session.openChannel("exec");
    ((ChannelExec)channel).setCommand(command);
    

    OutputStream out = channel.getOutputStream();
    InputStream in = channel.getInputStream();
    
    channel.connect();
    if (checkAck(in) != 0) {
      throw new RuntimeException("连接失败");
    }
    String lfile = _lfile.getPath();
    String fileName = _lfile.getName();
    long filesize = _lfile.length();
    
    command = "C0644 " + filesize + " " + fileName + "\n";
    out.write(command.getBytes());
    out.flush();
    checkAck(in);
    

    FileInputStream fis = new FileInputStream(lfile);
    byte[] buf = new byte[1024];
    for (;;)
    {
      int len = fis.read(buf, 0, buf.length);
      if (len <= 0) {
        break;
      }
      out.write(buf, 0, len);
    }
    fis.close();
    fis = null;
    

    buf[0] = 0;
    out.write(buf, 0, 1);
    out.flush();
    
    checkAck(in);
    out.close();
    channel.disconnect();
  }
  
  static int checkAck(InputStream in)
    throws Exception
  {
    int b = in.read();
    if (b == 0) {
      return b;
    }
    if (b == -1) {
      return b;
    }
    if ((b == 1) || (b == 2))
    {
      StringBuffer sb = new StringBuffer();
      int c;
      do
      {
        c = in.read();
        sb.append((char)c);
      } while (c != 10);
      if (b == 1) {
        throw new RuntimeException(sb.toString());
      }
      if (b == 2) {
        throw new RuntimeException(sb.toString());
      }
    }
    return b;
  }
  
  protected String[] getCommand()
  {
    return null;
  }
}
