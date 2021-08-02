package cn.isbut.service.Impl;

import cn.isbut.dto.BlogDTO;
import cn.isbut.dto.BlogViewDTO;
import cn.isbut.dto.BlogVisibilityDTO;
import cn.isbut.exception.NotFoundException;
import cn.isbut.service.*;
import cn.isbut.task.RedisSyncScheduleTask;
import cn.isbut.util.MarkdownUtils;
import cn.isbut.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.isbut.common.RedisKey;
import cn.isbut.entity.Blog;
import cn.isbut.entity.User;
import cn.isbut.mapper.BlogMapper;
import cn.isbut.util.JacksonUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 博客文章业务层实现
 * @Author: ryan
 */
@Service
public class BlogServiceImpl implements BlogService {
    //必须注入，不然会报错No bean named 'RedisSyncScheduleTask' available
    @Autowired
    private RedisSyncScheduleTask redisSyncScheduleTask;

    private final BlogMapper blogMapper;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final RedisService redisService;
    private final UserService userService;
    //The dependencies of some of the beans in the application context form a cycle:
    //private final RedisSyncScheduleTask redisSyncScheduleTask;
    //随机博客显示5条
    private static final int randomBlogLimitNum = 5;
    //最新推荐博客显示3条
    private static final int newBlogPageSize = 3;
    //每页显示7条博客简介
    private static final int pageSize = 7;
    //博客简介列表排序方式
    private static final String orderBy = "is_top desc, create_time desc";

    public BlogServiceImpl(BlogMapper blogMapper, TagService tagService, CategoryService categoryService, RedisService redisService, UserService userService) {
        this.blogMapper = blogMapper;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.redisService = redisService;
        this.userService = userService;
    }

    /**
     * 项目启动时，保存所有博客的浏览量到Redis
     */
    @PostConstruct
    private void saveBlogViewsToRedis() {
        String redisKey = RedisKey.BLOG_VIEWS_MAP;
        //Redis中没有存储博客浏览量的Hash
        if (!redisService.hasKey(redisKey)) {
            //从数据库中读取并存入Redis
            Map<Long, Integer> blogViewsMap = getBlogViewsMap();
            redisService.saveMapToHash(redisKey, blogViewsMap);
        }
    }

    @Transactional
    @Override
    public int saveBlog(BlogDTO blogDTO) {
        if (blogMapper.saveBlog(blogDTO) != 1){
            throw new PersistenceException("添加博客失败");
        }
        redisService.saveKVToHash(RedisKey.BLOG_VIEWS_MAP, blogDTO.getId(), 0);
        deleteBlogRedisCache();
        return 1;
    }

    @Transactional
    @Override
    public int saveBlogAndTag(Long tagId, Long blogId) {
        if (blogMapper.saveBlogAndTag(blogId, tagId) != 1) {
            throw new PersistenceException("维护博客标签关联表失败");
        }
        return 1;
    }

    @Transactional
    @Override
    public int deleteBlogById(Long id) {
        if (blogMapper.deleteBlogById(id) != 1){
            throw new NotFoundException("该博客不存在");
        }
        deleteBlogRedisCache();
        redisService.deleteByHashKey(RedisKey.BLOG_VIEWS_MAP,id);
        return 1;
    }

    @Transactional
    @Override
    public int deleteBlogAndTagByBlogId(Long id) {
        if (blogMapper.deleteBlogAndTagByBlogId(id) == 0){
            throw new PersistenceException("维护博客和标签列表失败");
        }
        return 1;
    }

    @Transactional
    @Override
    public int updateBlog(BlogDTO blogDTO) {
        if (blogMapper.updateBlog(blogDTO) != 1){
            throw new PersistenceException("更新失败");
        }
        deleteBlogRedisCache();
        redisService.saveKVToHash(RedisKey.BLOG_VIEWS_MAP, blogDTO.getId(), blogDTO.getViews());
        return 1;
    }

    @Transactional
    @Override
    public int updateBlogRecommendById(Long blogId, Boolean recommend) {
        if (blogMapper.updateBlogRecommendById(blogId, recommend) != 1) {
            throw new PersistenceException("操作失败");
        }
        return 1;
    }

    @Transactional
    @Override
    public int updateBlogTopById(Long blogId, Boolean top) {
        if (blogMapper.updateBlogTopById(blogId, top) != 1) {
            throw new PersistenceException("操作失败");
        }
        redisService.deleteCacheByKey(RedisKey.HOME_BLOG_INFO_LIST);
        return 1;
    }

