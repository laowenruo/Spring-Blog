package cn.isbut.mapper;

import cn.isbut.model.dto.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.model.dto.BlogView;
import cn.isbut.model.dto.BlogVisibility;
import cn.isbut.model.vo.ArchiveBlog;
import cn.isbut.model.vo.BlogDetail;
import cn.isbut.model.vo.BlogInfo;
import cn.isbut.model.vo.CategoryBlogCount;
import cn.isbut.model.vo.NewBlog;
import cn.isbut.model.vo.RandomBlog;
import cn.isbut.model.vo.SearchBlog;
import java.util.List;

/**
 * @author Ryan
 * @Description: 博客文章持久层接口
 *
 */
@Mapper
@Repository
public interface BlogMapper {
	/**
	 * 按标题和分类查询博客List
	 * @param title 标题
	 * @param categoryId 分类id
	 * @return 博文
	 */
	List<cn.isbut.entity.Blog> getListByTitleAndCategoryId(String title, Integer categoryId);

	/**
	 * 根据内容查询博文
	 * @param query 查询字符串
	 * @return 博文
	 */
	List<SearchBlog> getSearchBlogListByQueryAndIsPublished(String query);

	/**
	 * 得到id和博文标题
	 * @return 博文
	 */
	List<cn.isbut.entity.Blog> getIdAndTitleList();

	/**
	 * 得到新发布的博文
	 * @return 博文
	 */
	List<NewBlog> getNewBlogListByIsPublished();

	/**
	 * 得到博文相关信息
	 * @return 博文信息
	 */
	List<BlogInfo> getBlogInfoListByIsPublished();

	/**
	 * 通过分类名查询博文
	 * @param categoryName 分类名
	 * @return 博文信息
	 */
	List<BlogInfo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName);

	/**
	 * 根据标签查询博文
	 * @param tagName 标签名
	 * @return 博文信息
	 */
	List<BlogInfo> getBlogInfoListByTagNameAndIsPublished(String tagName);

	/**
	 * 查询公开博客年月List
	 * @return 日期
	 */
	List<String> getGroupYearMonthByIsPublished();

	/**
	 * 按年月查询公开博客简要信息List
	 * @param yearMonth 日期
	 * @return 归档博文简要信息
	 */
	List<ArchiveBlog> getArchiveBlogListByYearMonthAndIsPublished(String yearMonth);

	/**
	 *查询随机的公开且推荐文章
	 * @param limitNum 限制
	 * @return 随机博文
	 */
	List<RandomBlog> getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend(Integer limitNum);

	/**
	 *查询所有文章的浏览量
	 * @return 博文浏览量
	 */
	List<BlogView> getBlogViewsList();

	/**
	 * 按id删除博客
	 * @param id 博文id
	 * @return 状态
	 */
	int deleteBlogById(Integer id);

	/**
	 * 删除博文标签
	 * @param blogId 博文id
	 * @return 删除状态
	 */
	int deleteBlogTagByBlogId(Integer blogId);

	/**
	 * 保存博文
	 * @param blog 博文
	 * @return 成功与否状态
	 */
	int saveBlog(Blog blog);

	/**
	 * 保存博文标签
	 * @param blogId 博文id
 	 * @param tagId 标签id
	 * @return 成功与否状态
	 */
	int saveBlogTag(Integer blogId, Integer tagId);

	/**
	 * 设置博文相关
	 * @param blogId 博文id
	 * @param bv 可见性对象
	 * @return 成功与否状态
	 */
	int updateBlogVisibilityById(Integer blogId, BlogVisibility bv);

	/**
	 * 更新博文
	 * @param blogId 博文id
	 * @param top 置顶？
	 * @return 成功与否状态
	 */
	int updateBlogTopById(Integer blogId, Boolean top);

	/**
	 * 更新阅读量
	 * @param blogId 博文id
	 * @param views 阅读量
	 * @return 成功与否状态
	 */
	int updateViews(Integer blogId, Integer views);

	/**
	 * 得到博文通过id
	 * @param id 博文id
	 * @return 博文
	 */
	cn.isbut.entity.Blog getBlogById(Integer id);

	/**
	 * 通过id得到博文名称
	 * @param id 博文id
	 * @return 博文标题
	 */
	String getTitleByBlogId(Integer id);

	/**
	 * 得到博文通过id
	 * @param id 博文id
	 * @return 博文详情
	 */
	BlogDetail getBlogByIdAndIsPublished(Integer id);

	/**
	 * 查询受密码保护文章密码
	 * @param blogId 博文id
	 * @return 密码
	 */
	String getBlogPassword(Integer blogId);

	/**
	 * 更新博文
	 * @param blog 博文
	 * @return 成功与否状态
	 */
	int updateBlog(Blog blog);

	/**
	 * 得到博文数量
	 * @return 数量
	 */
	int countBlog();

	/**
	 * 得到所有发布博文数量
	 * @return 数量
	 */
	int countBlogByIsPublished();

	/**
	 * 查询分类博文数量
	 * @param categoryId 分类id
	 * @return 数量
	 */
	int countBlogByCategoryId(Integer categoryId);

	/**
	 * 查询标签博文数量
	 * @param tagId 标签id
	 * @return 数量
	 */
	int countBlogByTagId(Integer tagId);

	/**
	 * 查询博文评论开关
	 * @param blogId 博文id
	 * @return 开关状态
	 */
	Boolean getCommentEnabledByBlogId(Integer blogId);

	/**
	 * 查询博文发布状态
	 * @param blogId 博文id
	 * @return 发布状态
	 */
	Boolean getPublishedByBlogId(Integer blogId);

	/**
	 * 得到分类下的博文
	 * @return  分类博文数量
	 */
	List<CategoryBlogCount> getCategoryBlogCountList();

	/**
	 * 更新博文推荐状态
	 * @param blogId 博文id
	 * @param recommend 推荐状态
	 * @return 成功状态
	 */
    int updateBlogRecommendById(Integer blogId, Boolean recommend);
}
