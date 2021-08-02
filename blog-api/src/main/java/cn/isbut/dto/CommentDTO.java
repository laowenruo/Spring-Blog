package cn.isbut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Ryan
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String nickname;//昵称
    private String email;//邮箱
    private String content;//评论内容
    private String avatar;//头像(图片路径)
    private Date createTime;//评论时间
    private String ip;//评论者ip地址
    private Boolean published;//公开或回收站
    private Boolean adminComment;//博主回复
    private Integer page;//0普通文章，1关于我页面
    private Boolean notice;//接收邮件提醒
    private Long parentCommentId;//父评论id
    private Long blogId;//所属的文章id
    private String qq;//如果评论昵称为QQ号，则将昵称和头像置为QQ昵称和QQ头像，并将此字段置为QQ号备份
}
