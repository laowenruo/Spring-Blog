package cn.isbut.service;

import java.util.Map;

/**
 * @author Ryan
 */
public interface AboutService {
	/**
	 * 获取关于我页面信息
	 * @return 关于我
	 */
	Map<String, String> getAboutInfo();

	/**
	 * 获取关于我页面配置
	 * @return 设置
	 */
	Map<String, String> getAboutSetting();

	/**
	 * 修改关于我页面
	 * @param map 设置
	 */
	void updateAbout(Map<String, String> map);

	/**
	 * 关于我评论开关
	 * @return 布尔
	 */
	boolean getAboutCommentEnabled();
}
