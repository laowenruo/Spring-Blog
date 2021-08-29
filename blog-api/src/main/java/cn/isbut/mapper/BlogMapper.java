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
	 * 通过分类和标签查询博文
	 * @param title
	 * @param categoryId
	 * @return
	 */
	List<cn.isbut.entity.Blog> getListByTitleAndCategoryId(String title, Integer categoryId);

	/**
	 * 根据内容查询博文
	 * @param query
	 * @return
	 */
	List<SearchBlog> getSearchBlogListByQueryAndIsPublished(String query);

	/**
	 * 得到id和博文标题
	 * @return
	 */
	List<cn.isbut.entity.Blog> getIdAndTitleList();

	/**
	 * 得到新发布的博文
	 * @return
	 */
	List<NewBlog> getNewBlogListByIsPublished();

	/**
	 * 得到博文相关信息
	 * @return
	 */
	List<BlogInfo> getBlogInfoListByIsPublished();

	/**
	 * 通过分类名查询博文
	 * @param categoryName
	 * @return
	 */
	List<BlogInfo> getBlogInfoListByCategoryNameAndIsPublished(String categoryName);

	/**
	 * 根据标签查询博文
	 * @param tagName
	 * @return
	 */
	List<BlogInfo> getBlogInfoListByTagNameAndIsPublished(String tagName);

	/**
	 * 查询公开博客年月List
	 * @return
	 */
	List<String> getGroupYearMonthByIsPublished();

	/**
	 * 按年月查询公开博客简要信息List
	 * @param yearMonth
	 * @return
	 */
	List<ArchiveBlog> getArchiveBlogListByYearMonthAndIsPublished(String yearMonth);

	/**
	 *查询随机的公开且推荐文章
	 * @param limitNum
	 * @return
	 */
	List<RandomBlog> getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend(Integer limitNum);

	/**
	 *查询所有文章的浏览量
	 * @return
	 */
	List<BlogView> getBlogViewsList();

	/**
	 * 按id删除博客
	 * @param id
	 * @return
	 */
	int deleteBlogById(Integer id);

	/**
	 * 删除博文标签
	 * @param blogId
	 * @return
	 */
	int deleteBlogTagByBlogId(Integer blogId);

	/**
	 * 保存博文
	 * @param blog
	 * @return
	 */
	int saveBlog(Blog blog);

	/**
	 * 保存博文标签
	 * @param blogId
	 * @param tagId
	 * @return
	 */
	int saveBlogTag(Integer blogId, Integer tagId);

	/**
	 * 设置博文相关
	 * @param blogId
	 * @param bv
	 * @return
	 */
	int updateBlogVisibilityById(Integer blogId, BlogVisibility bv);

	/**
	 * 更新博文
	 * @param blogId
	 * @param top
	 * @return
	 */
	int updateBlogTopById(Integer blogId, Boolean top);

	/**
	 * 更新阅读量
	 * @param blogId
	 * @param views
	 * @return
	 */
	int updateViews(Integer blogId, Integer views);

	/**
	 * 得到博文通过id
	 * @param id
	 * @return
	 */
	cn.isbut.entity.Blog getBlogById(Integer id);

	/**
	 * 通过id得到博文名称
	 * @param id
	 * @return
	 */
	String getTitleByBlogId(Integer id);

	/**
	 * 得到博文通过id
	 * @param id
	 * @return
	 */
	BlogDetail getBlogByIdAndIsPublished(Integer id);

	/**
	 * 查询受密码保护文章密码
	 * @param blogId
	 * @return
	 */
	String getBlogPassword(Integer blogId);

	/**
	 * 更新博文
	 * @param blog
	 * @return
	 */
	int updateBlog(Blog blog);

	/**
	 * 得到博文数量
	 * @return
	 */
	int countBlog();

	/**
	 * 得到所有发布博文数量
	 * @return
	 */
	int countBlogByIsPublished();

	/**
	 * 查询分类博文数量
	 * @param categoryId
	 * @return
	 */
	int countBlogByCategoryId(Integer categoryId);

	/**
	 * 查询标签博文数量
	 * @param tagId
	 * @return
	 */
	int countBlogByTagId(Integer tagId);

	/**
	 * 查询博文评论开关
	 * @param blogId
	 * @return
	 */
	Boolean getCommentEnabledByBlogId(Integer blogId);

	/**
	 * 查询博文发布状态
	 * @param blogId
	 * @return
	 */
	Boolean getPublishedByBlogId(Integer blogId);

	/**
	 * 得到分类下的博文
	 * @return
	 */
	List<CategoryBlogCount> getCategoryBlogCountList();
}
