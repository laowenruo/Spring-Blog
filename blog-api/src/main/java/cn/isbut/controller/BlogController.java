package cn.isbut.controller;


import cn.isbut.util.StringUtils;
import cn.isbut.vo.*;
import cn.isbut.annotation.VisitLogger;
import cn.isbut.common.Result;
import cn.isbut.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description:
 */
@Api(tags = "博客文章模块")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    /**
    * 根据分类名称查询博客列表
    * @param categoryName
    * @param pageNum
    * @return {@link Result}
    * @date 2021/5/31 18:06
    */
    @ApiOperation(value = "查看分类对应博客")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryName", value = "分类名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query")
    })
    @VisitLogger(behavior = "查看分类对应博客")
    @GetMapping("/categoriesBlog")
    public Result blogListByCategory(@RequestParam(defaultValue = "") String categoryName,
                                     @RequestParam(defaultValue = "1") Integer pageNum){
        PageResultVO<BlogInfoVO> blogList = blogService.getBlogInfoListByCategoryNameAndIsPublished(categoryName, pageNum);
        Map<String, Object> map = new HashMap<>();
        map.put("blogList", blogList);
        return Result.success(map);
    }

    /**
    * 根据标签名称获取博客列表
    * @param tagName
    * @param pageNum
    * @return {@link Result}
    */
    @ApiOperation(value = "查看标签对应博客")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagName", value = "标签名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query")
    })
    @VisitLogger(behavior = "查看标签对应博客")
    @GetMapping("/tagBlog")
    public Result blogListByTag(@RequestParam(defaultValue = "") String tagName,
                                @RequestParam(defaultValue = "1") Integer pageNum){
        PageResultVO<BlogInfoVO> blogList = blogService.getBlogInfoListByTagNameAndIsPublished(tagName, pageNum);
        Map<String, Object> map = new HashMap<>();
        map.put("blogList", blogList);
        return Result.success(map);
    }

    @ApiOperation(value = "查看博客")
    @ApiImplicitParam(name = "id", value = "博客Id", required = true, dataType = "Long", paramType = "query")
    @VisitLogger(behavior = "查看博客")
    @GetMapping("/blogDetail")
    public Result getBlogDetail(@RequestParam Long id){
        BlogDetailVO blog = blogService.getBlogByIdAndIsPublished(id);
        blogService.updateViewsToRedis(id);
        return Result.success(blog);
    }
    @ApiOperation(value = "最新博客列表")
    @GetMapping("/articlesnewest")
    public Result getBlogNewest(){
        List<NewBlogVO> blogList = blogService.getNewBlogListByIsPublished();
        Map<String, Object> map = new HashMap<>();
        map.put("blogList", blogList);
        return Result.success(map);
    }

    /**
    * 按关键字根据文章内容搜索公开的博客文章
    * @param keywords 搜索关键字
    * @return {@link Result}
    */
    @ApiOperation(value = "根据关键字搜索博客")
    @ApiImplicitParam(name = "keywords", value = "关键字", required = true, dataType = "String", paramType = "query")
    @VisitLogger(behavior = "搜索博客")
    @GetMapping("/search")
    public Result search(@RequestParam String keywords){
        //校验关键字字符串合法性
        if (StringUtils.isEmpty(keywords) || StringUtils.hasSpecialChar(keywords) || keywords.trim().length() > 20) {
            return Result.error("参数错误");
        }
        List<SearchBlogVO> searchBlogs = blogService.getSearchBlogListByQueryAndIsPublished(keywords.trim());
        Map<String, Object> map = new HashMap<>();
        map.put("searchBlogs", searchBlogs);
        return Result.success(map);
    }
}
