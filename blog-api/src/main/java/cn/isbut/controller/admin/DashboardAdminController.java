package cn.isbut.controller.admin;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.config.RedisKeyConfig;
import cn.isbut.model.vo.Result;
import cn.isbut.service.DashboardService;
import cn.isbut.service.RedisService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 后台管理仪表盘
 *
 */
@RestController
@RequestMapping("/admin")
public class DashboardAdminController {

	DashboardService dashboardService;

	RedisService redisService;

	@GetMapping("/dashboard")
	public Result dashboard() {
		int todayPV = dashboardService.countVisitLogByToday();
		int todayUV = redisService.countBySet(RedisKeyConfig.IDENTIFICATION_SET);
		int blogCount = dashboardService.getBlogCount();
		int commentCount = dashboardService.getCommentCount();
		var categoryBlogCountMap = dashboardService.getCategoryBlogCountMap();
		var tagBlogCountMap = dashboardService.getTagBlogCountMap();
		var visitRecordMap = dashboardService.getVisitRecordMap();
		var cityVisitorList = dashboardService.getCityVisitorList();

		Map<String, Object> map = new HashMap<>(8);
		map.put("pv", todayPV);
		map.put("uv", todayUV);
		map.put("blogCount", blogCount);
		map.put("commentCount", commentCount);
		map.put("category", categoryBlogCountMap);
		map.put("tag", tagBlogCountMap);
		map.put("visitRecord", visitRecordMap);
		map.put("cityVisitor", cityVisitorList);
		return Result.ok("获取成功", map);
	}

	@Autowired
	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@Autowired
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
}
