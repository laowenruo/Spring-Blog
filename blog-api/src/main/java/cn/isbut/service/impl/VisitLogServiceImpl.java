package cn.isbut.service.impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.util.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.entity.VisitLog;
import cn.isbut.mapper.VisitLogMapper;
import cn.isbut.model.dto.VisitLogUuidTime;
import cn.isbut.service.VisitLogService;
import cn.isbut.util.IpAddressUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 访问日志业务层实现
 * 访问日志
 */
@Service
public class VisitLogServiceImpl implements VisitLogService {

	VisitLogMapper visitLogMapper;

    UserAgentUtils userAgentUtils;

	@Override
	public List<VisitLog> getVisitLogListByUUIDAndDate(String uuid, String startDate, String endDate) {
		return visitLogMapper.getVisitLogListByUUIDAndDate(uuid, startDate, endDate);
	}

	@Override
	public List<VisitLogUuidTime> getUUIDAndCreateTimeByYesterday() {
		return visitLogMapper.getUUIDAndCreateTimeByYesterday();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveVisitLog(VisitLog log) {
		String ipSource = IpAddressUtils.getCityInfo(log.getIp());
		Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
		String os = userAgentMap.get("os");
		String browser = userAgentMap.get("browser");
		log.setIpSource(ipSource);
		log.setOs(os);
		log.setBrowser(browser);
		if (visitLogMapper.saveVisitLog(log) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteVisitLogById(Integer id) {
		if (visitLogMapper.deleteVisitLogById(id) != 1) {
			throw new PersistenceException("删除日志失败");
		}
	}

	@Autowired
	public void setVisitLogMapper(VisitLogMapper visitLogMapper) {
		this.visitLogMapper = visitLogMapper;
	}

	@Autowired
	public void setUserAgentUtils(UserAgentUtils userAgentUtils) {
		this.userAgentUtils = userAgentUtils;
	}
}
