package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.SiteSetting;

import java.util.List;

/**
 * @author Ryan
 * @Description: 站点设置持久层接口
 *
 */
@Mapper
@Repository
public interface SiteSettingMapper {
	/**
	 * 得到站点设置
	 * @return 站点设置
	 */
	List<SiteSetting> getList();

	/**
	 * 查询友链页面信息
	 * @return 友链信息
	 */
	List<SiteSetting> getFriendInfo();

	/**
	 * 查询网页标题后缀
	 * @return 标题后缀
	 */
	String getWebTitleSuffix();

	/**
	 * 更新
	 * @param siteSetting 网站设置
	 * @return 成功与否状态
	 */
	int updateSiteSetting(SiteSetting siteSetting);

	/**
	 * -删除
	 * @param id 网站设置id
	 * @return 成功与否状态
	 */
	int deleteSiteSettingById(Integer id);

	/**
	 * 添加
	 * @param siteSetting 网站设置
	 * @return 成功与否状态
	 */
	int saveSiteSetting(SiteSetting siteSetting);

	/**
	 * 更新友链页面content
	 * @param content 友链内容
	 * @return 成功与否状态
	 */
	int updateFriendInfoContent(String content);

	/**
	 * 更新友链页面commentEnabled
	 * @param commentEnabled 状态
	 * @return 成功与否状态
	 */
	int updateFriendInfoCommentEnabled(Boolean commentEnabled);
}
