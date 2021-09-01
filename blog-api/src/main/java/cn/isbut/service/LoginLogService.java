package cn.isbut.service;

import org.springframework.scheduling.annotation.Async;
import cn.isbut.entity.LoginLog;

import java.util.List;

/**
 * @author Ryan
 */
public interface LoginLogService {
	/**
	 * 得到登录日志
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	List<LoginLog> getLoginLogListByDate(String startDate, String endDate);

	/**
	 * 保存登录日志
	 * @param log 登录日志
	 */
	@Async
	void saveLoginLog(LoginLog log);

	/**
	 * 删除登录日志id
	 * @param id 日志id
	 */
	void deleteLoginLogById(Integer id);
}
