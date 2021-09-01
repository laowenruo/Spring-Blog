package cn.isbut.service.impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.model.dto.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.mapper.CommentMapper;
import cn.isbut.model.vo.PageComment;
import cn.isbut.service.CommentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan
 * @Description: 博客评论业务层实现
 */
@Service
public class CommentServiceImpl implements CommentService {

	CommentMapper commentMapper;

	@Override
	public List<cn.isbut.entity.Comment> getListByPageAndParentCommentId(Integer page, Integer blogId, Integer parentCommentId) {
		List<cn.isbut.entity.Comment> comments = commentMapper.getListByPageAndParentCommentId(page, blogId, parentCommentId);
		for (cn.isbut.entity.Comment c : comments) {
			//递归查询子评论及其子评论
			List<cn.isbut.entity.Comment> replyComments = getListByPageAndParentCommentId(page, blogId, c.getId());
			c.setReplyComments(replyComments);
		}
		return comments;
	}

	@Override
	public List<PageComment> getPageCommentList(Integer page, Integer blogId, Integer parentCommentId) {
		List<PageComment> comments = getPageCommentListByPageAndParentCommentId(page, blogId, parentCommentId);
		for (PageComment c : comments) {
			List<PageComment> tmpComments = new ArrayList<>();
			getReplyComments(tmpComments, c.getReplyComments());
			c.setReplyComments(tmpComments);
		}
		return comments;
	}

	@Override
	public cn.isbut.entity.Comment getCommentById(Integer id) {
		cn.isbut.entity.Comment comment = commentMapper.getCommentById(id);
		if (comment == null) {
			throw new PersistenceException("评论不存在");
		}
		return comment;
	}

	/**
	 * 将所有子评论递归取出到一个List中
	 *
	 * @param comments 评论
	 */
	private void getReplyComments(List<PageComment> tmpComments, List<PageComment> comments) {
		for (PageComment c : comments) {
			tmpComments.add(c);
			getReplyComments(tmpComments, c.getReplyComments());
		}
	}

	private List<PageComment> getPageCommentListByPageAndParentCommentId(Integer page, Integer blogId, Integer parentCommentId) {
		List<PageComment> comments = commentMapper.getPageCommentListByPageAndParentCommentId(page, blogId, parentCommentId);
		for (PageComment c : comments) {
			List<PageComment> replyComments = getPageCommentListByPageAndParentCommentId(page, blogId, c.getId());
			c.setReplyComments(replyComments);
		}
		return comments;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCommentPublishedById(Integer commentId, Boolean published) {
		if (commentMapper.updateCommentPublishedById(commentId, published) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCommentNoticeById(Integer commentId, Boolean notice) {
		if (commentMapper.updateCommentNoticeById(commentId, notice) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCommentById(Integer commentId) {
		List<cn.isbut.entity.Comment> comments = getAllReplyComments(commentId);
		for (cn.isbut.entity.Comment c : comments) {
			delete(c);
		}
		if (commentMapper.deleteCommentById(commentId) != 1) {
			throw new PersistenceException("评论删除失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCommentsByBlogId(Integer blogId) {
		commentMapper.deleteCommentsByBlogId(blogId);
	}

	@Transactional(rollbackFor = Exception.class)

	@Override
	public void updateComment(cn.isbut.entity.Comment comment) {
		if (commentMapper.updateComment(comment) != 1) {
			throw new PersistenceException("评论修改失败");
		}
	}

	@Override
	public int countByPageAndIsPublished(Integer page, Integer blogId) {
		return commentMapper.countByPageAndIsPublished(page, blogId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveComment(Comment comment) {
		if (commentMapper.saveComment(comment) != 1) {
			throw new PersistenceException("评论失败");
		}
	}

	/**
	 * 递归删除子评论
	 *
	 * @param comment 需要删除子评论的父评论
	 */
	private void delete(cn.isbut.entity.Comment comment) {
		for (cn.isbut.entity.Comment c : comment.getReplyComments()) {
			delete(c);
		}
		if (commentMapper.deleteCommentById(comment.getId()) != 1) {
			throw new PersistenceException("评论删除失败");
		}
	}

	/**
	 * 按id递归查询子评论
	 *
	 * @param parentCommentId 需要查询子评论的父评论id
	 * @return 评论
	 */
	private List<cn.isbut.entity.Comment> getAllReplyComments(Integer parentCommentId) {
		List<cn.isbut.entity.Comment> comments = commentMapper.getListByParentCommentId(parentCommentId);
		for (cn.isbut.entity.Comment c : comments) {
			List<cn.isbut.entity.Comment> replyComments = getAllReplyComments(c.getId());
			c.setReplyComments(replyComments);
		}
		return comments;
	}

	@Autowired
	public void setCommentMapper(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}
}
