package nettyServer.util;

import java.math.BigDecimal;

/**
 * 
 *
 * @author xiezuojie
 */
public class NumericUtil {
	
	/**
	 * 计算四舍五入
	 * 
	 * @param v
	 * @return 四舍五入后的值
	 */
	public static int roundHalfUp(double v) {
		return new BigDecimal(v).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
}
