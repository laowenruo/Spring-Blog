package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 标签和博客数量
 * @Author: Mashiro
 * @Date: Created in 2021/5/27 21:26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TagBlogCountVO {
    private Long id;
    private String name;//标签名
    private Integer value;//标签下博客数量
}
