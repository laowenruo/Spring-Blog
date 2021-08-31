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
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 定时任务日志
	 */
	List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate);

	/**
	 * 保存定时任务日志
	 * @param jobLog 定时任务日志
	 * @return 成功与否状态
	 */
	int saveJobLog(ScheduleJobLog jobLog);

	/**
	 * 删除定时任务日志
	 * @param logId 任务id
	 * @return 成功与否状态
	 */
	int deleteJobLogByLogId(Integer logId);
}
