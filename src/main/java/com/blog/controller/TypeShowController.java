package com.blog.controller;

import com.blog.pojo.Blog;
import com.blog.pojo.Type;
import com.blog.service.BlogService;
import com.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;
    @GetMapping(value = {"/types/{id}","/types"})
    public String types(@PathVariable(required = false) Long id, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                        Model model){
        PageHelper.startPage(pagenum, 1000);  //开启分页
        List<Type> types = typeService.getBlogType();
        //-1从导航点过来的
        if (id == null){
            id = types.get(0).getId();
        }
        List<Blog> blogs = blogService.getByTypeId(id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("types", types);
        model.addAttribute("pageInfo", pageInfo);
        System.out.println(pageInfo.toString());
        model.addAttribute("activeTypeId", id);

        return "types";
    }
}
