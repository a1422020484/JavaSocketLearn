package nettyServer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 计算概率工具<br>
 * 
 * @author zuojie.x
 */
public class Probability<T> {
	private int probabilityRange; // 默认机率范围
	private int count; // 计数器,机率是从小到大累加的
	private final List<ProbabilityItem> itemList;

	/**
	 * 默认机率范围为100
	 */
	public Probability() {
		this(100);
	}

	/**
	 * @param probabilityRange
	 *            机率范围
	 */
	public Probability(int probabilityRange) {
		this.probabilityRange = probabilityRange;
		this.itemList = new ArrayList<>();
	}

	/**
	 * 添加一个指定出现机率的对象
	 * 
	 * @param probability
	 *            机率
	 * @param object
	 *            对象
	 * @return
	 */
	public Probability<T> add(int probability, T object) {
		ProbabilityItem item = new ProbabilityItem(count + 1, count + probability, object);
		itemList.add(item);
		count += probability;
		return this;
	}

	/**
	 * 以probabilityRange为最大值随机一个值,计算这个值匹配的机率对应的对象,并返回
	 * 
	 * @return 有一定机率出现的值,如果没有值那么返回null
	 */
	public T random() {
		if (probabilityRange <= 0) {
			return null;
		}
		// 列表已准备好,用随机值找匹配的
		int probability = ThreadLocalRandom.current().nextInt(probabilityRange) + 1; // 以1为起点计算
		// System.out.println("probability:" + probability + " list>" +
		// itemList);
		for (ProbabilityItem item : itemList) {
			if (item.isIn(probability))
				return item.getObj();
		}
		return null;
	}
	
	/**
	 * 在添加的所有单位中按权重值随机选择
	 * 
	 * @return 在没有添加任何单位时返回null
	 */
	public T random2() {
		if (count <= 0) {
			return null;
		}
		int probability = ThreadLocalRandom.current().nextInt(count) + 1; // 以1为起点计算
		// System.out.println("probability:" + probability + " list>" +
		// itemList);
		for (ProbabilityItem item : itemList) {
			if (item.isIn(probability))
				return item.getObj();
		}
		return null;
	}

	private class ProbabilityItem {
		// 范围起点 终点
		int rangeS, rangeE;
		T obj;

		ProbabilityItem(int rangeS, int rangeE, T obj) {
			this.rangeS = rangeS;
			this.rangeE = rangeE;
			this.obj = obj;
		}

		boolean isIn(int probability) {
			return rangeS <= probability && probability <= rangeE;
		}

		T getObj() {
			return obj;
		}

		@Override
		public String toString() {
			return "ProbabilityItem [rangeS=" + rangeS + ", rangeE=" + rangeE + ", obj=" + obj + "]";
		}
	}

	/**
	 * 清空之前添加的值
	 */
	public void clear() {
		count = 0;
		itemList.clear();
	}

	/**
	 * 设置机率范围
	 *
	 * @param probabilityRange
	 */
	public void setProbabilityRange(int probabilityRange) {
		this.probabilityRange = probabilityRange;
	}
}
