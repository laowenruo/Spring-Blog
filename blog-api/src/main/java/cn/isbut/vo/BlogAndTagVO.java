package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 把博客和标签关系存到数据库中使用的类
 * @Author: BeforeOne
 * @Date: Created in 2021/4/26 8:16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogAndTagVO {
    private Long tagId;
    private Long blogId;
}
