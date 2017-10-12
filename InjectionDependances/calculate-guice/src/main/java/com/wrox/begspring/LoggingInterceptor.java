package com.wrox.begspring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LoggingInterceptor implements MethodInterceptor {
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("LoggingInterceptor -> "
				+ invocation.getMethod().toString());
		return invocation.proceed();
	}

}