package projectquartz.task;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by gexiaobing on 2019-03-29
 *
 * @description TODO
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TaskProxy extends QuartzJobBean implements Serializable {

    public static final String TASK_BEAN_ID = "task_bean_id";

    private static Logger logger = LoggerFactory.getLogger(TaskProxy.class);

    protected ApplicationContext applicationContext;

    /**
     * 从SchedulerFactoryBean注入的applicationContext.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String taskBeanId = jobDetail.getJobDataMap().getString(TASK_BEAN_ID);
        ITask task = applicationContext.getBean(taskBeanId, ITask.class);
        if(Objects.isNull(task)){
            throw new RuntimeException("不存在该任务");
        }
        try {
            logger.info("schedule start spring bean task[{}]:任务开始执行", taskBeanId);
            task.execute(jobExecutionContext);
            logger.info("schedule start spring bean task[{}]:任务结束执行", taskBeanId);
        }catch (Throwable e) {
            logger.error(e.getMessage(),e);
            logger.error("schedule start spring bean task[{}]:任务执行异常", taskBeanId);
        }

    }

}
