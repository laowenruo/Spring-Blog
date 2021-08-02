package cn.isbut.mapper;

import cn.isbut.dto.VisitLogUuidTimeDTO;
import cn.isbut.entity.VisitLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 * @Description:
 */
@Mapper
@Repository
public interface VisitLogMapper {

    int saveVisitLog(VisitLog log);

    int deleteVisitLogById(Long id);

    int countVisitLogByToday();

    List<VisitLog> getVisitLogListByUUIDAndDate(String uuid, String startDate, String endDate);

    List<VisitLogUuidTimeDTO> getUUIDAndCreateTimeByYesterday();
}
