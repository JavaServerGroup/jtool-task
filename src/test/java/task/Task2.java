package task;

import com.af.taskhandle.TaskHelper;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Created by deng on 16-7-13.
 */
public class Task2 {
    private TaskHelper taskHelper;

    public void setTaskHelper(TaskHelper taskHelper) {
        this.taskHelper = taskHelper;
    }

    @Scheduled(cron="0/4 * * * * ?")
    public void excute2() {
        Boolean flag = taskHelper.isRun("testTask2", 4);
        if(flag) {
            System.out.println((new Date() + " task2 run"));
        }
    }
}
