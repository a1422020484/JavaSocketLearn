package cn.saturn.web.gamedata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemMsg {
	protected int id;
	protected int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static ItemMsg create(int id, int count) {
		ItemMsg itemMsg = new ItemMsg();
		itemMsg.setId(id);
		itemMsg.setCount(count);
		return itemMsg;
	}

	/** 解析物品字符串 **/
	public static List<ItemMsg> decode(String itemStr) {

		// 拆分编码
		String[] codeAry = itemStr.split(";");
		int codeCount = codeAry.length;
		if (codeCount <= 0) {
			return null;
		}

		// 编码解析
		List<ItemMsg> list = new ArrayList<>();
		for (int i = 0; i < codeCount; i++) {
			String code = codeAry[i];
			// 解析单个编码
			String[] values = code.split("_");
			if (values.length < 2) {
				return null;
			}
			try {
				// 读取数据
				int id = Integer.valueOf(values[0]);
				int count = Integer.valueOf(values[1]);
				list.add(ItemMsg.create(id, count));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		return list;
	}

	public static String encode(List<ItemMsg> list) {
		StringBuilder sb = new StringBuilder(64);
		// 遍历物品列表
		Iterator<ItemMsg> iter = list.iterator();
		while (iter.hasNext()) {
			ItemMsg itemMsg = iter.next();
			int id = itemMsg.getId();
			int count = itemMsg.getCount();
			if (count <= 0) {
				continue;
			}
			sb.append(id).append("_").append(count).append(";");
		}
		return sb.toString();
	}
}