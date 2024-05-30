package test;

import java.math.BigDecimal;

public class Long {

	private static final float ff = 0.0001f;
	private static final int ii = 10000;


	public static void main(String[] args) {
//		BigDecimal a1 = new BigDecimal(ff);
//		BigDecimal b1 = new BigDecimal(ii);
//		System.out.println();

		for (float i = 58866; i < 58890; i++) {
			System.out.println((int) (i * ii / ii));
			System.out.println("==============================");
		}
	}
}
