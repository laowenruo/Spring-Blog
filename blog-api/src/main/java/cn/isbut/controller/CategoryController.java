package cn.isbut.controller;

import cn.isbut.common.Result;
import cn.isbut.entity.Category;
import cn.isbut.service.CategoryService;
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
 * @Description: 类型管理控制器
 */
@Api(tags = "分类模块")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "查看所有分类")
    @GetMapping("/categories")
    public Result listCategories() {
        List<Category> categoryList = categoryService.getCategoryList();
        int count = categoryList.size();
        Map<String, Object> map = new HashMap<>();
        map.put("categoryList", categoryList);
        map.put("count", count);
        return Result.success(map);
    }
}
