package cn.isbut.service;

import cn.isbut.entity.SiteSetting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 */
public interface SiteSettingService {
	/**
	 * 得到设置列表
	 * @return 设置列表
	 */
	Map<String, List<SiteSetting>> getList();

	/**
	 * 得到设置信息
	 * @return 设置信息
	 */
	Map<String, Object> getSiteInfo();

	/**
	 * 得到网页后缀
	 * @return 网页后缀
	 */
	String getWebTitleSuffix();

	/**
	 * 更新站点设置
	 * @param siteSettings 站点设置
	 * @param deleteIds id
	 */
	void updateSiteSetting(List<LinkedHashMap> siteSettings, List<Integer> deleteIds);
}
