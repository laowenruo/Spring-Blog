package cn.isbut.service;

import cn.isbut.dto.CommentDTO;
import cn.isbut.vo.PageCommentVO;
import cn.isbut.entity.Comment;

import java.util.List;

/**
 * @Description: 评论业务层接口
 * @Author: ryan
 */
public interface CommentService {

    /**
    * 新增评论
    * @param commentDTO
    * @return
    * @author ryan
    *
    */
    void saveComment(CommentDTO commentDTO);

    /**
    * 通过评论Id删除评论
    * @param commentId 评论Id
    * @return
    * @author ryan
    *
    */
    void deleteCommentById(Long commentId);

    /**
    * 通过博客Id删除评论
    * @param blogId
    * @return
    * @author ryan
    *
    */
    void deleteCommentsByBlogId(Long blogId);

    /**
    * 修改评论
    * @param comment 评论对象
    * @return
    * @author ryan
    *
    */
    void updateComment(Comment comment);

    /**
    * 更新评论公开状态
    * @param commentId 评论 id
    * @param published  是否公开
    * @return
    * @author ryan
    *
    */
    void updateCommentPublishedById(Long commentId, Boolean published);

    /**
    * 更新评论通知状态
    * @param commentId 评论 id
    * @param notice 是否开启邮件通知
    * @return
    * @author ryan
    *
    */
    void updateCommentNoticeById(Long commentId, Boolean notice);

    /**
    * 通过 id 获取评论
    * @param id 评论 id
    * @return {@link Comment}
    * @author ryan
    *
    */
    Comment getCommentById(Long id);

    List<Comment> getListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId);

    List<PageCommentVO> getPageCommentList(Integer page, Long blogId, Long parentCommentId);

    List<PageCommentVO> getReplyCommentList(Long parentCommentId);

    /**
    * 统计页面公开的评论
    * @param page 页面
    * @param blogId 博客 id
    * @return {@link int}
    * @author ryan
    *
    */
    int countByPageAndIsPublished(Integer page, Long blogId);

}
