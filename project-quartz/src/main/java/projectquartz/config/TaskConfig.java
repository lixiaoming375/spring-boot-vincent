package projectquartz.config;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import projectquartz.annotation.CronScheduled;
import projectquartz.task.ITask;
import projectquartz.task.TaskProxy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gexiaobing on 2019-03-29
 *
 * @description TODO
 */
@ConditionalOnClass({Scheduler.class, SchedulerFactoryBean.class,
        PlatformTransactionManager.class})
@Component
public class TaskConfig implements SchedulerFactoryBeanCustomizer {

    @Autowired
    private ApplicationContext context;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {

        List<Trigger> triggerList = new ArrayList<>();
        Map<String, ITask> taskMap = context.getBeansOfType(ITask.class);
        taskMap.forEach((taskName,task)->{
            if (!task.getClass().isAnnotationPresent(CronScheduled.class)){
                return;
            }

            CronScheduled cronScheduled = task.getClass().getAnnotation(CronScheduled.class);
            JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
            jobDetailFactoryBean.setJobClass(TaskProxy.class);
            jobDetailFactoryBean.setDurability(true);
            jobDetailFactoryBean.setName(task.getClass().getName());
            Map<String, Object> jobData = new HashMap();
            jobData.put(TaskProxy.TASK_BEAN_ID, taskName);
            jobDetailFactoryBean.setJobDataAsMap(jobData);
            jobDetailFactoryBean.afterPropertiesSet();

            CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
            cronTriggerFactoryBean.setCronExpression(cronScheduled.expression());
            cronTriggerFactoryBean.setName(taskName + "_trigger");
            cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
            try {
                cronTriggerFactoryBean.afterPropertiesSet();
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage(),e);
            }
            triggerList.add(cronTriggerFactoryBean.getObject());
        });

        schedulerFactoryBean.setTriggers(triggerList.toArray(new Trigger[triggerList.size()]));
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        schedulerFactoryBean.setAutoStartup(true);
    }
}
