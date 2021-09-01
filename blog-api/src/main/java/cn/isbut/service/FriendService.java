package cn.isbut.service;

import cn.isbut.model.dto.Friend;
import cn.isbut.model.vo.FriendInfo;

import java.util.List;

/**
 * @author Ryan
 */
public interface FriendService {
	/**
	 * 得到友链列表
	 * @return 友链列表
	 */
	List<cn.isbut.entity.Friend> getFriendList();

	/**
	 * 得到展示的友链列表
	 * @return 友链列表
	 */
	List<cn.isbut.model.vo.Friend> getFriendVOList();

	/**
	 * 更新友链状态
	 * @param friendId 友链id
	 * @param published 可见状态
	 */
	void updateFriendPublishedById(Integer friendId, Boolean published);

	/**
	 * 保存友链
	 * @param friend 友链
	 */
	void saveFriend(cn.isbut.entity.Friend friend);

	/**
	 * 更新友链
	 * @param friend 友链
	 */
	void updateFriend(Friend friend);

	/**
	 * 删除友链
	 * @param id 友链id
	 */
	void deleteFriend(Integer id);

	/**
	 * 更新友链点击量
	 * @param nickname 友链名字
	 */
	void updateViewsByNickname(String nickname);

	/**
	 * 得到友链信息
	 * @param cache 是否缓存？
	 * @param md 是否markdown？
	 * @return 友链信息
	 */
	FriendInfo getFriendInfo(boolean cache, boolean md);

	/**
	 * 更新友链内容
	 * @param content 友链内容
	 */
	void updateFriendInfoContent(String content);

	/**
	 * 友链评论开关
	 * @param commentEnabled 开关状态？
	 */
	void updateFriendInfoCommentEnabled(Boolean commentEnabled);
}
