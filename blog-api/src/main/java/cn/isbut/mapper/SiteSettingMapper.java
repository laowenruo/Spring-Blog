package cn.isbut.mapper;

import cn.isbut.entity.SiteSetting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 站点设置持久层接口
 * @Author: ryan
 */
@Mapper
@Repository
public interface SiteSettingMapper {

    /**
    * 添加站点设置
    * @param siteSetting 站点设置对象
    * @return {@link int}
    * @author ryan
     */
    int saveSiteSetting(SiteSetting siteSetting);

    /**
    * 删除站点信息
    * @param id 站点设置id
    * @return {@link int}
    * @author ryan
    */
    int deleteSiteSettingById(Integer id);

    /**
    * 更新站点信息
    * @param siteSetting 站点设置对象
    * @return {@link int}
    * @author ryan
    */
    int updateSiteSetting(SiteSetting siteSetting);

    //int updateFriendInfoContent(String content);

    //int updateFriendInfoCommentEnabled(Boolean commentEnabled);

    /**
    * 查询网页标题后缀
    * @param
    * @return {@link String}
    * @author ryan
    */
    String getWebTitleSuffix();

    /**
    * 查询站点设置
    * @param
    * @return {@link List< SiteSetting>}
    * @author ryan
    */
    List<SiteSetting> getSiteSettingList();

    //List<SiteSetting> getFriendInfo();



}
