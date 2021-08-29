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
	 * @return
	 */
	List<cn.isbut.entity.Friend> getFriendList();

	/**
	 * 得到前端展示友链
	 * @return
	 */
	List<cn.isbut.model.vo.Friend> getFriendVOList();

	/**
	 * 通过友链id设置可见性
	 * @param id
	 * @param published
	 * @return
	 */
	int updateFriendPublishedById(Integer id, Boolean published);

	/**
	 * 保存友链
	 * @param friend
	 * @return
	 */
	int saveFriend(cn.isbut.entity.Friend friend);

	/**
	 * 更新友链
	 * @param friend
	 * @return
	 */
	int updateFriend(Friend friend);

	/**
	 * 删除友链
	 * @param id
	 * @return
	 */
	int deleteFriend(Integer id);

	/**
	 * 增加点击量
	 * @param nickname
	 * @return
	 */
	int updateViewsByNickname(String nickname);
}
