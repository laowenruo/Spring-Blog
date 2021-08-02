package cn.isbut.mapper;

import cn.isbut.dto.BlogDTO;
import cn.isbut.dto.BlogViewDTO;
import cn.isbut.dto.BlogVisibilityDTO;
import cn.isbut.vo.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.isbut.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Ryan
 * @Description: 博客持久层接口
 */
@Mapper
public interface BlogMapper extends BaseMapper {

    /**
    * @Description: 新增博客
    * @param blogDTO
     * @return {@link int}
    * @throws
    * @data 2021/4/25 20:39
    *
    */
    int saveBlog(BlogDTO blogDTO);

    /**
    * @Description: 维护博客和标签表
    * @param tagId
    * @param blogId
    * @return {@link int}
    * @throws
    * @data 2021/5/27 13:30
    *
    */
    int saveBlogAndTag(Long tagId, Long blogId);

    /**
    * @Description: 删除博客和标签关联
    * @param id
    * @return {@link int}
    * @throws
    *
    */
    int deleteBlogAndTagByBlogId(Long id);

    /**
    * @Description: 删除博客
    * @param id
    * @return {@link int}
    * @throws
    *
    */
    int deleteBlogById(Long id);

    /**
    * @Description: 编辑博客
    * @param blogDTO
    * @return {@link int}
    *
    */
    int updateBlog(BlogDTO blogDTO);

    /**
    * @Description: 更新博客推荐状态
    * @param blogId
    * @param recommend
    * @return {@link int}
    * @throws
    *
    */
    int updateBlogRecommendById(Long blogId, Boolean recommend);

    /**
    * @Description: 更新博客置顶状态
    * @param blogId
    * @param top
    * @return {@link int}
    * @throws
    *
    */
    int updateBlogTopById(Long blogId, Boolean top);

    /**
    * @Description: 更新博客可见性状态
    * @param blogId
    * @param bv
    * @return {@link int}
    * @throws
    *
    */
    int updateBlogVisibilityById(Long blogId, BlogVisibilityDTO bv);

    /**
    * @Description: 根据Id查询博客，用于新增博客
    * @param id
    * @return {@link Blog}
    * @throws
    *
    */
    Blog getBlogById(Long id);

    /**
    * @Description: 根据类型名称获取博客List
    * @param categoryName
    * @return {@link List< Blog>}
    * @throws
    *
    */
    List<BlogInfoVO> getBlogInfoListByCategoryNameAndIsPublished(String categoryName);
    
    /**
    * @Description: 根据标签名称获取博客列表
    * @param tagName
    * @return {@link List< Blog>}
    * @throws
    *
    */
    List<BlogInfoVO> getBlogInfoListByTagNameAndIsPublished(String tagName);

    /**
    * @Description: 按标题和分类查询博客List
    * @param title
    * @param categoryId
    * @return {@link List< Blog>}
    * @throws
    *
    */
    List<Blog> getBlogListByTitleAndCategoryId(String title, Integer categoryId);

    /**
    * @Description: 按关键字根据文章内容搜索公开且无密码保护的博客文章
    * @param query
    * @return {@link List< SearchBlogVO >}
    * @throws
    *
    */
    List<SearchBlogVO> getSearchBlogListByQueryAndIsPublished(String query);

    /**
    * @Description: 查询最新的公开博客
    * @param
    * @return {@link List<  NewBlogVO >}
    * @throws
    *
    */
    List<NewBlogVO> getNewBlogListByIsPublished();
    
    /**
    * @Description: 查询所有博客id和title
    * @param
    * @return {@link List< Blog>}
    * @throws
    *
    */
    List<Blog> getBlogIdAndTitleList();

    /**
    * @Description: 查询公开博客的简要信息
    * @param
    * @return {@link List< BlogInfoVO>}
    * @throws
    *
    */
    List<BlogInfoVO> getBlogInfoListByIsPublished();

    /**
    * @Description: 按id查询公开博客
    * @param id
    * @return {@link BlogDetailVO}
    * @throws
    *
    */
    BlogDetailVO getBlogByIdAndIsPublished(Long id);

    /**
    * @Description: 查询公开博客年月List
    * @param
    * @return {@link List< String>}
    * @throws
    *
    */
    List<String> getGroupYearMonthByIsPublished();

    /**
    * @Description: 按年份查询博客
    * @param yearMonth
    * @return {@link List< Blog>}
    * @throws
    *
    */
    List<ArchiveBlogVO> getArchiveBlogListByYearMonthAndIsPublished(String yearMonth);

    /**
    * @Description: 查询随机的公开且推荐文章
    * @param limitNum
    * @return {@link List< RandomBlogVO>}
    * @throws
    *
    */
    List<RandomBlogVO> getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend(Integer limitNum);

    /**
    * @Description: 查询所有文章的浏览量
    * @param
    * @return {@link List<  BlogViewDTO >}
    *
    */
    List<BlogViewDTO> getBlogViewsList();

    String getTitleByBlogId(Long id);

    List<ArchiveInfoVO>getTitleAndCreateTime();
    /**
    * @Description: 更新博客阅读次数
    * @param blogId
    * @param views
    * @return {@link int}
    * @throws
    *
    */
    int updateViews(Long blogId, Integer views);

    /**
    * @Description: 获取博客总访问量
    * @param
    * @return {@link Integer}
    * @throws
    *
    */
    int getViews();

    /**
    * @Description: 查询博客总数
    * @param
    * @return {@link int}
    * @throws
    *
    */
    int countBlog();

    /**
    * @Description: 查询公开博客总数
    * @param
    * @return {@link int}
    * @throws
    *
    */
    int countBlogByIsPublished();

    /**
    * @Description: 按分类id查询博客数量
    * @param categoryId
    * @return {@link int}
    * @throws
    *
    */
    int countBlogByCategoryId(Long categoryId);

    /**
    * @Description: 按标签id查询博客数量
    * @param tagId
    * @return {@link int}
    * @throws
    *
    */
    int countBlogByTagId(Long tagId);

    /**
    * @Description: 查询博客是否启用评论
    * @param blogId
    * @return {@link Boolean}
    * @throws
    *
    */
    Boolean getCommentEnabledByBlogId(Long blogId);

    /**
    * @Description: 查询博客是否公开
    * @param blogId
    * @return {@link Boolean}
    * @throws
    *
    */
    Boolean getPublishedByBlogId(Long blogId);

    /**
    * @Description: 查询每个分类的博客数量
    * @param
    * @return {@link List< CategoryBlogCountVO>}
    *
    */
    List<CategoryBlogCountVO> getCategoryBlogCountList();


}
