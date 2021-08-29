package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.ScheduleJobLog;

import java.util.List;

/**
 * @author Ryan
 * @Description: 定时任务日志持久层接口
 *
 */
@Mapper
@Repository
public interface ScheduleJobLogMapper {
	/**
	 * 得到时间范围内的定时任务日志
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate);

	/**
	 * 保存定时任务日志
	 * @param jobLog
	 * @return
	 */
	int saveJobLog(ScheduleJobLog jobLog);

	/**
	 * 删除定时任务日志
	 * @param logId
	 * @return
	 */
	int deleteJobLogByLogId(Integer logId);
}
