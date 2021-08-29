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
	 * @param page
	 * @param blogId
	 * @param parentCommentId
	 * @return
	 */
	List<cn.isbut.entity.Comment> getListByPageAndParentCommentId(Integer page, Integer blogId, Integer parentCommentId);

	/**
	 * 查询页面展示的评论List
	 * @param parentCommentId
	 * @return
	 */
	List<PageComment> getPageCommentListByPageAndParentCommentId(Integer page, Integer blogId, Integer parentCommentId);

	/**
	 * 按id查询评论
	 * @param id
	 * @return
	 */
	cn.isbut.entity.Comment getCommentById(Integer id);

	/**
	 * 更新评论公开状态
	 * @param commentId
	 * @param published
	 * @return
	 */
	int updateCommentPublishedById(Integer commentId, Boolean published);

	/**
	 * 更新评论接收邮件提醒状态
	 * @param commentId
	 * @param notice
	 * @return
	 */
	int updateCommentNoticeById(Integer commentId, Boolean notice);

	/**
	 * 按id删除评论
	 * @param commentId
	 * @return
	 */
	int deleteCommentById(Integer commentId);

	/**
	 * 按博客id删除博客下所有评论
	 * @param blogId
	 * @return
	 */
	int deleteCommentsByBlogId(Integer blogId);

	/**
	 * 更新评论
	 * @param comment
	 * @return
	 */
	int updateComment(cn.isbut.entity.Comment comment);

	/**
	 * 按页面查询评论数量
	 * @param page
	 * @param blogId
	 * @return
	 */
	int countByPageAndIsPublished(Integer page, Integer blogId);

	/**
	 * 查询所有评论数量
	 * @return
	 */
	int countComment();

	/**
	 * 保存评论
	 * @param comment
	 * @return
	 */
	int saveComment(Comment comment);
}
