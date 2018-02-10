package nettyServer.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nettyServer.util.RandomUtil;
import proto.ItemMsg;

/**
 * 奖励对象(物品集合)
 * <p/>
 * 1.用于批量删除物品和增加物品
 * <p/>
 * 2.提供发送邮件功能
 * 
 * @author zhuangyuetao
 */
public class Award {
    // DropModel.dropId
    public int dropId;
	// true表示内部数据不能修改
	private boolean model = false;
	// k: item's id v: count
	private final Map<Integer, Integer> itemList = new HashMap<>();

	public Award() {

	}

	public Award(List<ItemMsg> items) {
		int count = (items != null) ? items.size() : 0;
		for (int i = 0; i < count; i++) {
			ItemMsg itemMsg = items.get(i);
			int itemId = itemMsg.getItemId();
			int itemCount = itemMsg.getItemCount();

			itemList.put(itemId, itemCount);
		}
	}

	public Award(Award a) {
        dropId = a.dropId;
		itemList.putAll(a.itemList);
	}

	/**
	 * @param model
	 *            true表示内部数据不能修改
	 */
	public void setModel(boolean model) {
		this.model = model;
	}

	/**
	 * 成倍修改, 以取整为准
	 * 
	 * @param rate
	 *            修改比例
	 */
	public void toDoubled(float rate) {
		if (rate == 1.0f) {
			return; // 不改动
		}
		// 遍历检测
		Iterator<Map.Entry<Integer, Integer>> iter = itemList.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> entry = iter.next();
			// int itemId = entry.getKey();
			int count = entry.getValue();
			if (count != 0) {
				int setCount = (int) (count * rate);
				entry.setValue(setCount); // 更新值
			} else {
				iter.remove(); // 为空,删了吧.
			}
		}
	}

	public Award randomGet(int num) {
		Award a = new Award();
		List<Integer> l = new ArrayList<>(itemList.keySet());
		List<Integer> l2 = RandomUtil.randomGetList(l, num);
		if (l2.size() == 0) {
			return a;
		}
		for (Integer k : l2) {
			a.addItem(k, itemList.get(k));
		}
		return a;
	}

	/**
	 * @return 是否有奖励内容,true有,false没有
	 */
	public boolean hasItems() {
		return !itemList.isEmpty();
	}

	/**
	 * 获取物品数量
	 * 
	 * @param itemId
	 * @return 数量
	 */
	public int getItem(int itemId) {
		Integer count = itemList.get(itemId);
		if (count == null) {
			return 0;
		}
		return count;
	}
	
	public Map<Integer, Integer> getItemsMap() {
		return Collections.unmodifiableMap(itemList);
	}

	public void addItems(Award award) {
		if (model) {
			throw new RuntimeException("模型数据不能修改!");
		}
		for (Map.Entry<Integer, Integer> en : award.itemList.entrySet()) {
			addItem(en.getKey(), en.getValue());
		}
	}

	/**
	 * 增加物品数量
	 * 
	 * @param itemId
	 *            物品ID
	 * @param count
	 * @return
	 */
	public int addItem(int itemId, int count) {
		if (model) {
			throw new RuntimeException("模型数据不能修改!");
		}
		// 判断这个物品是否存在
		// ItemModel itemModel = ItemModelManager.get(itemId);
		// if (itemModel == null) {
		// return 0;
		// }

		// 不改变
		if (count == 0) {
			return 0;
		}

		// 读取数量
		Integer nowCount = itemList.get(itemId);
		if (nowCount == null) {
			// 新增
			itemList.put(itemId, count);
			return count;
		}

		// 累加
		int setCount = nowCount + count;
		itemList.put(itemId, setCount);
		return setCount;
	}

	/**
	 * 减少物品数量
	 * 
	 * @param itemId
	 *            物品ID
	 * @param count
	 * @return
	 */
	public int deleteItem(int itemId, int count) {
		if (model) {
			throw new RuntimeException("模型数据不能修改!");
		}

		Integer nowCount = itemList.get(itemId);
		if (nowCount == null) {
			return 0; // 不能再减少了
		}

		// 累加
		int setCount = nowCount + count;
		setCount = Math.max(setCount, 0);
		itemList.put(itemId, setCount);
		return setCount;
	}

	/**
	 * 移除某一类的物品
	 * 
	 * @param itemId
	 * @return
	 */
	public int removeItem(int itemId) {
		if (model) {
			throw new RuntimeException("模型数据不能修改!");
		}

		Integer nowCount = itemList.remove(itemId);
		return (nowCount != null) ? nowCount : 0;
	}


	/**
	 * 是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		boolean empty = itemList.isEmpty();
		if (empty) {
			return true;
		}
		// 遍历检测
		Iterator<Map.Entry<Integer, Integer>> iter = itemList.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> entry = iter.next();
			// int itemId = entry.getKey();
			int count = entry.getValue();
			if (count > 0) {
				return false; // 存在物品
			} else {
				iter.remove();
			}
		}

		return true;
	}

}
