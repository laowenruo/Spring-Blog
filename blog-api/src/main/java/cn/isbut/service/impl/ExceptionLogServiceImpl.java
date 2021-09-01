package cn.isbut.service.impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.util.UserAgentUtils;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.entity.ExceptionLog;
import cn.isbut.mapper.ExceptionLogMapper;
import cn.isbut.service.ExceptionLogService;
import cn.isbut.util.IpAddressUtils;

import java.util.List;

/**
 * @author Ryan
 * @Description: 异常日志业务层实现
 */
@Service
public class ExceptionLogServiceImpl implements ExceptionLogService {

	ExceptionLogMapper exceptionLogMapper;

    UserAgentUtils userAgentUtils;

	@Override
	public List<ExceptionLog> getExceptionLogListByDate(String startDate, String endDate) {
		return exceptionLogMapper.getExceptionLogListByDate(startDate, endDate);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void saveExceptionLog(ExceptionLog log) {
		var ipSource = IpAddressUtils.getCityInfo(log.getIp());
		var userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
		String os = userAgentMap.get("os");
		String browser = userAgentMap.get("browser");
		log.setIpSource(ipSource);
		log.setOs(os);
		log.setBrowser(browser);
		if (exceptionLogMapper.saveExceptionLog(log) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteExceptionLogById(Integer id) {
		if (exceptionLogMapper.deleteExceptionLogById(id) != 1) {
			throw new PersistenceException("删除日志失败");
		}
	}

	@Autowired
	public void setExceptionLogMapper(ExceptionLogMapper exceptionLogMapper) {
		this.exceptionLogMapper = exceptionLogMapper;
	}

	@Autowired
	public void setUserAgentUtils(UserAgentUtils userAgentUtils) {
		this.userAgentUtils = userAgentUtils;
	}
}
