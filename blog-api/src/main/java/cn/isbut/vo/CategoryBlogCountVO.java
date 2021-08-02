package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: BeforeOne
 * @Date: Created in 2021/5/27 15:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBlogCountVO {
    private Long id;
    private String name;//分类名
    private Integer value;//分类下博客数量
}
