package cn.isbut.mapper;

import cn.isbut.model.dto.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 * @Description: 友链持久层接口
 *
 */
@Mapper
@Repository
public interface FriendMapper {
	/**
	 * 得到友链列表
	 * @return 友链
	 */
	List<cn.isbut.entity.Friend> getFriendList();

	/**
	 * 得到前端展示友链
	 * @return 友链
	 */
	List<cn.isbut.model.vo.Friend> getFriendVOList();

	/**
	 * 通过友链id设置可见性
	 * @param id 友链id
	 * @param published 发布状态
	 * @return  成功与否状态
	 */
	int updateFriendPublishedById(Integer id, Boolean published);

	/**
	 * 保存友链
	 * @param friend 友链
	 * @return 成功与否状态
	 */
	int saveFriend(cn.isbut.entity.Friend friend);

	/**
	 * 更新友链
	 * @param friend 友链
	 * @return 成功与否状态
	 */
	int updateFriend(Friend friend);

	/**
	 * 删除友链
	 * @param id 友链id
	 * @return 成功与否状态
	 */
	int deleteFriend(Integer id);

	/**
	 * 增加点击量
	 * @param nickname 友链名字
	 * @return 成功与否状态
	 */
	int updateViewsByNickname(String nickname);
}
