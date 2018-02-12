package cn.saturn.web.controllers.item.dao;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

import cn.saturn.operation.OperationExt;
import cn.saturn.operation.ReportSqlTemp;
import cn.saturn.web.controllers.chest.dao.ChestModel;
import cn.saturn.web.controllers.chest.dao.ChestModelManager;
import cn.saturn.web.controllers.item.dao.ItemModel;
import cn.saturn.web.controllers.item.dao.ItemModelManager;

//@Component
public class GoodsManager {

	public static List<GoodsItem> getAllGoods() {

		List<ItemModel> list1 = ItemModelManager.getModels();
		List<ChestModel> list2 = ChestModelManager.getModels();
		List<GoodsItem> GoodsItemList = new ArrayList<GoodsItem>();

		for (int i = 0; i < list1.size(); i++) {
			GoodsItem goods = new GoodsItem();
			ItemModel item = list1.get(i);
			goods.setId(item.getItemId());
			goods.setName(item.getItemName());
			GoodsItemList.add(goods);
		}

		for (int i = 0; i < list2.size(); i++) {
			GoodsItem goods = new GoodsItem();
			ChestModel item = list2.get(i);
			goods.setId(item.getChestId());
			goods.setName(item.getChestName());
			GoodsItemList.add(goods);
		}

		return GoodsItemList;
	}

	public static Map<Integer, String> getAllGoodsMap() {

		List<ItemModel> list1 = ItemModelManager.getModels();
		List<ChestModel> list2 = ChestModelManager.getModels();
		Map<Integer, String> goodsMap = new HashMap<Integer, String>();

		for (int i = 0; i < list1.size(); i++) {
			ItemModel item = list1.get(i);
			goodsMap.put(item.getItemId(), item.getItemName());
		}

		for (int i = 0; i < list2.size(); i++) {
			ChestModel item = list2.get(i);
			goodsMap.put(item.getChestId(), item.getChestName());
		}

		return goodsMap;
	}

	public static List<String[]> selectOut(String[] titleCH, List<String[]> out, Map<Integer, String> itemMap) {

		List<String[]> outer = new LinkedList<String[]>();
		int lengh = titleCH.length;

		for (int i = 0; i < out.size(); i++) {
			String[] StringMap = new String[lengh];
			/*if (i == 0) {
				for (int j = 0; j < titleCH.length - 1; j++) {
					if (j == 0) {
						String value = out.get(i)[j];
							StringMap[j] = value;
							StringMap[j + 1] = "name";;
					} else {
						String value = out.get(i)[j];
						StringMap[j + 1] = value;
						
					}
				}
			}*/ 
			//else {
				for (int j = 0; j < titleCH.length - 1; j++) {
					if (j == 0) {
						String value = out.get(i)[j];
						if (value == null || value.equals("")) {
							value = "";
							StringMap[j] = value;
							StringMap[j + 1] = value;
						} else {
							try {
								int Ids = Integer.valueOf(value);
								StringMap[j] = value;
								String names = itemMap.get(Ids);
								if (names == null || names.equals(""))
									names = "";
								StringMap[j + 1] = names;
							} catch (NumberFormatException e) {
								System.out.println("NumberFormatException");
								StringMap[j] = value;
								StringMap[j + 1] = value;
							}
						}

					} else {
						String value = out.get(i)[j];
						if (value == null || value.equals(""))
							value = "";
						StringMap[j + 1] = value;
					}
				}
			//}
			outer.add(StringMap);
		}

		return outer;
	}

	public static void main(String[] args) {
		String[] titleCH = { "id", "名字", "数量1","数量2" };
		String[] Str = { "id", "pot1","pot2" };
		String[] Str1 = { "19001", "191","" };
		String[] Str2 = { "19002", "192","1902" };
		List<String[]> Slist = new LinkedList<String[]>();
		Slist.add(Str);
		Slist.add(Str1);
		Slist.add(Str2);
		Map<Integer, String> itemMap = new HashMap<Integer, String>();
		itemMap.put(19001, "name1");
		itemMap.put(19002, "name2");
		List<String[]> outer = GoodsManager.selectOut(titleCH, Slist, itemMap);
		for (String[] out : outer) {

			for (int j = 0; j < out.length; j++) {
				System.out.println(out[j]);
			}
		}
	}

}
