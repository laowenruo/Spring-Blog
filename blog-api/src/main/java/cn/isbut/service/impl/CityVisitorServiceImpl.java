package cn.isbut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.isbut.entity.CityVisitor;
import cn.isbut.mapper.CityVisitorMapper;
import cn.isbut.service.CityVisitorService;

/**
 * @author Ryan
 * @Description: 城市访客数量统计业务层实现
 *
 */
@Service
public class CityVisitorServiceImpl implements CityVisitorService {

	CityVisitorMapper cityVisitorMapper;

	@Override
	public void saveCityVisitor(CityVisitor cityVisitor) {
		cityVisitorMapper.saveCityVisitor(cityVisitor);
	}

	@Autowired
	public void setCityVisitorMapper(CityVisitorMapper cityVisitorMapper) {
		this.cityVisitorMapper = cityVisitorMapper;
	}
}
