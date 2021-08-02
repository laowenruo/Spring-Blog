package cn.isbut.service;

import cn.isbut.entity.SiteSetting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ryan
 */
public interface SiteSettingService {

    void updateSiteSetting(List<LinkedHashMap> siteSettings, List<Integer> deleteIds);

    String getWebTitleSuffix();

    Map<String, List<SiteSetting>> getList();

    Map<String, Object> getSiteInfo();

}
