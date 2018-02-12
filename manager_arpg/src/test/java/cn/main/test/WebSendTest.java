package cn.main.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.tribes.util.Arrays;

import proto.ProtocolWeb;
import xzj.core.util.RSAUtils;
import zyt.utils.TimeUtils;
import cn.saturn.web.code.Head;
import cn.saturn.web.code.action.LoginAction;
import cn.saturn.web.controllers.server.dao.SaturnClient;

public class WebSendTest {

	public static void main(String[] args) {
		// String url = "http://203.195.254.101:9504";
		// String url = "http://127.0.0.1:9501";
		String url = "http://10.0.0.8:9501";
		try {
			SaturnClient client = new SaturnClient(url);

			ProtocolWeb.SetActivityWCS.Builder b = ProtocolWeb.SetActivityWCS.newBuilder();
			b.setEnable(0);
			b.setClazz(1);
			b.setOrder(1);
			b.setStartDayTime(0);
			b.setEndDayTime(0);
			b.setIntro("活动简介223");
			b.setTips("活动提示2222");
			b.setIcon("activity_banner_05");
			b.setSpeArg(1);
			// 充值返利
			// b.setId(1);
			// b.setTital("充值返还1111");
			// b.setType(ActivityInfoModel.activityType_cf);

			// // 通用活动
			// b.setId(2);
			// b.setTital("通用活动1111");
			// b.setType(ActivityInfoModel.activityType_normal);
			// // b.setType(ActivityInfoModel.activityType_phjl);

			// 通用活动
			b.setId(3);
			b.setTital("兑换1111");
			b.setType(15);
			// b.setType(ActivityInfoModel.activityType_phjl);
			// 条件
			List<String> requires = new ArrayList<>();
			requires.add("100000004_100");
			requires.add("100000004_1000");
			b.addAllRequires(requires);

			// 奖励
			List<String> awards = new ArrayList<>();
			awards.add("100000004_100;100000001_100");
			awards.add("100000004_300;100000001_200");
			b.addAllAwards(awards);
			// // 条件
			// List<String> requires = new ArrayList<>();
			// requires.add("100");
			// requires.add("1000");
			// b.addAllRequires(requires);

			ProtocolWeb.SetActivityWCS sendMsg = b.build();
			ProtocolWeb.SetActivityWSC retMsg = client.call(Head.H19001, sendMsg, ProtocolWeb.SetActivityWSC.class);
			System.out.println("------------:" + retMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
