package com.pepper.spring.aop;

import com.pepper.spring.exception.PerformanceException;
import com.pepper.spring.pojo.Audience;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AudienceAroundAdvice implements MethodInterceptor {
    private Audience audience;

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            audience.takeSeats();
            audience.turnOffCellPhones();
            Object returnValue = invocation.proceed();
            audience.applaud();
            return returnValue;
        } catch (PerformanceException throwable) {
            audience.demandRefund();
            throw throwable;
        }
    }

}