package cn.isbut.service.impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.util.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.config.RedisKeyConfig;
import cn.isbut.entity.Visitor;
import cn.isbut.mapper.VisitorMapper;
import cn.isbut.model.dto.VisitLogUuidTime;
import cn.isbut.service.RedisService;
import cn.isbut.service.VisitorService;
import cn.isbut.util.IpAddressUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 访客统计业务层实现
 * 保存访客
 */
@Service
public class VisitorServiceImpl implements VisitorService {

	VisitorMapper visitorMapper;

	RedisService redisService;

    UserAgentUtils userAgentUtils;

	@Override
	public List<Visitor> getVisitorListByDate(String startDate, String endDate) {
		return visitorMapper.getVisitorListByDate(startDate, endDate);
	}

	@Override
	public List<String> getNewVisitorIpSourceByYesterday() {
		return visitorMapper.getNewVisitorIpSourceByYesterday();
	}

	@Override
	public boolean hasUUID(String uuid) {
		return visitorMapper.hasUUID(uuid) != 0;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveVisitor(Visitor visitor) {
		String ipSource = IpAddressUtils.getCityInfo(visitor.getIp());
		Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(visitor.getUserAgent());
		String os = userAgentMap.get("os");
		String browser = userAgentMap.get("browser");
		visitor.setIpSource(ipSource);
		visitor.setOs(os);
		visitor.setBrowser(browser);
		if (visitorMapper.saveVisitor(visitor) != 1) {
			throw new PersistenceException("访客添加失败");
		}
	}

	@Override
	public void updatePVAndLastTimeByUUID(VisitLogUuidTime dto) {
		visitorMapper.updatePVAndLastTimeByUUID(dto);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteVisitor(Integer id, String uuid) {
		//删除Redis中该访客的uuid
		redisService.deleteValueBySet(RedisKeyConfig.IDENTIFICATION_SET, uuid);
		if (visitorMapper.deleteVisitorById(id) != 1) {
			throw new PersistenceException("删除访客失败");
		}
	}

	@Autowired
	public void setVisitorMapper(VisitorMapper visitorMapper) {
		this.visitorMapper = visitorMapper;
	}

	@Autowired
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	@Autowired
	public void setUserAgentUtils(UserAgentUtils userAgentUtils) {
		this.userAgentUtils = userAgentUtils;
	}
}
