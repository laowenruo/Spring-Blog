package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 页面评论
 * @Author: Mashiro
 * @Date: Created in 2021/5/27 21:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCommentVO {
    private Long id;
    private String nickname;//昵称
    private String content;//评论内容
    private String avatar;//头像(图片路径)
    private Date createTime;//评论时间
    private Boolean adminComment;//博主回复
    private String parentCommentId;//父评论id
    private String parentCommentNickname;//父评论昵称

    private List<PageCommentVO> replyComments = new ArrayList<>();//回复该评论的评论
}
