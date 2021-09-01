package cn.isbut.service;

import cn.isbut.model.dto.Blog;
import cn.isbut.model.dto.BlogVisibility;
import cn.isbut.model.vo.BlogDetail;
import cn.isbut.model.vo.BlogInfo;
import cn.isbut.model.vo.NewBlog;
import cn.isbut.model.vo.PageResult;
import cn.isbut.model.vo.RandomBlog;
import cn.isbut.model.vo.SearchBlog;

import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 */
public interface BlogService {
	/**
	 * 获取博客文章列表
	 * @param title 标题
	 * @param categoryId 分类id
	 * @return 文章列表
	 */
	List<cn.isbut.entity.Blog> getListByTitleAndCategoryId(String title, Integer categoryId);

	/**
	 * 按关键字根据文章内容搜索公开且无密码保护的博客文章
	 * @param query
	 * @return 文章
	 */
	List<SearchBlog> getSearchBlogListByQueryAndIsPublished(String query);

	/**
	 * 获取所有博客id和title 供评论分类的选择
	 * @return 博文
	 */
	List<cn.isbut.entity.Blog> getIdAndTitleList();

	/**
	 * 获取站点配置信息、最新推荐博客、分类列表、标签云、随机博客
	 * @return 首页数据
	 */
	List<NewBlog> getNewBlogListByIsPublished();

	/**
	 * 按置顶、创建时间排序 分页查询博客简要信息列表
	 * @param pageNum 分页
	 * @return  分页展示博客简要信息
	 */
	PageResult<BlogInfo> getBlogInfoListByIsPublished(Integer pageNum);

	/**
	 * 根据分类name分页查询公开博客列表
	 * @param categoryName 分类名
	 * @param pageNum 分页
	 * @return 获取分类下博文
	 */
	PageResult<BlogInfo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName, Integer pageNum);

	/**
	 * 根据标签name分页查询公开博客列表
	 * @param tagName 标签名
	 * @param pageNum 分页
	 * @return 获取标签下博文
	 */
	PageResult<BlogInfo> getBlogInfoListByTagNameAndIsPublished(String tagName, Integer pageNum);

	/**
	 * 按年月分组归档公开博客 统计公开博客总数
	 * @return 统计信息
	 */
	Map<String, Object> getArchiveBlogAndCountByIsPublished();

	/**
	 * 获取随机博文
	 * @return 随机博文
	 */
	List<RandomBlog> getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend();

	/**
	 * 删除博客文章、删除博客文章下的所有评论、同时维护 blog_tag 表
	 * @param id 博文id
	 */
	void deleteBlogById(Integer id);

	/**
	 * 删除博客文章时维护 blog_tag 表
	 * @param blogId 博文id
	 */
	void deleteBlogTagByBlogId(Integer blogId);

	/**
	 * 保存博客
	 * @param blog 博文
	 */
	void saveBlog(Blog blog);

	/**
	 * 保存博客标签
	 * @param blogId 博文id
	 * @param tagId 标签id
	 */
	void saveBlogTag(Integer blogId, Integer tagId);

	/**
	 * 更新博客推荐状态
	 * @param blogId
	 * @param recommend
	 */
	void updateBlogRecommendById(Integer blogId, Boolean recommend);

	/**
	 * 更新博客可见性状态
	 * @param blogId 博文id
	 * @param blogVisibility 博文设置
	 */
	void updateBlogVisibilityById(Integer blogId, BlogVisibility blogVisibility);

	/**
	 * 更新博客置顶状态
	 * @param blogId 博文id
	 * @param top 置顶状态
	 */
	void updateBlogTopById(Integer blogId, Boolean top);

	/**
	 * 更新redis的文章浏览量
	 * @param blogId 博文id
	 */
	void updateViewsToRedis(Integer blogId);

	/**
	 * 更新文章浏览量
	 * @param blogId 博文id
	 * @param views 浏览量
	 */
	void updateViews(Integer blogId, Integer views);

	/**
	 * 通过id得到博客文章
	 * @param id 博文id
	 * @return 博文
	 */
	cn.isbut.entity.Blog getBlogById(Integer id);

	/**
	 * 通过id得到博客标题
	 * @param id 博文id
	 * @return 博文标题
	 */
	String getTitleByBlogId(Integer id);

	/**
	 * 通过id得到博客详情
	 * @param id 博文id
	 * @return 博文详情
	 */
	BlogDetail getBlogByIdAndIsPublished(Integer id);

	/**
	 * 得到博客文章的密码
	 * @param blogId 博文id
	 * @return 博文密码
	 */
	String getBlogPassword(Integer blogId);

	/**
	 * 更新博文
	 * @param blog 博文
	 */
	void updateBlog(Blog blog);

	/**
	 * 统计发布博文数量
	 * @return 数量
	 */
	int countBlogByIsPublished();

	/**
	 * 统计某一分类下博文数量
	 * @param categoryId 分类id
	 * @return 数量
	 */
	int countBlogByCategoryId(Integer categoryId);

	/**
	 * 统计某一标签下博文数量
	 * @param tagId 标签id
	 * @return 数量
	 */
	int countBlogByTagId(Integer tagId);

	/**
	 * 博文评论设置开关
	 * @param blogId 博文id
	 * @return 布尔值
	 */
	Boolean getCommentEnabledByBlogId(Integer blogId);

	/**
	 * 得到博文的可见性
	 * @param blogId 博文id
	 * @return 布尔值
	 */
	Boolean getPublishedByBlogId(Integer blogId);
}