    @Transactional
    @Override
    public int updateBlogVisibilityById(Long blogId, BlogVisibilityDTO bv) {
        if (blogMapper.updateBlogVisibilityById(blogId, bv) != 1) {
            throw new PersistenceException("操作失败");
        }
        deleteBlogRedisCache();
        return 1;
    }

    @Transactional
    @Override
    public int updateViews(Long blogId, Integer views) {
        if (blogMapper.updateViews(blogId, views) != 1) {
            throw new PersistenceException("更新失败");
        }
        return 1;
    }

    @Transactional
    @Override
    public void updateViewsToRedis(Long blogId) {
        redisService.incrementByHashKey(RedisKey.BLOG_VIEWS_MAP, blogId, 1);
    }

    @Override
    public String getTitleByBlogId(Long id) {
        return blogMapper.getTitleByBlogId(id);
    }

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = blogMapper.getBlogById(id);
        if (blog == null) {
            throw new NotFoundException("博客不存在");
        }
        //将浏览量设置为Redis中的最新值
        int view = (int) redisService.getValueByHashKey(RedisKey.BLOG_VIEWS_MAP, blog.getId());
        blog.setViews(view);
        return blog;
    }


    @Override
    public PageResultVO<BlogInfoVO> getBlogInfoListByCategoryNameAndIsPublished(String categoryName, Integer pageNum) {
        //设置分页属性
        PageHelper.startPage(pageNum, pageSize, orderBy);
        //获取 按分类名称查询的博客
        List<BlogInfoVO> blogInfoVOList = processBlogInfo(blogMapper.getBlogInfoListByCategoryNameAndIsPublished(categoryName));
        PageInfo<BlogInfoVO> pageInfo = new PageInfo<>(blogInfoVOList);
        PageResultVO<BlogInfoVO> pageResultVO = new PageResultVO<>(pageInfo.getPages(), pageInfo.getList());
        //将pageResultVO数据中博客的访问量设置成redis中的最新值
        setBlogViewsFromRedisToPageResult(pageResultVO);
        return pageResultVO;
    }


    @Override
    public PageResultVO<BlogInfoVO> getBlogInfoListByTagNameAndIsPublished(String tagName, Integer pageNum) {
        //设置分页属性
        PageHelper.startPage(pageNum, pageSize, orderBy);
        //获取 按标签名称查询的博客
        List<BlogInfoVO> blogInfoVOList = processBlogInfo(blogMapper.getBlogInfoListByTagNameAndIsPublished(tagName));
        PageInfo<BlogInfoVO> pageInfo = new PageInfo<>(blogInfoVOList);
        PageResultVO<BlogInfoVO> pageResultVO = new PageResultVO<>(pageInfo.getPages(), pageInfo.getList());
        //将pageResultVO数据中博客的访问量设置成redis中的最新值
        setBlogViewsFromRedisToPageResult(pageResultVO);
        return pageResultVO;
    }

    @Override
    public List<Blog> getBlogListByTitleAndCategoryId(String title, Integer categoryId) {
        return blogMapper.getBlogListByTitleAndCategoryId(title, categoryId);
    }

    @Override
    public List<SearchBlogVO> getSearchBlogListByQueryAndIsPublished(String query) {
        List<SearchBlogVO> searchBlogVOList = blogMapper.getSearchBlogListByQueryAndIsPublished(query);
        for (SearchBlogVO searchBlogVO : searchBlogVOList) {
            //获取文章的内容
            String content = searchBlogVO.getContent();
            //设置内容长度为 contentLength
            int contentLength = content.length();
            //获取搜索的关键词 query 第一次出现在文章时候的索引
            //并且将头坐标begin设置为 关键字第一次索引前面第十个
            int begin = content.indexOf(query) - 10;
            //判断begin是否超出文章，如果超出文章头部边界则默认从文章开头
            begin = Math.max(begin, 0);
            //以begin之后21个字符为末尾
            int end = begin + 50;
            //判断是否超出文章尾部便捷
            end = Math.min(end, contentLength - 1);
            searchBlogVO.setContent(MarkdownUtils.markdownToHtmlExtensions(content.substring(begin, end)));
        }
        return searchBlogVOList;
    }

    @Override
    public List<NewBlogVO> getNewBlogListByIsPublished() {
        //设置 最新博客列表
        String redisKey = RedisKey.NEW_BLOG_LIST;
        //如果redis中有最新的博客列表，就返回redis中的最新博客列表
        List<NewBlogVO> newBlogVOListFromRedis = redisService.getListByValue(redisKey);
        if (newBlogVOListFromRedis != null){
            return newBlogVOListFromRedis;
        }
        //设置分页信息
        PageHelper.startPage(1,newBlogPageSize);
        //从数据库中获取最新博客信息
        List<NewBlogVO> newBlogVOList = blogMapper.getNewBlogListByIsPublished();
        //存入redis
        redisService.saveListToValue(redisKey,newBlogVOList);
        return newBlogVOList;
    }

    @Override
    public List<Blog> getBlogIdAndTitleList() {
        return blogMapper.getBlogIdAndTitleList();
    }

    @Override
    public PageResultVO<BlogInfoVO> getBlogInfoListByIsPublished(Integer pageNum) {
        String redisKey = RedisKey.HOME_BLOG_INFO_LIST;
        //从redis中获取当前页的文章
        PageResultVO<BlogInfoVO> pageResultVOFromRedis = redisService.getBlogInfoPageResultByHash(redisKey, pageNum);
        //如果不为空，则读取redis中最新的访客数后返回
        if (pageResultVOFromRedis != null){
            setBlogViewsFromRedisToPageResult(pageResultVOFromRedis);
            return pageResultVOFromRedis;
        }
        //redis中没有数据，则从数据库中查询，并且存入到redis中
        PageHelper.startPage(pageNum, pageSize, orderBy);
        //随机获取公开且随机的文章
        List<BlogInfoVO> blogInfoVOList = blogMapper.getBlogInfoListByIsPublished();
        //将文章描述转换成html，给文章添加标签list
        processBlogInfo(blogInfoVOList);
        //将文章list放入pageInfo
        PageInfo<BlogInfoVO> pageInfo = new PageInfo<>(blogInfoVOList);
        //将页数信息和文章列表放入PageResultVO对象中
        PageResultVO<BlogInfoVO> pageResultVO = new PageResultVO<>(pageInfo.getPages(), pageInfo.getList());
        setBlogViewsFromRedisToPageResult(pageResultVO);
        //放入缓存中
        redisService.saveKVToHash(redisKey, pageNum, pageResultVO);
        return pageResultVO;
    }

    @Override
    public BlogDetailVO getBlogByIdAndIsPublished(Long id) {
        BlogDetailVO blogDetailVO = blogMapper.getBlogByIdAndIsPublished(id);
        if (blogDetailVO == null){
            throw new NotFoundException("博客不存在");
        }
        //blogDetailVO.setContent(MarkdownUtils.markdownToHtmlExtensions(blogDetailVO.getContent()));
        blogDetailVO.setTags(tagService.getTagListByBlogId(blogDetailVO.getId()));
        int views = (int) redisService.getValueByHashKey(RedisKey.BLOG_VIEWS_MAP, blogDetailVO.getId());
        blogDetailVO.setViews(views);
        return blogDetailVO;
    }

    @Override
    public List<RandomBlogVO> getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend() {
        return blogMapper.getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend(randomBlogLimitNum);
    }

    @Override
    public Map<String, Object> getArchiveBlogAndCountByIsPublished() {
        //首先尝试从缓存中获取
        String redisKey = RedisKey.ARCHIVE_BLOG_MAP;
        Map<String, Object> archiveMapFromRedis = redisService.getMapByValue(redisKey);
        if (archiveMapFromRedis != null){
            return archiveMapFromRedis;
        }
        //从数据库中获取，并且存入redis
        Map<String, Object> map = new HashMap<>();
        //获取年月
        List<String> groupYearMonth = blogMapper.getGroupYearMonthByIsPublished();
        //将对应的年月和博客列表以 键值 对的方式存储
        Map<String, List<ArchiveBlogVO>> archiveBlogMap = new LinkedHashMap<>();
        for (String s : groupYearMonth) {
            List<ArchiveBlogVO> archiveBlogVOList = blogMapper.getArchiveBlogListByYearMonthAndIsPublished(s);
            archiveBlogMap.put(s,archiveBlogVOList);
        }
        //统计博客数量
        Integer count = countBlogByIsPublished();
        map.put("BlogMap",archiveBlogMap);
        map.put("count", count);
        //存入redis
        redisService.saveMapToValue(redisKey,map);
        return map;
    }

    @Override
    public int getViews() {
        return blogMapper.getViews();
    }

    @Override
    public int countBlogByIsPublished() {
        return blogMapper.countBlogByIsPublished();
    }

    @Override
    public int countBlogByCategoryId(Long categoryId) {
        return blogMapper.countBlogByCategoryId(categoryId);
    }

    @Override
    public int countBlogByTagId(Long tagId) {
        return blogMapper.countBlogByTagId(tagId);
    }

    @Override
    public Boolean getCommentEnabledByBlogId(Long blogId) {
        return blogMapper.getCommentEnabledByBlogId(blogId);
    }

    @Override
    public Boolean getPublishedByBlogId(Long blogId) {
        return blogMapper.getPublishedByBlogId(blogId);
    }

    @Override
    public IndexInfoVO getIndexInfo() {
        //获取博主信息
        User user = userService.getUserInfo(1L);
        //获取文章数量
        Integer blogCount = blogMapper.countBlog();
        //获取分类数量
        Integer categoryCount = categoryService.getCategoryList().size();
        //获取标签数量
        Integer tagCount = tagService.getTagList().size();
        //todo 实现发布公告
        String notice = "发布你的第一篇公告吧";
        //查询访问量
        String viewCount = "999";
        IndexInfoVO indexInfoVO = new IndexInfoVO(user.getNickname(),user.getAvatar(),blogCount,categoryCount,tagCount,
                notice,viewCount);
        return indexInfoVO;
    }

    @Override
    public PageResultVO<ArchiveInfoVO> getArchiveBlog(Integer pageNum) {
        PageHelper.startPage(pageNum, 7, "create_time desc");
        PageInfo<ArchiveInfoVO> pageInfo = new PageInfo<>(blogMapper.getTitleAndCreateTime());
        PageResultVO<ArchiveInfoVO> pageResultVO = new PageResultVO<>(pageInfo.getPages(), pageInfo.getList());
        return pageResultVO;
    }

    /**
    * 获取博客访客map
    * @return {@link Map
    * @author ryan
    * @data 2021/5/28 21:39
    *
    */
    private Map<Long, Integer> getBlogViewsMap() {
        List<BlogViewDTO> blogViewDTOList = blogMapper.getBlogViewsList();
        Map<Long, Integer> blogViewsMap = new HashMap<>();
        for (BlogViewDTO blogView : blogViewDTOList) {
            blogViewsMap.put(blogView.getId(), blogView.getViews());
        }
        return blogViewsMap;
    }

    /**
    * 将pageResultVO数据中博客的访问量设置成redis中的最新值
    * @param pageResultVO`
    * @author ryan
    * @data 2021/5/28 19:16
    *
    */
    private void setBlogViewsFromRedisToPageResult(PageResultVO<BlogInfoVO> pageResultVO){
        String redisKey = RedisKey.BLOG_VIEWS_MAP;
        List<BlogInfoVO> blogInfoVOList = pageResultVO.getList();
        for (int i = 0; i < blogInfoVOList.size(); i++) {
            //转换成可用的BlogInfoVO对象
            BlogInfoVO blogInfoVO = JacksonUtils.convertValue(blogInfoVOList.get(i), BlogInfoVO.class);
            Long blogId = blogInfoVO.getId();
            //获取redis中的 views
            int views = (int) redisService.getValueByHashKey(redisKey,blogId);
            //将获取到的views设置给blogInfoVOList
            blogInfoVO.setViews(views);
            blogInfoVOList.set(i,blogInfoVO);

        }
    }

    private List<BlogInfoVO> processBlogInfo(List<BlogInfoVO> blogInfoVOList){
        for (BlogInfoVO blogInfoVO : blogInfoVOList) {
            blogInfoVO.setDescription(MarkdownUtils.markdownToHtmlExtensions(blogInfoVO.getDescription()));
            blogInfoVO.setTags(tagService.getTagListByBlogId(blogInfoVO.getId()));
        }
        return blogInfoVOList;
    }

    /**
     * 删除首页缓存、最新推荐缓存、归档页面缓存、博客浏览量缓存
     */
    private void deleteBlogRedisCache() {
        redisService.deleteCacheByKey(RedisKey.HOME_BLOG_INFO_LIST);
        redisService.deleteCacheByKey(RedisKey.NEW_BLOG_LIST);
        redisService.deleteCacheByKey(RedisKey.ARCHIVE_BLOG_MAP);
    }
}

