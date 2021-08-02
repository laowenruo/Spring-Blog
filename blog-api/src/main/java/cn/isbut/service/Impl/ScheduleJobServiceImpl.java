package cn.isbut.service.Impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.service.ScheduleJobService;
import cn.isbut.entity.ScheduleJob;
import cn.isbut.entity.ScheduleJobLog;
import cn.isbut.mapper.ScheduleJobLogMapper;
import cn.isbut.mapper.ScheduleJobMapper;
import cn.isbut.util.quartz.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Description: 定时任务业务层实现
 * @Author: BeforeOne
 * @Date: Created in 2021/5/27 20:16
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    private final Scheduler scheduler;
    private final ScheduleJobMapper schedulerJobMapper;
    private final ScheduleJobLogMapper scheduleJobLogMapper;

    public ScheduleJobServiceImpl(Scheduler scheduler, ScheduleJobMapper schedulerJobMapper, ScheduleJobLogMapper scheduleJobLogMapper) {
        this.scheduler = scheduler;
        this.schedulerJobMapper = schedulerJobMapper;
        this.scheduleJobLogMapper = scheduleJobLogMapper;
    }

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

    @Transactional
    @Override
    public void saveJob(ScheduleJob scheduleJob) {
        if (schedulerJobMapper.saveJob(scheduleJob) != 1){
            throw new PersistenceException("添加任务异常");
        }
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

    @Override
    public void saveJobLog(ScheduleJobLog log) {
        if (scheduleJobLogMapper.saveJobLog(log) != 1){
            throw new PersistenceException("添加任务日志异常");
        }
    }

    @Override
    public void deleteJobById(Long jobId) {
        if (schedulerJobMapper.deleteJobById(jobId) != 1){
            throw new PersistenceException("删除任务异常");
        }
        ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    }

    @Override
    public void deleteJobLogByLogId(Long logId) {
        if (scheduleJobLogMapper.deleteJobLogByLogId(logId) != 1){
            throw new PersistenceException("删除任务日志异常");
        }
    }

    @Override
    public void updateJob(ScheduleJob scheduleJob) {
        if (schedulerJobMapper.updateJob(scheduleJob) != 1){
            throw new PersistenceException("更新任务异常");
        }
    }

    @Override
    public void updateJobStatusById(Long jobId, Boolean status) {
        if (status){
            ScheduleUtils.resumeJob(scheduler, jobId);
        }else {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }
        if (schedulerJobMapper.updateJobStatusById(jobId, status) != 1){
            throw new PersistenceException("更新任务状态异常");
        }
    }

    @Override
    public void runJobById(Long jobId) {
        ScheduleUtils.run(scheduler, schedulerJobMapper.getJobById(jobId));
    }

    @Override
    public List<ScheduleJob> getJobList() {
        return schedulerJobMapper.getJobList();
    }

    @Override
    public List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate) {
        return scheduleJobLogMapper.getJobLogListByDate(startDate, endDate);
    }
}
