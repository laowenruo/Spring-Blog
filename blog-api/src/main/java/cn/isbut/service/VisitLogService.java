package cn.isbut.service;

import org.springframework.scheduling.annotation.Async;
import cn.isbut.entity.VisitLog;
import cn.isbut.model.dto.VisitLogUuidTime;

import java.util.List;

/**
 * @author Ryan
 */
public interface VisitLogService {
	/**
	 * 得到访问日志
	 * @param uuid 访客标识码
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 访问日志
	 */
	List<VisitLog> getVisitLogListByUUIDAndDate(String uuid, String startDate, String endDate);

	/**
	 * 得到昨天的访问
	 * @return 昨日访问
	 */
	List<VisitLogUuidTime> getUUIDAndCreateTimeByYesterday();

	/**
	 * 保存访问日志
	 * @param log 访问日志
	 */
	@Async
	void saveVisitLog(VisitLog log);

	/**
	 * 删除访问日志
	 * @param id 日志id
	 */
	void deleteVisitLogById(Integer id);
}
