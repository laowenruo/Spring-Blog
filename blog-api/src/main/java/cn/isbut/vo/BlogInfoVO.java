package cn.isbut.vo;

import cn.isbut.entity.Category;
import cn.isbut.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 用于展示的博客信息
 * @Author: BeforeOne
 * @Date: Created in 2021/5/25 20:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogInfoVO {
    private Long id;
    private String title;//文章标题
    private String firstPicture;//文章展示图片
    private String description;//描述
    private Date createTime;//创建时间
    private Integer views;//浏览次数
    private Boolean top;//是否置顶
    private Category category;//文章分类
    private List<Tag> tags = new ArrayList<>();//文章标签
}
