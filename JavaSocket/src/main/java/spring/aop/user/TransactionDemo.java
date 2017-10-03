package spring.aop.user;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Administrator
 *         <p>
 *         这个是一个切面的代码
 */
public class TransactionDemo {

	public void startTransaction() {
		System.out.println("startTransaction");
	}

	public void commitTransaction() {
		System.out.println("commit Transaction");
	}

	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("begin transaction");
		joinPoint.proceed();
		System.out.println("end   transaction");
	}
}
