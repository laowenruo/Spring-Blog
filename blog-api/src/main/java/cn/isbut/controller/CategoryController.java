package cn.isbut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.annotation.VisitLogger;
import cn.isbut.model.vo.BlogInfo;
import cn.isbut.model.vo.PageResult;
import cn.isbut.model.vo.Result;
import cn.isbut.service.BlogService;

/**
 * @author Ryan
 * @Description: 分类
 *
 */
@RestController
public class CategoryController {

	BlogService blogService;

	/**
	 * 根据分类name分页查询公开博客列表
	 *
	 * @param categoryName 分类name
	 * @param pageNum      页码
	 * @return result
	 */
	@VisitLogger(behavior = "查看分类")
	@GetMapping("/category")
	public Result category(@RequestParam String categoryName,
	                       @RequestParam(defaultValue = "1") Integer pageNum) {
		PageResult<BlogInfo> pageResult = blogService.getBlogInfoListByCategoryNameAndIsPublished(categoryName, pageNum);
		return Result.ok("请求成功", pageResult);
	}

	@Autowired
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}
}
