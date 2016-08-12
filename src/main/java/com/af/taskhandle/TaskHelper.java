package com.af.taskhandle;

import com.af.annotation.Task;
import com.af.exception.TaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created by deng on 16-7-12.
 * 使用注意TaskHelper isRun(Class<?> taskClazz)注意
 * 1、服务器时间同步
 * 2、redisKey唯一
 * 3、expireTime请设置为任务时间
 */
public class TaskHelper {

    private Logger logger = LoggerFactory.getLogger(TaskHelper.class);

    private RedisTemplate<String, String> redisTemplate;

    /**
     * 使用此方法注意
     * 1、每一个类中只定义一个任务方法
     * 2、expireTime 单位为s
     */
    public boolean isRun(Class<?> taskClazz) {
        Method[] methodArray = taskClazz.getMethods();
        Boolean setKeyFlag = false;
        for(Method method : methodArray) {
            Task task = method.getAnnotation(Task.class);
            if(task == null) {
                continue;
            }
            String redisKey = task.redisKey();
            long expireSecondTime = task.expireSecondTime();
            logger.debug("task method: {} run , task key: {}, task expireTime: {}", method.getName(), redisKey, expireSecondTime);
            if(expireSecondTime < 0) {
                throw new TaskException("expireTime不能小于0");
            }
            setKeyFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, redisKey);
            redisTemplate.expire(redisKey, expireSecondTime/2, TimeUnit.SECONDS);
            break;
        }
        return setKeyFlag;
    }

    /**
     * 使用此方法注意
     * 1、同一个类中可以定义多个任务
     * 2、expireTime 单位为s
     */
    public boolean isRun(String redisKey, long expireSecondTime) {
        if(redisKey == null || expireSecondTime < 0) {
            throw new TaskException("参数错误");
        }
        logger.debug("task key: {}, task expireTime: {}", redisKey, expireSecondTime);
        Boolean setKeyFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, redisKey);
        redisTemplate.expire(redisKey, expireSecondTime/2, TimeUnit.SECONDS);
        return setKeyFlag;
    }

    /**
     * 使用此方法注意
     * 1、同一个类中可以定义多个任务
     * 2、expireMilliseconds 单位为ms
     */
    public boolean isRunFastTask(String redisKey, long expireMillisecondTime) {
        if(redisKey == null || expireMillisecondTime < 0) {
            throw new TaskException("参数错误");
        }
        Boolean setKeyFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, redisKey);
        redisTemplate.expire(redisKey, expireMillisecondTime/2, TimeUnit.MILLISECONDS);
        return setKeyFlag;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
