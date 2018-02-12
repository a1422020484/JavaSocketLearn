package cn.saturn.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.saturn.web.controllers.statistics.dao.GoodsModel;

@Component
public class LogsReaderWriter {

	public void writeString(String str) throws Exception {

		File file = new File("\\user\\out.log");

		try (FileOutputStream output = new FileOutputStream(file)) {
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			byte[] contentInBytes = str.getBytes();
			output.write(contentInBytes);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param filePath
	 * @param serId
	 * @return 返回Map<Date, Map<Integer, Integer>>集合
	 * @throws Exception
	 */

	public Map<Date, Map<Integer, Integer>> readString(String strMsg) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		Map<Date, Map<Integer, Integer>> goodsmap = new HashMap<Date, Map<Integer, Integer>>();
		Map<Integer, Integer> intmap;
		String line = null;
		Date createtime = null;
		boolean flag = false;
		if (strMsg != null && strMsg != "") {
			String[] msg = strMsg.split("\n");
			for (int i = 0; i < msg.length - 1; i++) {
				line = msg[i];

				if (line.contains("//start::")) {
					try {
						String[] strtime = line.split("start::");
						String stime = strtime[1].trim();
						createtime = sdf.parse(stime);
						// createtime= DateUtils.addHour(timeafter, -1);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (line.contains("//all")) {
					// line = line + " \n ";
					flag = true;
				} else if (line.contains("//end")) {
					flag = false;

				} else if (flag) {
					String[] str = line.split("\\|");
					if (str.length >= 2) {

						Date pointHour = DateUtils.getPointHour(createtime);
						String goodsidstr = str[0];
						String quantitystr = str[1];
						int goodsid = Integer.parseInt(goodsidstr);
						int quantity = Integer.parseInt(quantitystr);

						intmap = goodsmap.get(pointHour);
						if (intmap == null) {
							intmap = new HashMap<Integer, Integer>();

							goodsmap.put(pointHour, intmap);
						}
						Integer count = intmap.get(goodsid);
						if (count == null) {
							count = 0;
						}
						count = count + quantity;
						intmap.put(goodsid, count);
					}
				}

			}
		}

		return goodsmap;
	}

	public List<GoodsModel> readStringToList(String strMsg, int type, int serverid) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<GoodsModel> goodsList = new ArrayList<GoodsModel>();
		String line = null;
		Date createtime = null;
		boolean flag = false;
		try {
			if (strMsg != null && strMsg != "") {
				String[] msg = strMsg.split("\n");
				for (int i = 0; i < msg.length - 1; i++) {
					line = msg[i];

					if (line.contains("//start::")) {
						try {
							String[] strtime = line.split("start::");
							String stime = strtime[1].trim();
							createtime = sdf.parse(stime);
							// createtime= DateUtils.addHour(timeafter, -1);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// line = line + " \n ";
						flag = true;
					} else if (line.contains("//end")) {
						flag = false;

					} else if (flag) {
						if (!line.isEmpty()) {
							String[] str = line.split("\\|");
							if (str.length >= 3) {

								Date pointHour = DateUtils.getPointHour(createtime);
								String playeridstr = str[0];
								String goodsidstr = str[1];
								String quantitystr = str[2].replaceAll("/t", "").replaceAll("/n", "").trim();
								// System.out.println(quantitystr);
								int playerid = Integer.parseInt(playeridstr);
								int goodsid = Integer.parseInt(goodsidstr);
								int quantity = Integer.parseInt(quantitystr);
								GoodsModel goodsModel = new GoodsModel();
								goodsModel.setPlayerid(playerid);
								goodsModel.setGoodsid(goodsid);
								goodsModel.setQuantity(quantity);
								goodsModel.setType(type);
								goodsModel.setServerid(serverid);
								goodsModel.setCreatetime(pointHour);
								goodsList.add(goodsModel);
							}
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodsList;
	}

}
