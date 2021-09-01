package cn.isbut.service.impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.model.dto.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.config.RedisKeyConfig;
import cn.isbut.entity.SiteSetting;
import cn.isbut.mapper.FriendMapper;
import cn.isbut.mapper.SiteSettingMapper;
import cn.isbut.model.vo.FriendInfo;
import cn.isbut.service.FriendService;
import cn.isbut.service.RedisService;
import cn.isbut.util.markdown.MarkdownUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Ryan
 * @Description: 友链业务层实现
 *
 */
@Service
public class FriendServiceImpl implements FriendService {

	FriendMapper friendMapper;

	SiteSettingMapper siteSettingMapper;

	RedisService redisService;

	@Override
	public List<cn.isbut.entity.Friend> getFriendList() {
		return friendMapper.getFriendList();
	}

	@Override
	public List<cn.isbut.model.vo.Friend> getFriendVOList() {
		return friendMapper.getFriendVOList();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateFriendPublishedById(Integer friendId, Boolean published) {
		if (friendMapper.updateFriendPublishedById(friendId, published) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveFriend(cn.isbut.entity.Friend friend) {
		friend.setViews(0);
		friend.setCreateTime(new Date());
		if (friendMapper.saveFriend(friend) != 1) {
			throw new PersistenceException("添加失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateFriend(Friend friend) {
		if (friendMapper.updateFriend(friend) != 1) {
			throw new PersistenceException("修改失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteFriend(Integer id) {
		if (friendMapper.deleteFriend(id) != 1) {
			throw new PersistenceException("删除失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateViewsByNickname(String nickname) {
		if (friendMapper.updateViewsByNickname(nickname) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	@Override
	public FriendInfo getFriendInfo(boolean cache, boolean md) {
		String redisKey = RedisKeyConfig.FRIEND_INFO_MAP;
		if (cache) {
			FriendInfo friendInfoFromRedis = redisService.getObjectByValue(redisKey, FriendInfo.class);
			if (friendInfoFromRedis != null) {
				return friendInfoFromRedis;
			}
		}
		List<SiteSetting> siteSettings = siteSettingMapper.getFriendInfo();
		FriendInfo friendInfo = new FriendInfo();
		for (SiteSetting siteSetting : siteSettings) {
			if ("friendContent".equals(siteSetting.getNameEn())) {
				if (md) {
					friendInfo.setContent(MarkdownUtils.markdownToHtmlExtensions(siteSetting.getValue()));
				} else {
					friendInfo.setContent(siteSetting.getValue());
				}
			} else if ("friendCommentEnabled".equals(siteSetting.getNameEn())) {
				friendInfo.setCommentEnabled("1".equals(siteSetting.getValue()));
			}
		}
		if (cache && md) {
			redisService.saveObjectToValue(redisKey, friendInfo);
		}
		return friendInfo;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateFriendInfoContent(String content) {
		if (siteSettingMapper.updateFriendInfoContent(content) != 1) {
			throw new PersistenceException("修改失败");
		}
		deleteFriendInfoRedisCache();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateFriendInfoCommentEnabled(Boolean commentEnabled) {
		if (siteSettingMapper.updateFriendInfoCommentEnabled(commentEnabled) != 1) {
			throw new PersistenceException("修改失败");
		}
		deleteFriendInfoRedisCache();
	}

	/**
	 * 删除友链页面缓存
	 */
	private void deleteFriendInfoRedisCache() {
		redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_MAP);
	}

	@Autowired
	public void setFriendMapper(FriendMapper friendMapper) {
		this.friendMapper = friendMapper;
	}

	@Autowired
	public void setSiteSettingMapper(SiteSettingMapper siteSettingMapper) {
		this.siteSettingMapper = siteSettingMapper;
	}

	@Autowired
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
}
