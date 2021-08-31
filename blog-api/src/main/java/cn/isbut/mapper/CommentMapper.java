package cn.isbut.mapper;

import cn.isbut.model.dto.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.model.vo.PageComment;

import java.util.List;

/**
 * @author Ryan
 * @Description: 博客评论持久层接口
 *
 */
@Mapper
@Repository
public interface CommentMapper {
	/**
	 * 按页面和父评论id查询评论List
	 * @param page 页数
	 * @param blogId 博文id
	 * @param parentCommentId 父评论id
	 * @return 评论
	 */
	List<cn.isbut.entity.Comment> getListByPageAndParentCommentId(Integer page, Integer blogId, Integer parentCommentId);

	/**
	 * 查询页面展示的评论List
	 * @param parentCommentId 父id
	 * @param page 页数
	 * @param blogId 博文id
	 * @return 评论
	 */
	List<PageComment> getPageCommentListByPageAndParentCommentId(Integer page, Integer blogId, Integer parentCommentId);

	/**
	 * 按id查询评论
	 * @param id 评论id
	 * @return 评论
	 */
	cn.isbut.entity.Comment getCommentById(Integer id);

	/**
	 * 更新评论公开状态
	 * @param commentId 评论id
	 * @param published 发布状态
	 * @return 成功与否状态
	 */
	int updateCommentPublishedById(Integer commentId, Boolean published);

	/**
	 * 更新评论接收邮件提醒状态
	 * @param commentId 评论id
	 * @param notice 通知状态
	 * @return 成功与否状态
	 */
	int updateCommentNoticeById(Integer commentId, Boolean notice);

	/**
	 * 按id删除评论
	 * @param commentId 评论id
	 * @return 成功与否状态
	 */
	int deleteCommentById(Integer commentId);

	/**
	 * 按博客id删除博客下所有评论
	 * @param blogId 评论id
	 * @return 成功与否状态
	 */
	int deleteCommentsByBlogId(Integer blogId);

	/**
	 * 更新评论
	 * @param comment 评论
	 * @return 成功与否状态
	 */
	int updateComment(cn.isbut.entity.Comment comment);

	/**
	 * 按页面查询评论数量
	 * @param page 页数
	 * @param blogId 博文id
	 * @return 评论数量
	 */
	int countByPageAndIsPublished(Integer page, Integer blogId);

	/**
	 * 查询所有评论数量
	 * @return 数量
	 */
	int countComment();

	/**
	 * 保存评论
	 * @param comment 博文
	 * @return 成功与否状态
	 */
	int saveComment(Comment comment);
}
