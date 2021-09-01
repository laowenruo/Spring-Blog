package cn.isbut.service;

import org.springframework.scheduling.annotation.Async;
import cn.isbut.entity.ExceptionLog;

import java.util.List;

/**
 * @author Ryan
 */
public interface ExceptionLogService {
	/**
	 * 列出范围内的错误日志
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 错误日志
	 */
	List<ExceptionLog> getExceptionLogListByDate(String startDate, String endDate);

	/**
	 * 保存异常日志，异步注解，即不需要等待
	 * @param log 日志
	 */
	@Async
	void saveExceptionLog(ExceptionLog log);

	/**
	 * 删除异常日志
	 * @param id 日志id
	 */
	void deleteExceptionLogById(Integer id);
}
