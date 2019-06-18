package projectgoodquartz.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import projectgoodquartz.entity.JobConfig;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tengxiao
 * @since 2019-06-17
 */
public interface JobConfigService extends IService<JobConfig> {
    /**
     * 获取任务数量
     * @param
     * @return
     */
    public int getJobCount();

    /**
     * 查询定时任务列表
     * @param map
     * @return
     */
    List<JobConfig> querySysJobList(HashMap<String, Object> map);

    /**
     * 新增定时任务
     * @param record
     * @return
     */
    int insertSelective(JobConfig record);

    /**
     * 删除定时任务
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 根据主键查询定时任务详情
     * @param id
     * @return
     */
    JobConfig selectByPrimaryKey(Integer id);

    /**
     * 根据bean查询定时任务详情
     * @param
     * @return
     */
    JobConfig selectByBean(JobConfig bean);

    /**
     * 更新定时任务详情
     * @param
     * @return
     */
    boolean updateByPrimaryKeySelective(JobConfig bean);
}
