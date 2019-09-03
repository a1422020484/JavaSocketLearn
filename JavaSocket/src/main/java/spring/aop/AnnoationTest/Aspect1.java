package spring.aop.AnnoationTest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Aspect1 {
	@Pointcut("execution(public * spring.aop.AnnoationTest.*(..))")
	public void recordLog() {
	}

	@Before(value = "recordLog()")
	public void before(JoinPoint joinPoint) {
		System.out.println("[Aspect1] before advise");
	}

	@Around(value = "recordLog()")
	public void around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("[Aspect1] around advise 1");
		pjp.proceed();
		System.out.println("[Aspect1] around advise2");
	}

	@AfterReturning(value = "recordLog()")
	public void afterReturning(JoinPoint joinPoint) {
		System.out.println("[Aspect1] afterReturning advise");
	}

	@AfterThrowing(value = "recordLog()")
	public void afterThrowing(JoinPoint joinPoint) {
		System.out.println("[Aspect1] afterThrowing advise");
	}

	@After(value = "recordLog()")
	public void after(JoinPoint joinPoint) {
		System.out.println("[Aspect1] after advise");
	}
}
