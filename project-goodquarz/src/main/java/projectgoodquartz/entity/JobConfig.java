package projectgoodquartz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author tengxiao
 * @since 2019-06-17
 */
@TableName("t_job_config")
public class JobConfig extends Model<JobConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 时间表达式
     */
    private String jobCron;

    /**
     * 类路径，全类型
     */
    private String jobClassPath;

    /**
     * 传递map参数
     */
    private String jobDateMap;

    /**
     * 状态：1 启用 0 停用
     */
    private Integer jobStatus;

    /**
     * 任务功能描述
     */
    private String jobDescribe;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public String getJobClassPath() {
        return jobClassPath;
    }

    public void setJobClassPath(String jobClassPath) {
        this.jobClassPath = jobClassPath;
    }

    public String getJobDateMap() {
        return jobDateMap;
    }

    public void setJobDateMap(String jobDateMap) {
        this.jobDateMap = jobDateMap;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobDescribe() {
        return jobDescribe;
    }

    public void setJobDescribe(String jobDescribe) {
        this.jobDescribe = jobDescribe;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "JobConfig{" +
        "id=" + id +
        ", jobName=" + jobName +
        ", jobGroup=" + jobGroup +
        ", jobCron=" + jobCron +
        ", jobClassPath=" + jobClassPath +
        ", jobDateMap=" + jobDateMap +
        ", jobStatus=" + jobStatus +
        ", jobDescribe=" + jobDescribe +
        "}";
    }
}
