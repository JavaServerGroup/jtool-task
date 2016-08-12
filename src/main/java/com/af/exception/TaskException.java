package com.af.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by deng on 16-7-13.
 */
public class TaskException extends RuntimeException{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String message;

    public TaskException(String message) {
        super(message);
        this.message = message;
        logger.error(this.message + "\t" + this);
    }

    @Override
    public String toString() {
        return "TaskException{" +
                "message='" + message + '\'' +
                '}';
    }
}
