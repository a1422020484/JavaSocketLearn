package cn.home.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zyt.utils.FileUtils;
import cn.saturn.web.controllers.server.dao.SectionModel;
import cn.saturn.web.controllers.server.dao.ServerModel;
import cn.saturn.web.utils.AuthorityUtils;

import com.alibaba.fastjson.JSON;

public class JsonTest {

	public static void resetAuthority() {

		String[] authoritynames = { "普通", "客服", "GM", "商务", "", "", "", "", "", "", "管理员" };
		String jsonStr = JSON.toJSONString(authoritynames);
		System.out.println(jsonStr);

		// 保存
		String outPath = "src/main/resources/resource/authorityName.json";
		FileUtils.saveFile(outPath, jsonStr, "UTF-8", false);

		Map<Integer, String[]> authoritys = new HashMap<Integer, String[]>();
		authoritys.put(0, new String[] { "log" });
		authoritys.put(1, new String[] { "log", "chat", "playerinfo", "bug", "message", "forbidden" });
		authoritys.put(2, new String[] { "log", "mail", "message", "game", "chat", "playerinfo", "bug", "gm", "forbidden" });
		authoritys.put(3, new String[] { "statistics" });
		jsonStr = JSON.toJSONString(authoritys);
		System.out.println(jsonStr);
		// List<String[]> authoritys = new ArrayList<String[]>();
		// authoritys.add(new String[] { "server", "mail" });

		outPath = "src/main/resources/resource/authorityMap.json";
		FileUtils.saveFile(outPath, jsonStr, "UTF-8", false);
	}

	public static void resetVindicatorIp() {

		String[] vindicatorIps = {

				// 自家ip
				"116.30.171.186",
				// 4399
				"120.42.46.66", "211.162.35.121",
				// 自家ip
				"119.137.34.1-119.137.34.255",
				// 联想
				"124.126.118.153",
				// UC IP白名单
				"14.152.64.73", "14.152.64.74", "183.232.46.73", "183.232.46.74", "122.13.84.73", "122.13.84.74",

				// 9游
				"221.228.102.128", "221.228.102.80", "221.228.102.96", "112.25.246.128", "211.103.25.32", "211.103.26.112", "153.35.88.128", "153.35.91.112", "221.6.115.224",
				// 9游 IP白名单段
				"221.228.102.129-221.228.102.254", "221.228.102.81-221.228.102.94", "221.228.102.97-221.228.102.126", "112.25.246.129-112.25.246.254", "211.103.25.33-211.103.25.62",
				"211.103.26.113-211.103.26.126", "153.35.88.129-153.35.88.254", "153.35.91.113-153.35.91.126", "221.6.115.225-221.6.115.254",

				// 华为
				"183.62.195.194",
				// 拇指玩
				"203.100.92.81", };
		String jsonStr = JSON.toJSONString(vindicatorIps);
		System.out.println(jsonStr);

		String outPath = "src/main/resources/resource/vindicatorIp.json";
		FileUtils.saveFile(outPath, jsonStr, "UTF-8", false);
	}

	public static void main(String[] args) {

		// String[] types = { "全部", "系统", "建筑", "冒险", "商城", "铁匠铺", "光之神殿", "任务", "好友", "竞技场", "掠夺战" };
		//
		// String jsonStr = JSON.toJSONString(types);
		// System.out.println(jsonStr);

		// SectionModel model = new SectionModel();
		// model.setId(1);
		// model.setName("123");
		// model.setPlatforms("123");
		// model.setRecommend(false);
		// model.setState(0);
		// model.setTag("123");

		// ServerModel model = new ServerModel();
		// model.setId(0);
		// model.setMaintainText("123");
		// model.setName("213");
		// model.setOperate(true);
		// model.setRemark("123213");
		// model.setSection(1);
		//
		// String jsonStr02 = JSON.toJSONString(model);
		// System.out.println(jsonStr02);

		// String[] authoritynames = { "常规", "圣诞", "元旦", "春节", "情人节" };
		// jsonStr = JSON.toJSONString(authoritynames);
		// System.out.println(jsonStr);
		//
		// Map<String, String> params = new HashMap<>();
		// params.put("排行榜", "rankings_{0}.log");
		//
		// jsonStr = JSON.toJSONString(params);
		// System.out.println(jsonStr);

		// resetAuthority();
		resetVindicatorIp();
	}
}
