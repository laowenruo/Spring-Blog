package cn.isbut.service.impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.util.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.entity.OperationLog;
import cn.isbut.mapper.OperationLogMapper;
import cn.isbut.service.OperationLogService;
import cn.isbut.util.IpAddressUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 操作日志业务层实现
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

	OperationLogMapper operationLogMapper;

	UserAgentUtils userAgentUtils;

	@Override
	public List<OperationLog> getOperationLogListByDate(String startDate, String endDate) {
		return operationLogMapper.getOperationLogListByDate(startDate, endDate);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveOperationLog(OperationLog log) {
		String ipSource = IpAddressUtils.getCityInfo(log.getIp());
		Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
		String os = userAgentMap.get("os");
		String browser = userAgentMap.get("browser");
		log.setIpSource(ipSource);
		log.setOs(os);
		log.setBrowser(browser);
		if (operationLogMapper.saveOperationLog(log) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteOperationLogById(Integer id) {
		if (operationLogMapper.deleteOperationLogById(id) != 1) {
			throw new PersistenceException("删除日志失败");
		}
	}

	@Autowired
	public void setOperationLogMapper(OperationLogMapper operationLogMapper) {
		this.operationLogMapper = operationLogMapper;
	}

	@Autowired
	public void setUserAgentUtils(UserAgentUtils userAgentUtils) {
		this.userAgentUtils = userAgentUtils;
	}
}
