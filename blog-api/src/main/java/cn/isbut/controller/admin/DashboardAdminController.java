package cn.isbut.controller.admin;

import cn.isbut.common.RedisKey;
import cn.isbut.common.Result;
import cn.isbut.entity.CityVisitor;
import cn.isbut.service.DashboardService;
import cn.isbut.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 后台管理仪表盘控制层
 * @Author: ryan
 */
@Api(tags = "仪表盘管理模块")
@RestController
@RequestMapping("/admin")
public class DashboardAdminController {

    private final DashboardService dashboardService;
    private final RedisService redisService;

    public DashboardAdminController(DashboardService dashboardService, RedisService redisService) {
        this.dashboardService = dashboardService;
        this.redisService = redisService;
    }

    /**
    * 仪表盘显示数据
    *
    * @param
    * @return {@link Result}
    *
    * @author ryan
    *
    */
    @ApiOperation(value = "获取仪表盘数据")
    @GetMapping("/dashboard")
    public Result dashboard(){
        //今日点击量
        int todayPV = dashboardService.countVisitLogByToday();
        //今日用户访问量
        int todayUV = redisService.countBySet(RedisKey.IDENTIFICATION_SET);
        //博客总数量
        int blogCount = dashboardService.getBlogCount();
        //评论总数量
        int commentCount = dashboardService.getCommentCount();
        //各分类下文章数量
        Map<String, List> categoryBlogCountMap = dashboardService.getCategoryBlogCountMap();
        //各标签下评论的数量
        Map<String, List> tagBlogCountMap = dashboardService.getTagBlogCountMap();
        //访客记录
        Map<String, List> visitorRecordMap = dashboardService.getVisitRecordMap();
        //访客城市信息
        List<CityVisitor> cityVisitorList = dashboardService.getCityVisitorList();
        //所有信息放入map
        Map<String, Object> map = new HashMap<>();
        map.put("pv",todayPV);
        map.put("uv",todayUV);
        map.put("blogCount",blogCount);
        map.put("commentCount",commentCount);
        map.put("category",categoryBlogCountMap);
        map.put("tag",tagBlogCountMap);
        map.put("visitRecord",visitorRecordMap);
        map.put("cityVisitor",cityVisitorList);

        return Result.success(map);
    }
}
