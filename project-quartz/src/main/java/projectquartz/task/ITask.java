package projectquartz.task;

import org.quartz.JobExecutionContext;

/**
 * Created by gexiaobing on 2019-03-29
 *
 * @description TODO
 */
public interface ITask {

    /**
     * 执行任务
     * @param jobExecutionContext
     */
    void execute(JobExecutionContext jobExecutionContext);
}
