package cn.isbut.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.isbut.config.RedisKeyConfig;
import cn.isbut.service.BlogService;
import cn.isbut.service.RedisService;

import java.util.Map;
import java.util.Set;

/**
 * @author Ryan
 * @Description: Redis相关定时任务
 *
 */
@Component
public class RedisSyncScheduleTask {

	RedisService redisService;

	BlogService blogService;

	/**
	 * 从Redis同步博客文章浏览量到数据库
	 */
	public void syncBlogViewsToDatabase() {
		String redisKey = RedisKeyConfig.BLOG_VIEWS_MAP;
		Map blogViewsMap = redisService.getMapByHash(redisKey);
		Set<Integer> keys = blogViewsMap.keySet();
		for (Integer key : keys) {
			Integer views = (Integer) blogViewsMap.get(key);
			blogService.updateViews(key.intValue(), views);
		}
	}

	@Autowired
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	@Autowired
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}
}
