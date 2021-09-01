package cn.isbut.service.impl;

import cn.isbut.exception.PersistenceException;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.entity.ScheduleJob;
import cn.isbut.entity.ScheduleJobLog;
import cn.isbut.mapper.ScheduleJobLogMapper;
import cn.isbut.mapper.ScheduleJobMapper;
import cn.isbut.service.ScheduleJobService;
import cn.isbut.util.quartz.ScheduleUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Ryan
 * @Description: 定时任务业务层实现
 *
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

	Scheduler scheduler;

	ScheduleJobMapper schedulerJobMapper;

	ScheduleJobLogMapper scheduleJobLogMapper;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init() {
		List<ScheduleJob> scheduleJobList = getJobList();
		for (ScheduleJob scheduleJob : scheduleJobList) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
			//如果不存在，则创建
			if (cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	@Override
	public List<ScheduleJob> getJobList() {
		return schedulerJobMapper.getJobList();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveJob(ScheduleJob scheduleJob) {
		if (schedulerJobMapper.saveJob(scheduleJob) != 1) {
			throw new PersistenceException("添加失败");
		}
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateJob(ScheduleJob scheduleJob) {
		if (schedulerJobMapper.updateJob(scheduleJob) != 1) {
			throw new PersistenceException("更新失败");
		}
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteJobById(Integer jobId) {
		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		if (schedulerJobMapper.deleteJobById(jobId) != 1) {
			throw new PersistenceException("删除失败");
		}
	}

	@Override
	public void runJobById(Integer jobId) {
		ScheduleUtils.run(scheduler, schedulerJobMapper.getJobById(jobId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateJobStatusById(Integer jobId, Boolean status) {
		if (status) {
			ScheduleUtils.resumeJob(scheduler, jobId);
		} else {
			ScheduleUtils.pauseJob(scheduler, jobId);
		}
		if (schedulerJobMapper.updateJobStatusById(jobId, status) != 1) {
			throw new PersistenceException("修改失败");
		}
	}

	@Override
	public List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate) {
		return scheduleJobLogMapper.getJobLogListByDate(startDate, endDate);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveJobLog(ScheduleJobLog jobLog) {
		if (scheduleJobLogMapper.saveJobLog(jobLog) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteJobLogByLogId(Integer logId) {
		if (scheduleJobLogMapper.deleteJobLogByLogId(logId) != 1) {
			throw new PersistenceException("日志删除失败");
		}
	}

	@Autowired
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Autowired
	public void setSchedulerJobMapper(ScheduleJobMapper schedulerJobMapper) {
		this.schedulerJobMapper = schedulerJobMapper;
	}

	@Autowired
	public void setScheduleJobLogMapper(ScheduleJobLogMapper scheduleJobLogMapper) {
		this.scheduleJobLogMapper = scheduleJobLogMapper;
	}
}
