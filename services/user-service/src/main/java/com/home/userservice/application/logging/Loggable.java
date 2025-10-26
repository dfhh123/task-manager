package com.home.userservice.application.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    LogLevel value() default LogLevel.INFO;
    boolean logArgs() default true;
    boolean logResult() default true;
    boolean logExecutionTime() default true;
}

enum LogLevel {
    DEBUG, INFO, WARN, ERROR
}
