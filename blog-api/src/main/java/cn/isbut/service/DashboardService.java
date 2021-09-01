package cn.isbut.service;

import cn.isbut.entity.CityVisitor;

import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 */
public interface DashboardService {
	/**
	 * 得到今日访问量
	 * @return 今日访问量
	 */
	int countVisitLogByToday();

	/**
	 * 得到博文数量
	 * @return 博文数量
 	 */
	int getBlogCount();

	/**
	 * 得到评论数量
	 * @return 评论数量
	 */
	int getCommentCount();

	/**
	 * 得到分类数量
	 * @return 分类数量
	 */
	Map<String, List> getCategoryBlogCountMap();

	/**
	 * 得到标签数量
	 * @return 标签数量
	 */
	Map<String, List> getTagBlogCountMap();

	/**
	 * 得到访问uv、pv等网站记录
	 * @return 数据
	 */
	Map<String, List> getVisitRecordMap();

	/**
	 * 得到访客列表
	 * @return 访客列表
	 */
	List<CityVisitor> getCityVisitorList();
}
