package cn.isbut.controller.admin;

import cn.isbut.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.isbut.annotation.OperationLogger;
import cn.isbut.common.Result;
import cn.isbut.entity.Tag;
import cn.isbut.service.BlogService;
import cn.isbut.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 后台标签管理模块
 * @Author: ryan
 */
@Api(tags = "后台标签管理模块")
@RestController
@RequestMapping("/admin")
public class TagAdminController {

    private final TagService tagService;
    private final BlogService blogService;

    public TagAdminController(TagService tagService, BlogService blogService) {
        this.tagService = tagService;
        this.blogService = blogService;
    }

    /**
    * 添加标签
    * @param tag 标签对象
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "添加标签")
    @OperationLogger("添加标签")
    @PostMapping("/tag")
    public Result saveTag(@RequestBody Tag tag){
        return saveAndUpdateCheckDate(tag, "save");
    }

    /**
    * 删除标签
    * @param id 标签id
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "删除标签")
    @ApiImplicitParam(name = "id", value = "标签Id", required = true, dataType = "Long", paramType = "query")
    @OperationLogger("删除标签")
    @DeleteMapping("/tag")
    public Result deleteTagByBlogId(@RequestParam Long id){
        //删除存在博客关联的分类后，该博客的查询会出异常
        int num = blogService.countBlogByTagId(id);
        if (num != 0) {
            return Result.error("已有博客与此标签关联，不可删除");
        }
        tagService.deleteTagById(id);
        return Result.success("删除评论成功");
    }

    /**
    * 修改标签
    * @param tag 标签对象
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "修改标签")
    @OperationLogger("修改标签")
    @PutMapping("/tag")
    public Result updateTag(@RequestBody Tag tag){
        return saveAndUpdateCheckDate(tag, "update");
    }

    /**
    * 标签列表
    * @param pageNum
    * @param pageSize
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    @GetMapping("/tags")
    public Result tagList(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize){
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<Tag> pageInfo = new PageInfo<>(tagService.getTagList());
        return Result.success(pageInfo);
    }

    private Result saveAndUpdateCheckDate(Tag tag, String type) {
        if (StringUtils.isEmpty(tag.getName())) {
            return Result.error("参数不能为空");
        }
        //查询标签是否已存在
        Tag tag1 = tagService.getTagByName(tag.getName());
        //如果 tag1.getId().equals(tag.getId()) == true 就是更新标签
        if (tag1 != null && !tag1.getId().equals(tag.getId())) {
            return Result.error("该标签已存在");
        }
        if ("save".equals(type)) {
            tagService.saveTag(tag);
            return Result.success("添加标签成功");
        } else {
            tagService.updateTag(tag);
            return Result.success("标签更新成功");
        }
    }
}
