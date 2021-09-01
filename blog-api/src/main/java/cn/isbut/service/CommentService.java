package cn.isbut.service;

import cn.isbut.model.dto.Comment;
import cn.isbut.model.vo.PageComment;

import java.util.List;

/**
 * @author Ryan
 */
public interface CommentService {
	/**
	 * 通过id和父评论id查询某页评论
	 * @param page 页面类型
	 * @param blogId 博文id
	 * @param parentCommentId 父评论id
	 * @return 评论
	 */
	List<cn.isbut.entity.Comment> getListByPageAndParentCommentId(Integer page, Integer blogId, Integer parentCommentId);

	/**
	 * 得到页面评论
	 * @param page 页面类型
 	 * @param blogId 博文id
	 * @param parentCommentId 父评论id
	 * @return 分页评论
	 */
	List<PageComment> getPageCommentList(Integer page, Integer blogId, Integer parentCommentId);

	/**
	 * 通过id查询评论
	 * @param id 评论id
	 * @return 评论
	 */
	cn.isbut.entity.Comment getCommentById(Integer id);

	/**
	 * 更新评论的可见性by id
	 * @param commentId 评论id
	 * @param published 可见性
	 */
	void updateCommentPublishedById(Integer commentId, Boolean published);

	/**
	 * 更新评论的邮件通知
	 * @param commentId 评论id
	 * @param notice 通知状态？
	 */
	void updateCommentNoticeById(Integer commentId, Boolean notice);

	/**
	 * 通过id删除评论
	 * @param commentId 评论id
	 */
	void deleteCommentById(Integer commentId);

	/**
	 * 通过博文id删除评论
	 * @param blogId 博文id
	 */
	void deleteCommentsByBlogId(Integer blogId);

	/**
	 * 更新评论
	 * @param comment 评论
	 */
	void updateComment(cn.isbut.entity.Comment comment);

	/**
	 * 统计博文某页面类型的评论
	 * @param page 页面类型
	 * @param blogId 博文id
	 * @return 分页评论
	 */
	int countByPageAndIsPublished(Integer page, Integer blogId);

	/**
	 * 保存评论
	 * @param comment 评论
	 */
	void saveComment(Comment comment);
}
