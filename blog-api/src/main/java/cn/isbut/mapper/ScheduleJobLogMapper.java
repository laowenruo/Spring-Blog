package cn.isbut.mapper;

import cn.isbut.entity.ScheduleJobLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 * @Description:
 */
@Mapper
@Repository
public interface ScheduleJobLogMapper {

    int saveJobLog(ScheduleJobLog scheduleJoblog);

    int deleteJobLogByLogId(Long LogId);

    List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate);
}
