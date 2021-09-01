package cn.isbut.service.impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.util.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.entity.LoginLog;
import cn.isbut.mapper.LoginLogMapper;
import cn.isbut.service.LoginLogService;
import cn.isbut.util.IpAddressUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 登录日志业务层实现
 *
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

	LoginLogMapper loginLogMapper;

    UserAgentUtils userAgentUtils;

	@Override
	public List<LoginLog> getLoginLogListByDate(String startDate, String endDate) {
		return loginLogMapper.getLoginLogListByDate(startDate, endDate);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveLoginLog(LoginLog log) {
		String ipSource = IpAddressUtils.getCityInfo(log.getIp());
		Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
		String os = userAgentMap.get("os");
		String browser = userAgentMap.get("browser");
		log.setIpSource(ipSource);
		log.setOs(os);
		log.setBrowser(browser);
		if (loginLogMapper.saveLoginLog(log) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteLoginLogById(Integer id) {
		if (loginLogMapper.deleteLoginLogById(id) != 1) {
			throw new PersistenceException("删除日志失败");
		}
	}

	@Autowired
	public void setLoginLogMapper(LoginLogMapper loginLogMapper) {
		this.loginLogMapper = loginLogMapper;
	}

	@Autowired
	public void setUserAgentUtils(UserAgentUtils userAgentUtils) {
		this.userAgentUtils = userAgentUtils;
	}
}
