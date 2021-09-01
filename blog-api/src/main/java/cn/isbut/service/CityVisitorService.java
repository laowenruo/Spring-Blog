package cn.isbut.service;

import cn.isbut.entity.CityVisitor;

/**
 * @author Ryan
 */
public interface CityVisitorService {
	/**
	 * 保存城市访客
	 * @param cityVisitor 城市访客
	 */
	void saveCityVisitor(CityVisitor cityVisitor);
}
