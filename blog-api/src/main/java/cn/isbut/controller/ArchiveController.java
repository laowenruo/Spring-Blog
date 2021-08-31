package cn.isbut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.annotation.VisitLogger;
import cn.isbut.model.vo.Result;
import cn.isbut.service.BlogService;

import java.util.Map;

/**
 * @author Ryan
 * @Description: 归档页面
 *
 */
@RestController
public class ArchiveController {
	@Autowired
	BlogService blogService;

	/**
	 * 按年月分组归档公开博客 统计公开博客总数
	 *
	 * @return result
	 */
	@VisitLogger(behavior = "访问页面", content = "文章归档")
	@GetMapping("/archives")
	public Result archives() {
		Map<String, Object> archiveBlogMap = blogService.getArchiveBlogAndCountByIsPublished();
		return Result.ok("请求成功", archiveBlogMap);
	}
}
