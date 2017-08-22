package com.apcmob.spring;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AnnotLoggingAspect {

	private Logger log = Logger.getLogger(getClass());

	@Before("execution(public * apcmob.front.service.*.*(..))")
	public String beforeLogging(JoinPoint joinPoint) {

		String className = joinPoint.getTarget().getClass().getSimpleName();

		String methodName = joinPoint.getSignature().getName();

		if (log.isDebugEnabled()) {
			log.debug("ClassName=" + className + "::::: " + methodName);
		}

		return methodName;
	}

	@AfterReturning(pointcut = "execution(public * apcmob.front.service.*.*(..))", returning = "ret")
	public void returningLogging(JoinPoint joinPoint, Object ret) {

		String methodName = joinPoint.getSignature().getName();

		if (log.isDebugEnabled()) {

			log.debug("called successfully: " + methodName);
		}
	}

	@AfterThrowing(pointcut = "execution(public * apcmob.front.service.*.*(..))", throwing = "ex")
	public void throwingLogging(JoinPoint joinPoint, Throwable ex) {

		String methodName = joinPoint.getSignature().getName();

		if (log.isDebugEnabled()) {
			log.debug("exception occured: " + methodName + " throws " + ex.getClass().getName());
		}
	}

	@After("execution(public * apcmob.front.service.*.*(..))")
	public void afterLogging(JoinPoint joinPoint) {

		String className = joinPoint.getTarget().getClass().getSimpleName();

		String methodName = joinPoint.getSignature().getName();

		if (log.isDebugEnabled()) {
			log.debug("ClassName=" + className + "::::: ?? " + methodName);
		}
	}

}
