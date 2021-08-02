package cn.isbut.mapper;

import cn.isbut.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 * @Description:
 */
@Repository
@Mapper
public interface ScheduleJobMapper {

    /**
    * 新增任务
    * @param scheduleJob
    * @return {@link int}
    * @author ryan
    */
    int saveJob(ScheduleJob scheduleJob);

    /**
    * 删除任务
    * @param jobId
    * @return {@link int}
    * @author ryan
    */
    int deleteJobById(Long jobId);

    /**
    * 更新任务
    * @param scheduleJob
    * @return {@link int}
    * @author ryan
    */
    int updateJob(ScheduleJob scheduleJob);

    /**
    * 更新任务状态
    * @param jobId
    * @param status
    * @return {@link int}
    * @author ryan
    */
    int updateJobStatusById(Long jobId, Boolean status);

    /**
    * 通过id获取任务信息
    * @param jobId
    * @return
    * @author ryan
    */
    ScheduleJob getJobById(Long jobId);

    /**
    * 获取任务列表
    * @param
    * @return
    * @author ryan
    */
    List<ScheduleJob> getJobList();



}
