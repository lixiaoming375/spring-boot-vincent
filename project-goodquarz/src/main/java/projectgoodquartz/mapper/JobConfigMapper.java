package projectgoodquartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import projectgoodquartz.entity.JobConfig;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tengxiao
 * @since 2019-06-17
 */
@Repository
public interface JobConfigMapper extends BaseMapper<JobConfig> {
    /**
     * 获取任务数量
     * @param
     * @return
     */
    int getJobCount();


}
