package cn.isbut.service;

import cn.isbut.dto.VisitLogUuidTimeDTO;
import cn.isbut.entity.VisitLog;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Description:
 * @Author: ryan
 */
public interface VisitLogService {

    @Async
    void saveVisitLog(VisitLog log);

    void deleteVisitLogById(Long id);

    List<VisitLog> getVisitLogListByUUIDAndDate(String uuid, String startDate, String endDate);

    List<VisitLogUuidTimeDTO> getUUIDAndCreateTimeByYesterday();



}
