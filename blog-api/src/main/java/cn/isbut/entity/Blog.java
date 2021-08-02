package cn.isbut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ryan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    private Long id;
    private String title;//标题
    private String content;//内容
    private String firstPicture;//首图
    private String description;//文章描述
    private String flag;//标记
    private Integer views;//浏览次数
    private Boolean appreciation;//赞赏开关
    private Boolean commentEnabled;//评论开关
    private Boolean published;//发布(公开或者私密)
    private Boolean recommend;//推荐开关
    private Boolean top;//是否置顶
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Category category;//文章分类
    private User user;//文章作
    private List<Tag> tags = new ArrayList<>();//文章标签


}
