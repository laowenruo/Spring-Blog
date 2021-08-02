package cn.isbut.controller;

import cn.isbut.common.Result;
import cn.isbut.entity.Tag;
import cn.isbut.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description:
 */
@Api(tags = "标签模块")
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @ApiOperation(value = "标签列表")
    @GetMapping("/tags")
    public Result tagList(){
        List<Tag> tagList = tagService.getTagList();
        int count = tagList.size();
        Map<String, Object> map = new HashMap<>();
        map.put("tags", tagList);
        map.put("count", count);
        return Result.success(map);
    }
}
