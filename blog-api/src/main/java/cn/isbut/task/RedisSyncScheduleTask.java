package cn.isbut.task;

import cn.isbut.common.RedisKey;
import cn.isbut.service.BlogService;
import cn.isbut.service.RedisService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: Mashiro
 * @Date: Created in 2021/5/28 17:13
 */
@Component
public class RedisSyncScheduleTask {

    private final BlogService blogService;
    private final RedisService redisService;

    public RedisSyncScheduleTask(BlogService blogService, RedisService redisService) {
        this.blogService = blogService;
        this.redisService = redisService;
    }

    /**
     * 从Redis同步博客文章浏览量到数据库
     */
    public void syncBlogViewsToDatabase() {
        String redisKey = RedisKey.BLOG_VIEWS_MAP;
        Map blogViewsMap = redisService.getMapByHash(redisKey);
        Set<Integer> keys = blogViewsMap.keySet();
        for (Integer key : keys) {
            Integer views = (Integer) blogViewsMap.get(key);
            blogService.updateViews(key.longValue(), views);
        }
    }
}
