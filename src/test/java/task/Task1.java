package task;

import com.af.annotation.Task;
import com.af.taskhandle.TaskHelper;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Created by deng on 16-7-11.
 */
public class Task1 {

    private TaskHelper taskHelper;

    @Scheduled(cron="0/4 * * * * ?")
    @Task(redisKey="testTask1", expireSecondTime = 4)
    public void excute() {
        Boolean flag = taskHelper.isRun(Task1.class);
        if(flag) {
            System.out.println((new Date() + "  task1 run"));
        }
    }

    public void setTaskHelper(TaskHelper taskHelper) {
        this.taskHelper = taskHelper;
    }

}
