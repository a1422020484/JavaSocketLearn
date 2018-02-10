package nettyServer.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机工具类
 * 
 * @author xiezuojie
 */
public final class RandomUtil {

	/**
	 * @return 随机的int值
	 */
	public static int randomInt() {
		return ThreadLocalRandom.current().nextInt();
	}

	/**
	 * @param n
	 *            随机范围,必须是正数
	 * @return 随机的int值,以0开始,小于n.
	 */
	public static int randomInt(int n) {
		return ThreadLocalRandom.current().nextInt(n);
	}

	/**
	 * @param low
	 * @param high
	 *            随机范围,必须是正数
	 * @return 随机的int值,以low开始,小于high.
	 */
	public static int randomInt(int low, int high) throws Exception {
		if(high < low){
			throw new Exception("random rang error");
		}
		return randomInt(high - low) + low;
	}

	/**
	 * @return 随机的float值
	 */
	public static float randomFloat() {
		return ThreadLocalRandom.current().nextFloat();
	}

	/**
	 * 在指定值范围内随机,包含小值,不包含大值,参数小值与大值无顺序要求.
	 * 
	 * @param s1 值1
	 * @param s2 值2
	 * @return 随机取的int值
	 */
	public static int randomInRange(int s1, int s2, boolean includeMax) {
		if (s1 == s2)
			return s2;
		if (s1 > s2) {
			int t = s1;
			s1 = s2;
			s2 = t;
		}
		if (includeMax) {
			s2 ++;
		}
		return ThreadLocalRandom.current().nextInt(s2 - s1) + s1;
	}
	
	/**
	 * 在指定值范围内随机,包含小值,不包含大值,参数小值与大值无顺序要求.
	 * <br>
	 * <b>只适合小于10000的值,并且只有两个小数</b>
	 * 
	 * @param s1 值1
	 * @param s2 值2
	 * @return 随机取的float值
	 */
	public static float randomInRange(float s1, float s2, boolean includeMax) {
		int i1 = (int) (s1 * 10000);
		int i2 = (int) (s2 * 10000);
		int ri = randomInRange(i1, i2, includeMax);
		return 1F * ri / 10000F;
	}
	
	/**
	 * 等同于执行inRandom(prob, 100)
	 * 
	 * @see {@link #inRandom(int, int)}
	 * @param prob
	 * @return 是否命中
	 */
	public static boolean inRandom(int prob) {
		return inRandom(prob, 100);
	}

	/**
	 * 执行一次随机,机率最大值是max,机率为prob,返回随机结果
	 * 
	 * @param prob
	 *            机率
	 * @param max
	 *            机率最大值
	 * @return 是否命中
	 */
	public static boolean inRandom(int prob, int max) {
		return randomInt(max) < prob;
	}
	
	/**
	 * 执行一次随机,范围是0.00~1.00,机率为prob,返回随机结果
	 * 
	 * @param prob
	 *            机率
	 * @return 是否命中
	 */
	public static boolean inRandom(float prob) {
		return inRandom((int) (prob * 100), 100);
	}
	
	/**
	 * 在数值段内随机取指定数量的值(包含min,不包含max),并且结果数组中不会有重复<br>
	 * 当max-min <= num - 1时,返回的就是从min到max的所有值组成的数组,否则随机取值.
	 * 
	 * 
	 * @param min 最小值,必须小于或等于max
	 * @param max 最大值
	 * @param num 取值数量
	 * @return 在指定范围内随机获取的数值数组
	 */
	public static int[] randomUnrepeated(int min, int max, int num) {
		if (min > max)
			throw new RuntimeException("参数min必须小于或等于max");
		int c = max - min;
		int[] rs = null;
		if (c <= num) {
			rs = new int[c];
			for (int i = 0; i < c; i ++) {
				rs[i] = i + min;
			}
		} else {
			rs = new int[num];
			List<Integer> l = new LinkedList<>();
			for (int i = min; i < max; i ++) {
				l.add(i);
			}
			for (int i = 0; i < num; i ++) {
				Integer v = l.get(ThreadLocalRandom.current().nextInt(l.size()));
				l.remove(v);
				rs[i] = v;
			}
		}
		return rs;
	}
	
	/**
	 * @param list
	 * @return 随机获取列表中的一个
	 */
	public static <T> T randomGet(List<T> list) {
		if (list.size() == 0) {
			return null;
		}
		return list.get(randomInt(list.size()));
	}
	
	/**
	 * @param list
	 * @param n
	 * @return 随机获取列表中的指定数量并返回这些数据
	 */
	public static <T> List<T> randomGetList(List<T> list, int n) {
		List<T> retList = new ArrayList<>(n);
		List<T> copy = new ArrayList<>(list);
		for (;;) {
			T t = randomGet(copy);
			if (t == null) {
				break;
			}
			copy.remove(t);
			retList.add(t);
			if (retList.size() == n) {
				break;
			}
		}
		
		return retList;
	}

	public static <T> List<T> randonWeightObject(List<T> objList, List<Integer> objWeight, int count)
	{
		if ((objList == null) || (objWeight == null) || (count <= 0) || (objList.size() != objWeight.size()) || (count > objList.size())) {
			throw new RuntimeException("random weight object exception");
		}

		List<Integer> objWeightStub = new ArrayList<>(objWeight.size());
		objWeightStub.addAll(objWeight);
		List<T> selObjList = new ArrayList<>(count);
		while (selObjList.size() < count)
		{
			int totalWeight = 0;
			List<Integer> fmtWeight = new ArrayList<>(objWeightStub.size());
			for (int i = 0; i < objWeightStub.size(); i++) {
				totalWeight += (objWeightStub.get(i));
				fmtWeight.add(totalWeight);
			}
			try
			{
				int randomWeight = randomInt(1, totalWeight);
				for (int i = 0; i < fmtWeight.size(); i++)
					if (randomWeight <= fmtWeight.get(i)) {
						selObjList.add(objList.get(i));
						objWeightStub.set(i, 0);
						break;
					}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return selObjList;
	}


	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i ++) {
			System.out.println(randomInRange(10, 14, false));
		}
	}
}
