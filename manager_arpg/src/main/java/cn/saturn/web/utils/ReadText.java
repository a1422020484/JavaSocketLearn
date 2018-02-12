package cn.saturn.web.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import cn.saturn.web.controllers.poke.dao.SendMailA;


@Component
public class ReadText{
	
	public static List<SendMailA>  readLine(String strMsg){
		
		List<SendMailA> saList = new ArrayList<SendMailA>();
		String line = null;
		boolean flag = false;
		try {
			if (strMsg != null && strMsg != "") {
				String[] msg = strMsg.split("\n");
				int SrvId=0;
				int sendId=0;
				for (int i = 0; i < msg.length - 1; i++) {
					line = msg[i];
					
					if (line.contains("//s")) {
						String srvIdStr=null;
						sendId=0;
						try {
							srvIdStr= line.replace("//s", "").replace(":", "").trim();
							SrvId=Integer.valueOf(srvIdStr);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// line = line + " \n ";
						flag = true;
					} else if (line.trim()==null ||  line.trim() == "") {
						flag = false;
					} else if (flag) {
						if (!line.isEmpty()) {
							sendId =sendId+1;
							String[] str = line.split("\\s+");
							if (str.length >= 3) {
								String pl = str[0];
								String na = str[1];
								String text = str[2];
								SendMailA sa=new SendMailA();
								sa.setSrvId(SrvId);
								sa.setPlayerId(Integer.valueOf(pl));
								sa.setPlayerName(na);
								sa.setExt(text);
								sa.setsId(sendId);
								saList.add(sa);
							}
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saList;
	}

}
