package cn.isbut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long id;
    private String name;
    private List<Blog> blogs = new ArrayList<>();//该分类下的博客文章

}
