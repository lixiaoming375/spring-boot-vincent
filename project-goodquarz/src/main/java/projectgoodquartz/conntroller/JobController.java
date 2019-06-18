package projectgoodquartz.conntroller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import projectgoodquartz.Service.impl.JobConfigServiceImpl;
import projectgoodquartz.entity.JobConfig;
import projectgoodquartz.exception.BizException;
import projectgoodquartz.util.ResultData;
import projectgoodquartz.util.SchedulerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/6/1713:53
 */

@Controller()
@RequestMapping("/job")
public class JobController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobConfigServiceImpl jobConfigService;

    /**
     * 查询任务列表（带分页）
     *
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    @ResponseBody
    public ResultData queryJobList(HttpServletRequest request, HttpServletResponse response) {
        String idStr = request.getParameter("id");
        String jobName = request.getParameter("jobName");
        String jobGroup = request.getParameter("jobGroup");
        String jobCron = request.getParameter("jobCron");
        String jobClassPath = request.getParameter("jobClassPath");
        String jobDescribe = request.getParameter("jobDescribe");

        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(idStr)) {
            map.put("id", idStr);
        }
        if (StringUtils.isNotBlank(jobName)) {
            map.put("job_name", jobName);
        }
        if (StringUtils.isNotBlank(jobGroup)) {
            map.put("job_group", jobGroup);
        }
        if (StringUtils.isNotBlank(jobCron)) {
            map.put("job_cron", jobCron);
        }
        if (StringUtils.isNotBlank(jobClassPath)) {
            map.put("job_class_path", jobClassPath);
        }
        if (StringUtils.isNotBlank(jobDescribe)) {
            map.put("job_describe", jobDescribe);
        }

        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        if(page>=1){
            page = (page-1)*limit;
        }
        ResultData resultData = new ResultData();

        try {
            List<JobConfig> jobList = jobConfigService.querySysJobList(map);
            int count = jobConfigService.getJobCount();
            resultData.setCode(0);
            resultData.setCount(count);
            resultData.setMsg("数据请求成功");
            resultData.setData(jobList);
            return resultData;
        } catch (Exception e) {
            throw new BizException("查询任务列表异常：" + e.getMessage());
        }
    }

    /**
     * 添加定时任务
     *
     * @throws Exception
     */
    @PostMapping(value = "/addJob")
    @ResponseBody
    @Transactional
    public int addjob(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("添加任务开始... ...");
        int num =0;
        String jobName = request.getParameter("jobName");
        String jobClassPath= request.getParameter("jobClassPath");
        String jobGroup= request.getParameter("jobGroup");
        String jobCron= request.getParameter("jobCron");
        String jobDescribe= request.getParameter("jobDescribe");
        String jobDataMap= request.getParameter("jobDataMap");

        if (StringUtils.isBlank(jobName)) {
            throw new BizException("任务名称不能为空");
        }
        if (StringUtils.isBlank(jobGroup)) {
            throw new BizException("任务组别不能为空");
        }
        if (StringUtils.isBlank(jobCron)) {
            throw new BizException("Cron表达式不能为空");
        }
        if (StringUtils.isBlank(jobClassPath)) {
            throw new BizException("任务类路径不能为空");
        }

        // 参数不为空时校验格式
        if(StringUtils.isNotBlank(jobDataMap)){
            try {
                JSONObject.parseObject(jobDataMap);
            } catch (Exception e) {
                throw new BizException("参数JSON格式错误");
            }
        }

        // 已存在校验
        JobConfig queryBean = new JobConfig();
        queryBean.setJobName(jobName);
        JobConfig result = jobConfigService.selectByBean(queryBean);
        if (null != result) {
            throw new BizException("任务名为" + jobName + "的任务已存在！");
        }

        JobConfig bean = new JobConfig();
        bean.setJobName(jobName);
        bean.setJobClassPath(jobClassPath);
        bean.setJobGroup(jobGroup);
        bean.setJobCron(jobCron);
        bean.setJobDescribe(jobDescribe);
        bean.setJobDateMap(jobDataMap);
        bean.setJobStatus(1);//启用
        try {
            num = jobConfigService.insertSelective(bean);
        } catch (Exception e) {
            throw new BizException("新增定时任务失败");
        }

        SchedulerUtil.addJob(jobClassPath,jobName, jobGroup, jobCron,jobDataMap);
        return num;
    }

    /**
     * 变更定时任务执行状态
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/changeState")
    @ResponseBody
    public int changeState(@RequestParam(value = "id") String idStr)throws Exception{
        logger.info("变更定时任务状态开始... ...");
        if (StringUtils.isBlank(idStr)) {
            throw new BizException("任务ID不能为空");
        }
        int id = Integer.parseInt(idStr);

        // 校验
        JobConfig queryBean = new JobConfig();
        queryBean.setId(id);
        JobConfig result = jobConfigService.selectByBean(queryBean);
        if (null == result) {
            throw new BizException("任务ID为" + id + "的任务不存在！");
        }

        JobConfig updateBean = new JobConfig();
        updateBean.setId(id);
        //如果是现在是启用，则停用
        if(1 == result.getJobStatus()){
            updateBean.setJobStatus(0);
            //SchedulerUtil.jobPause(result.getJobName(), result.getJobGroup());
            Boolean b=SchedulerUtil.isResume(result.getJobName(), result.getJobGroup());
            if (b) {
                SchedulerUtil.jobdelete(result.getJobName(), result.getJobGroup());
            }
        }

        //如果现在是停用，则启用
        if(0 == result.getJobStatus()){
            updateBean.setJobStatus(1);
            //SchedulerUtil.jobresume(result.getJobName(), result.getJobGroup());
            Boolean b=SchedulerUtil.isResume(result.getJobName(), result.getJobGroup());
            //存在则激活，不存在则添加
            if (b) {
                SchedulerUtil.jobresume(result.getJobName(), result.getJobGroup());
            }else {
                SchedulerUtil.addJob(result.getJobClassPath(),result.getJobName(), result.getJobGroup(), result.getJobCron(),result.getJobDateMap());
            }
        }

        try {
            jobConfigService.updateByPrimaryKeySelective(updateBean);
        } catch (Exception e) {
            throw new BizException("更新数据库的定时任务信息异常！");
        }
        // 1表示成功
        return 1;

    }

    /**
     * 删除一个任务
     *
     * @throws Exception
     */
    @PostMapping(value = "/deleteJob")
    @ResponseBody
    public int deletejob(@RequestParam(value = "id") String idStr) throws Exception {
        logger.info("删除定时任务状态开始... ...");
        int num =0;
        if (StringUtils.isBlank(idStr)) {
            throw new BizException("任务ID不能为空");
        }
        int id = Integer.parseInt(idStr);

        // 存在性校验
        JobConfig queryBean = new JobConfig();
        queryBean.setId(id);
        JobConfig result = jobConfigService.selectByBean(queryBean);
        if (null == result) {
            throw new BizException("任务ID为" + idStr + "的任务不存在！");
        }

        try {
            num = jobConfigService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new BizException("从数据库删除定时任务时发生异常！");
        }

        SchedulerUtil.jobdelete(result.getJobName(), result.getJobGroup());
        return num;
    }

    /**
     * 修改定时表达式
     */
    @RequestMapping("/reSchedulejob")
    @ResponseBody
    public Boolean updateByBean(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("修改定时任务信息开始... ...");
        Boolean bool =false;
        String jobCron = request.getParameter("jobCron");
        String jobDescribe = request.getParameter("jobDescribe");
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        // 数据非空校验
        if (!StringUtils.isNotBlank(idStr)) {
            throw new BizException("任务ID不能为空");
        }
        JobConfig result = jobConfigService.selectByPrimaryKey(id);
        // 数据不存在
        if (null == result) {
            throw new BizException("根据任务ID[" + id + "]未查到相应的任务记录");
        }
        JobConfig bean = new JobConfig();
        bean.setId(id);
        bean.setJobCron(jobCron);
        bean.setJobDescribe(jobDescribe);
        try {
            bool = jobConfigService.updateByPrimaryKeySelective(bean);
        } catch (Exception e) {
            throw new BizException("变更任务表达式异常：" + e.getMessage());
        }
        //只有任务状态为启用，才开始运行
        // 如果先启动再手工插入数据，此处会报空指针异常
        if( result.getJobStatus() ==1 ){
            SchedulerUtil.jobReschedule(result.getJobName(), result.getJobGroup(),jobCron);
        }

        // 返回成功
        return bool;
    }


}
