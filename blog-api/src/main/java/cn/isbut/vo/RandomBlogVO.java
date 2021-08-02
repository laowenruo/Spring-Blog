package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description:
 * @Author: BeforeOne
 * @Date: Created in 2021/5/27 14:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomBlogVO {
    private Long id;
    private String title;//文章标题
    private String firstPicture;//文章首图，用于随机文章展示
    private Date createTime;//创建时间
    private Boolean privacy;//是否私密文章
}
