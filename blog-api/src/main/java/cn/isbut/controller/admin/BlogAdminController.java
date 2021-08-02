package cn.isbut.controller.admin;


import cn.isbut.dto.BlogDTO;
import cn.isbut.dto.BlogVisibilityDTO;
import cn.isbut.util.QiniuUtils;
import cn.isbut.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.isbut.annotation.OperationLogger;
import cn.isbut.common.Result;
import cn.isbut.entity.Blog;
import cn.isbut.entity.Category;
import cn.isbut.entity.Tag;
import cn.isbut.entity.User;
import cn.isbut.service.BlogService;
import cn.isbut.service.CategoryService;
import cn.isbut.service.CommentService;
import cn.isbut.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Description:
 * @Author: ryan
 */
@Api(tags = "后台博客管理模块")
@RestController
@RequestMapping("/admin")
public class BlogAdminController {

    private final BlogService blogService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final CommentService commentService;

    public BlogAdminController(BlogService blogService, CategoryService categoryService, TagService tagService, CommentService commentService) {
        this.blogService = blogService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    /**
    * 保存草稿或者新增博客
    * @param blogDTO 博客DTO
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "发布博客")
    @ApiImplicitParam(name = "blogDTO", value = "博客对象DTO", required = true, dataType = "BlogDTO", paramType = "body")
    @OperationLogger("发布博客")
    @PostMapping("/blog")
    public Result savaBlog(@RequestBody BlogDTO blogDTO){
        return saveAndUpdateCheckData(blogDTO, "save");
    }
    /**
    * 删除博客，同时删除该博客下的所有评论，维护blog_tag表
    * @param id 博客id
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "删除博客")
    @ApiImplicitParam(name = "id", value = "博客Id", required = true, dataType = "Long", paramType = "query")
    @OperationLogger("删除博客")
    @DeleteMapping("/blog")
    public Result deleteBlog(@RequestParam Long id){
        commentService.deleteCommentsByBlogId(id);
        blogService.deleteBlogAndTagByBlogId(id);
        blogService.deleteBlogById(id);
        return Result.success("删除成功");
    }

    /**
    * 更新博客
    * @param blogDTO 博客DTO
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "更新博客")
    @ApiImplicitParam(name = "blogDTO", value = "博客对象DTO", required = true, dataType = "BlogDTO", paramType = "body")
    @OperationLogger("更新博客")
    @PutMapping("/blog")
    public Result updateBlog(@RequestBody BlogDTO blogDTO){
        return saveAndUpdateCheckData(blogDTO, "update");
    }

    /**
    * 更新博客置顶状态
    * @param id 博客id
    * @param top 是否置顶
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "更新博客置顶状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "博客Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "top", value = "是否置顶", required = true, dataType = "Boolean", paramType = "query")
    })
    @OperationLogger("更新博客置顶状态")
    @PutMapping("/blog/top")
    public Result updateTop(@RequestParam Long id, @RequestParam Boolean top){
        if(blogService.updateBlogTopById(id, top) != 1){
            return Result.error();
        }
        return Result.success();
    }

    /**
    * 更新博客推荐状态
    * @param id 博客id
    * @param recommend 是否推荐
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "更新博客推荐状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "博客Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "recommend", value = "是否推荐", required = true, dataType = "Boolean", paramType = "query")
    })
    @OperationLogger("更新博客推荐状态")
    @PutMapping("/blog/recommend")
    public Result updateRecommend(@RequestParam Long id, @RequestParam Boolean recommend){
        if (blogService.updateBlogRecommendById(id, recommend) != 1){
            return Result.error();
        }
        return Result.success();
    }
    /**
    * 更新博客可见性状态
    * @param id
    * @param blogVisibilityDTO
    * @return {@link Result}
    * @author ryan
     * */
    @ApiOperation(value = "更新博客可见性状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "博客Id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "blogVisibilityDTO", value = "博客可见性DTO", required = true, dataType = "BlogVisibilityDTO", paramType = "body")
    })
    @OperationLogger("更新博客可见性状态")
    @PutMapping("/blog/{id}/visibility")
    public Result updateVisibility(@PathVariable Long id, @RequestBody BlogVisibilityDTO blogVisibilityDTO){
        blogService.updateBlogVisibilityById(id, blogVisibilityDTO);
        return Result.success();
    }

    /**
    * 获取分页列表和标签列表
    * @param
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "获取分页列表和标签列表")
    @GetMapping("/categoryAndTag")
    public Result categoryAndTag() {
        List<Category> categories = categoryService.getCategoryList();
        List<Tag> tags = tagService.getTagList();
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categories);
        map.put("tags", tags);
        return Result.success(map);
    }
    /**
    * 获取博客文章列表
    * @param title 按标题模糊查询
    * @param categoryId 按分类id查询
    * @param pageNum 页码
    * @param pageSize 每页个数
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "获取博客文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "文章标题", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "categoryId", value = "分类id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    @GetMapping("/blogs")
    public Result blogs(@RequestParam(defaultValue = "") String title,
                        @RequestParam(defaultValue = "") Integer categoryId,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize){
        String order = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, order);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogService.getBlogListByTitleAndCategoryId(title, categoryId));
        List<Category> categoryList = categoryService.getCategoryList();
        Map<String, Object> map = new HashMap<>();
        map.put("blogs", pageInfo);
        map.put("categories", categoryList);
        return Result.success(map);
    }

    /**
     * 根据博客id获取博客
     *
     * @param id 博客id
     * @return {@link Result}
     * @author ryan
     */
    @ApiOperation(value = "获取博客")
    @ApiImplicitParam(name = "id", value = "博客Id", required = true, dataType = "Long", paramType = "query")
    @GetMapping("/blog")
    public Result getBlog(@RequestParam Long id) {
        return Result.success(blogService.getBlogById(id));
    }

    @ApiImplicitParam(name = "file", value = "文章图片", required = true, dataType = "MultipartFile")
    @PostMapping("/upload")
    private Result upload(@RequestParam("file") MultipartFile multipartFile) {
        String pictureUrl = QiniuUtils.uploadImg(multipartFile);
        return Result.success(pictureUrl);
    }

    /**
     * 执行博客添加或更新操作：校验参数是否合法，添加分类、标签，维护博客标签关联表
     *
     * @param blogDTO 博客DTO
     * @param type    新增或者更新
     * @return {@link Result}
     * @author ryan
     */
    private Result saveAndUpdateCheckData(BlogDTO blogDTO, String type) {
        //必填字段不能为空
        if (StringUtils.isEmpty(blogDTO.getTitle(), blogDTO.getContent(), blogDTO.getDescription(), blogDTO.getFirstPicture())) {
            return Result.error("参数错误");
        }
        //处理分类
        Object cate = blogDTO.getCate();
        //不能为空
        if (cate == null) {
            return Result.error("分类不能为空");
        }
        //如果是选择已经存在的分类，前端传入的为 int 类型
        if (cate instanceof Integer){
            Category category = categoryService.getCategoryById(((Integer) cate).longValue());
            blogDTO.setCategory(category);
            //如果前端传入的是字符串类型，那么说明为新增操作
        }else if (cate instanceof String){
            //判断是否存在
            Category category = categoryService.getCategoryByName((String) cate);
            if (category != null){
                Result.error("重复添加");
            }
            Category c = new Category();
            c.setName((String) cate);
            categoryService.saveCategory(c);
            blogDTO.setCategory(c);
        }else {
            Result.error();
        }
        //处理标签
        List<Object> tagList = blogDTO.getTagList();
        List<Tag> tags =new ArrayList<>();
        if (tagList == null){
            Result.error("标签不能为空");
        }
        //List中每个对象都需要处理
        for (Object t : tagList) {
            if (t instanceof Integer){
                Tag tag = tagService.getTagById(((Integer) t).longValue());
                tags.add(tag);
            } else if (t instanceof String) {
                //查询标签是否已存在
                Tag tag1 = tagService.getTagByName((String) t);
                if (tag1 != null) {
                    return Result.error("重复添加");
                }
                Tag tag = new Tag();
                tag.setName((String) t);
                tagService.saveTag(tag);
                tags.add(tag);
            } else {
                return Result.error();
            }
        }

        if (blogDTO.getViews() == null || blogDTO.getViews() < 0) {
            blogDTO.setViews(0);
        }
        //初始化时间
        Date date = new Date();
        if ("save".equals(type)) {
            blogDTO.setCreateTime(date);
            blogDTO.setUpdateTime(date);
            User user = new User();
            //个人博客默认只有一个作者
            user.setId((long) 1);
            blogDTO.setUser(user);

            blogService.saveBlog(blogDTO);
            //关联博客和标签(维护 blog_tag 表)
            for (Tag t : tags) {
                blogService.saveBlogAndTag(blogDTO.getId(), t.getId());
            }
            return Result.success("新增成功");
        } else {
            blogDTO.setUpdateTime(date);
            blogService.updateBlog(blogDTO);
            //关联博客和标签(维护 blog_tag 表)
            blogService.deleteBlogAndTagByBlogId(blogDTO.getId());
            for (Tag t : tags) {
                blogService.saveBlogAndTag(blogDTO.getId(), t.getId());
            }
            return Result.success("更新成功");
        }
    }
}
