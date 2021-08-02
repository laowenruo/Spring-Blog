package cn.isbut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ryan
 * @Description: 邮件实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String nickname; //评论昵称
    private String content; //评论内容

    private String blogName;// 评论对应的博客名称
    private Long blogId;// 评论对应的博客Id

    private Boolean replyFlag;// 回复标记
    private String replyNickname;// 被回复的评论昵称
    private String replyContent;// 被回复的评论内容
    private String replyEmail;// 被回复的评论邮箱
}
