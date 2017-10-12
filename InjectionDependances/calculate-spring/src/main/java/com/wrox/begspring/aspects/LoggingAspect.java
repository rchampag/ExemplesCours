package com.wrox.begspring.aspects;

import org.aspectj.lang.JoinPoint;

public class LoggingAspect {

    public void logMethodExecution(JoinPoint jp) {
        System.out.println("AOP logging -> " + jp.toShortString());

    }

}