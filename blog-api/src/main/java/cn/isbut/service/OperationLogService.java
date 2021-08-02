package cn.isbut.service;

import cn.isbut.entity.OperationLog;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Description:
 * @Author: ryan
 */
public interface OperationLogService {

    @Async
    void saveOperationLog(OperationLog log);

    void deleteOperationLogById(Long id);

    List<OperationLog> getOperationLogListByDate(String startDate, String endDate);



}
