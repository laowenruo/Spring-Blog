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
	 * @return
	 */
	List<SiteSetting> getList();

	/**
	 * 查询友链页面信息
	 * @return
	 */
	List<SiteSetting> getFriendInfo();

	/**
	 * 查询网页标题后缀
	 * @return
	 */
	String getWebTitleSuffix();

	/**
	 * 更新
	 * @param siteSetting
	 * @return
	 */
	int updateSiteSetting(SiteSetting siteSetting);

	/**
	 * -删除
	 * @param id
	 * @return
	 */
	int deleteSiteSettingById(Integer id);

	/**
	 * 添加
	 * @param siteSetting
	 * @return
	 */
	int saveSiteSetting(SiteSetting siteSetting);

	/**
	 * 更新友链页面content
	 * @param content
	 * @return
	 */
	int updateFriendInfoContent(String content);

	/**
	 * 更新友链页面commentEnabled
	 * @param commentEnabled
	 * @return
	 */
	int updateFriendInfoCommentEnabled(Boolean commentEnabled);
}
