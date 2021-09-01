package cn.isbut.service.impl;

import cn.isbut.entity.ExceptionLog;
import cn.isbut.exception.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.config.RedisKeyConfig;
import cn.isbut.entity.About;
import cn.isbut.mapper.AboutMapper;
import cn.isbut.service.AboutService;
import cn.isbut.service.RedisService;
import cn.isbut.util.markdown.MarkdownUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ryan
 * @Description: 关于我页面业务层实现
 *
 */
@Service
public class AboutServiceImpl implements AboutService {

	AboutMapper aboutMapper;

	RedisService redisService;

	@Override
	public Map<String, String> getAboutInfo() {
		//先走缓存查询
		String redisKey = RedisKeyConfig.ABOUT_INFO_MAP;
		Map<String, String> aboutInfoMapFromRedis = redisService.getMapByValue(redisKey);
		if (aboutInfoMapFromRedis != null) {
			return aboutInfoMapFromRedis;
		}
		List<About> abouts = aboutMapper.getList();
		Map<String, String> aboutInfoMap = new HashMap<>(8);
		for (About about : abouts) {
			if ("content".equals(about.getNameEn())) {
				about.setValue(MarkdownUtils.markdownToHtmlExtensions(about.getValue()));
			}
			aboutInfoMap.put(about.getNameEn(), about.getValue());
		}
		redisService.saveMapToValue(redisKey, aboutInfoMap);
		return aboutInfoMap;
	}

	@Override
	public Map<String, String> getAboutSetting() {
		List<About> abouts = aboutMapper.getList();
		Map<String, String> map = new HashMap<>(8);
		for (About about : abouts) {
			map.put(about.getNameEn(), about.getValue());
		}
		return map;
	}

	@Override
	public void updateAbout(Map<String, String> map) {
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			updateOneAbout(key, map.get(key));
		}
		//更新的时候删除缓存
		deleteAboutRedisCache();
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateOneAbout(String nameEn, String value) {
		if (aboutMapper.updateAbout(nameEn, value) != 1) {
			throw new PersistenceException("修改失败");
		}
	}

	@Override
	public boolean getAboutCommentEnabled() {
		String commentEnabledString = aboutMapper.getAboutCommentEnabled();
		return Boolean.parseBoolean(commentEnabledString);
	}

	/**
	 * 删除关于我页面缓存
	 */
	private void deleteAboutRedisCache() {
		redisService.deleteCacheByKey(RedisKeyConfig.ABOUT_INFO_MAP);
	}

	@Autowired
	public void setAboutMapper(AboutMapper aboutMapper) {
		this.aboutMapper = aboutMapper;
	}

	@Autowired
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
}
