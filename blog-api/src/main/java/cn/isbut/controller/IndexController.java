package cn.isbut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.entity.Category;
import cn.isbut.entity.Tag;
import cn.isbut.model.vo.NewBlog;
import cn.isbut.model.vo.RandomBlog;
import cn.isbut.model.vo.Result;
import cn.isbut.service.BlogService;
import cn.isbut.service.CategoryService;
import cn.isbut.service.SiteSettingService;
import cn.isbut.service.TagService;

import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 站点相关
 */

@RestController
public class IndexController {

	SiteSettingService siteSettingService;

	BlogService blogService;

	CategoryService categoryService;

	TagService tagService;

	/**
	 * 获取站点配置信息、最新推荐博客、分类列表、标签云、随机博客
	 *
	 * @return result
	 */
	@GetMapping("/site")
	public Result site() {
		Map<String, Object> map = siteSettingService.getSiteInfo();
		List<NewBlog> newBlogList = blogService.getNewBlogListByIsPublished();
		List<Category> categoryList = categoryService.getCategoryNameList();
		List<Tag> tagList = tagService.getTagListNotId();
		List<RandomBlog> randomBlogList = blogService.getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend();
		map.put("newBlogList", newBlogList);
		map.put("categoryList", categoryList);
		map.put("tagList", tagList);
		map.put("randomBlogList", randomBlogList);
		return Result.ok("请求成功", map);
	}

	@Autowired
	public void setSiteSettingService(SiteSettingService siteSettingService) {
		this.siteSettingService = siteSettingService;
	}

	@Autowired
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}

	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Autowired
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}
}
