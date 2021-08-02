package cn.isbut.service;

import cn.isbut.entity.ExceptionLog;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Description:
 * @Author: ryan
 */
public interface ExceptionLogService {

    @Async
    void saveExceptionLog(ExceptionLog log);

    void deleteExceptionLogById(Long id);

    List<ExceptionLog> getExceptionLogListByDate(String startDate, String endDate);

}
