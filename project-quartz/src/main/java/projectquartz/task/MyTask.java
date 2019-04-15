package projectquartz.task;

import org.quartz.JobExecutionContext;
import projectquartz.annotation.CronScheduled;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/4/815:24
 */
@CronScheduled(expression = "0/5 * * * * ?")
public class MyTask implements ITask {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("execute=---------"+ MyTask.class.getName());
    }
}
