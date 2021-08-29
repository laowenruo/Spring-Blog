package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.ScheduleJob;

import java.util.List;

/**
 * @author Ryan
 * @Description: 定时任务持久层接口
 *
 */
@Mapper
@Repository
public interface ScheduleJobMapper {
	/**
	 * 得到任务列表
	 * @return
	 */
	List<ScheduleJob> getJobList();

	/**
	 * 通过id得到任务
	 * @param jobId
	 * @return
	 */
	ScheduleJob getJobById(Integer jobId);

	/**
	 * 保存任务
	 * @param scheduleJob
	 * @return
	 */
	int saveJob(ScheduleJob scheduleJob);

	/**
	 * 更新任务
	 * @param scheduleJob
	 * @return
	 */
	int updateJob(ScheduleJob scheduleJob);

	/**
	 * 按id删除任务
	 * @param jobId
	 * @return
	 */
	int deleteJobById(Integer jobId);

	/**
	 * 更新任务状态
	 * @param jobId
	 * @param status
	 * @return
	 */
	int updateJobStatusById(Integer jobId, Boolean status);
}
