package cn.isbut.controller;

import cn.isbut.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.annotation.VisitLogger;
import cn.isbut.entity.User;
import cn.isbut.model.dto.BlogPassword;
import cn.isbut.model.vo.BlogDetail;
import cn.isbut.model.vo.BlogInfo;
import cn.isbut.model.vo.PageResult;
import cn.isbut.model.vo.Result;
import cn.isbut.model.vo.SearchBlog;
import cn.isbut.service.BlogService;
import cn.isbut.service.impl.UserServiceImpl;
import cn.isbut.util.StringUtils;

import java.util.List;

/**
 * @author Ryan
 * @Description: 博客相关
 *
 */
@RestController
public class BlogController {

	BlogService blogService;

	UserServiceImpl userService;

	/**
	 * 按置顶、创建时间排序 分页查询博客简要信息列表
	 *
	 * @param pageNum 页码
	 * @return result
	 */
	@VisitLogger(behavior = "访问页面", content = "首页")
	@GetMapping("/blogs")
	public Result blogs(@RequestParam(defaultValue = "1") Integer pageNum) {
		PageResult<BlogInfo> pageResult = blogService.getBlogInfoListByIsPublished(pageNum);
		return Result.ok("请求成功", pageResult);
	}

	/**
	 * 按id获取公开博客详情
	 *
	 * @param id  博客id
	 * @param jwt 密码保护文章的访问Token
	 * @return result
	 */
	@VisitLogger(behavior = "查看博客")
	@GetMapping("/blog")
	public Result getBlog(@RequestParam Integer id,
	                      @RequestHeader(value = "Authorization", defaultValue = "") String jwt) {
		BlogDetail blog = blogService.getBlogByIdAndIsPublished(id);
		//对密码保护的文章校验Token
		if (!"".equals(blog.getPassword())) {
			if (JwtUtils.judgeTokenIsExist(jwt)) {
				try {
					String subject = JwtUtils.getTokenBody(jwt).getSubject();
					if (subject.startsWith("admin:")) {
						//博主身份Token
						String username = subject.replace("admin:", "");
						User admin = (User) userService.loadUserByUsername(username);
						if (admin == null) {
							return Result.create(403, "博主身份Token已失效，请重新登录！");
						}
					} else {
						//经密码验证后的Token
						Integer tokenBlogId = Integer.parseInt(subject);
						//博客id不匹配，验证不通过，可能博客id改变或客户端传递了其它密码保护文章的Token
						if (!tokenBlogId.equals(id)) {
							return Result.create(403, "Token不匹配，请重新验证密码！");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return Result.create(403, "Token已失效，请重新验证密码！");
				}
			} else {
				return Result.create(403, "此文章受密码保护，请验证密码！");
			}
			blog.setPassword("");
		}
		blogService.updateViewsToRedis(id);
		return Result.ok("获取成功", blog);
	}

	/**
	 * 校验受保护文章密码是否正确，正确则返回jwt
	 *
	 * @param blogPassword 博客id、密码
	 * @return result
	 */
	@VisitLogger(behavior = "校验博客密码")
	@PostMapping("/checkBlogPassword")
	public Result checkBlogPassword(@RequestBody BlogPassword blogPassword) {
		String password = blogService.getBlogPassword(blogPassword.getBlogId());
		if (password.equals(blogPassword.getPassword())) {
			//生成有效时间一个月的Token
			String jwt = JwtUtils.generateToken(blogPassword.getBlogId().toString(), 1000 * 3600 * 24 * 30L);
			return Result.ok("密码正确", jwt);
		} else {
			return Result.create(403, "密码错误");
		}
	}

	/**
	 * 按关键字根据文章内容搜索公开且无密码保护的博客文章
	 *
	 * @param query 关键字字符串
	 * @return result
	 */
	@VisitLogger(behavior = "搜索博客")
	@GetMapping("/searchBlog")
	public Result searchBlog(@RequestParam String query) {
		//校验关键字字符串合法性
		if (StringUtils.isEmpty(query) || StringUtils.hasSpecialChar(query) || query.trim().length() > 20) {
			return Result.error("参数错误");
		}
		List<SearchBlog> searchBlogs = blogService.getSearchBlogListByQueryAndIsPublished(query.trim());
		return Result.ok("获取成功", searchBlogs);
	}

	@Autowired
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}

	@Autowired
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
}
