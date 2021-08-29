package cn.isbut.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.config.RedisKeyConfig;
import cn.isbut.entity.CityVisitor;
import cn.isbut.model.vo.Result;
import cn.isbut.service.DashboardService;
import cn.isbut.service.RedisService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 后台管理仪表盘
 *
 */
@RestController
@RequestMapping("/admin")
public class DashboardAdminController {
	@Autowired
	DashboardService dashboardService;
	@Autowired
	RedisService redisService;

	@GetMapping("/dashboard")
	public Result dashboard() {
		int todayPV = dashboardService.countVisitLogByToday();
		int todayUV = redisService.countBySet(RedisKeyConfig.IDENTIFICATION_SET);
		int blogCount = dashboardService.getBlogCount();
		int commentCount = dashboardService.getCommentCount();
		Map<String, List> categoryBlogCountMap = dashboardService.getCategoryBlogCountMap();
		Map<String, List> tagBlogCountMap = dashboardService.getTagBlogCountMap();
		Map<String, List> visitRecordMap = dashboardService.getVisitRecordMap();
		List<CityVisitor> cityVisitorList = dashboardService.getCityVisitorList();

		Map<String, Object> map = new HashMap<>();
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
}
