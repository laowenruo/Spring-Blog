package cn.isbut.service;

import cn.isbut.entity.ScheduleJob;
import cn.isbut.entity.ScheduleJobLog;

import java.util.List;

/**
 * @author Ryan
 */
public interface ScheduleJobService {
	/**
	 * 得到任务列表
	 * @return 任务列表
	 */
	List<ScheduleJob> getJobList();

	/**
	 * 保存任务
	 * @param scheduleJob 任务
	 */
	void saveJob(ScheduleJob scheduleJob);

	/**
	 * 更新任务
	 * @param scheduleJob 任务
	 */
	void updateJob(ScheduleJob scheduleJob);

	/**
	 * 删除任务by id
	 * @param jobId 任务id
	 */
	void deleteJobById(Integer jobId);

	/**
	 * 运行任务by id
	 * @param jobId 任务id
	 */
	void runJobById(Integer jobId);

	/**
	 * 更新任务状态by id
	 * @param jobId 任务id
	 * @param status 状态
	 */
	void updateJobStatusById(Integer jobId, Boolean status);

	/**
	 * 得到范围内的任务
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 任务列表
	 */
	List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate);

	/**
	 *  保存任务日志
	 * @param log 任务日志
	 */
	void saveJobLog(ScheduleJobLog log);

	/**
	 * 删除任务日志 by id
	 * @param logId 日志id
	 */
	void deleteJobLogByLogId(Integer logId);
}
