package cn.isbut.service;

import org.springframework.scheduling.annotation.Async;
import cn.isbut.entity.OperationLog;

import java.util.List;

/**
 * @author Ryan
 */
public interface OperationLogService {
	/**
	 * 得到范围时间内的操作日志
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 操作日志列表
	 */
	List<OperationLog> getOperationLogListByDate(String startDate, String endDate);

	/**
	 * 保存操作日志，异步
	 * @param log 日志
	 */
	@Async
	void saveOperationLog(OperationLog log);

	/**
	 * 删除操作日志
	 * @param id 日志id
	 */
	void deleteOperationLogById(Integer id);
}
