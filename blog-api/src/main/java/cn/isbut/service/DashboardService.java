package cn.isbut.service;

import cn.isbut.entity.CityVisitor;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ryan
 */
public interface DashboardService {
    /**
    * @Description: 统计所有博客数目
    * @param
    * @return {@link int}
    * @throws
    * @author ryan
    * @data 2021/5/27 20:19
    *
    */
    int getBlogCount();

    /**
    * @Description: 获取评论数目
    * @param
    * @return {@link int}
    * @throws
    * @author ryan
    * @data 2021/5/27 20:39
    *
    */
    int getCommentCount();

    int countVisitLogByToday();

    Map<String, List> getCategoryBlogCountMap();

    Map<String, List> getTagBlogCountMap();

    Map<String, List> getVisitRecordMap();

    List<CityVisitor> getCityVisitorList();

}
