package projectgoodquartz.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectgoodquartz.Service.JobConfigService;
import projectgoodquartz.entity.JobConfig;
import projectgoodquartz.mapper.JobConfigMapper;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tengxiao
 * @since 2019-06-17
 */
@Service
public class JobConfigServiceImpl extends ServiceImpl<JobConfigMapper, JobConfig> implements JobConfigService {

    @Autowired
    private JobConfigMapper jobConfigMapper;

    @Override
    public int getJobCount() {
        return jobConfigMapper.getJobCount();
    }

    @Override
    public List<JobConfig> querySysJobList(HashMap<String, Object> map) {
        return jobConfigMapper.selectByMap(map);
    }

    @Override
    public int insertSelective(JobConfig record) {
        return jobConfigMapper.insert(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return jobConfigMapper.deleteById(id);
    }

    @Override
    public JobConfig selectByPrimaryKey(Integer id) {
        return jobConfigMapper.selectById(id);
    }

    @Override
    public JobConfig selectByBean(JobConfig bean) {
        return this.selectByBean(bean);
    }

    @Override
    public boolean updateByPrimaryKeySelective(JobConfig bean) {
        return this.updateById(bean);
    }
}
