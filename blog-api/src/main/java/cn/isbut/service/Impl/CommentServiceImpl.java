package cn.isbut.service.Impl;

import cn.isbut.dto.CommentDTO;
import cn.isbut.exception.PersistenceException;
import cn.isbut.vo.PageCommentVO;
import cn.isbut.entity.Comment;
import cn.isbut.mapper.CommentMapper;
import cn.isbut.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 博客评论业务层实现
 * @Author: Mashiro
 * @Date: Created in 2021/5/28 22:51
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Transactional
    @Override
    public void saveComment(CommentDTO commentDTO) {
        if (commentMapper.saveComment(commentDTO) != 1){
            throw new PersistenceException("评论失败");
        }
    }

    @Transactional
    @Override
    public void deleteCommentById(Long commentId) {
        //获取所有子评论，一并删除
        List<Comment> comments = getAllReplyComments(commentId);
        for (Comment comment : comments) {
            delete(comment);
        }
        if (commentMapper.deleteCommentById(commentId) != 1){
            throw new PersistenceException("删除评论失败");
        }
    }

    @Transactional
    @Override
    public void deleteCommentsByBlogId(Long blogId) {
        commentMapper.deleteCommentsByBlogId(blogId);

    }

    @Override
    public void updateComment(Comment comment) {
        if (commentMapper.updateComment(comment) != 1) {
            throw new PersistenceException("评论修改失败");
        }
    }

    @Override
    public void updateCommentPublishedById(Long commentId, Boolean published) {
        if (commentMapper.updateCommentPublishedById(commentId, published) != 1) {
            throw new PersistenceException("操作失败");
        }
    }

    @Override
    public void updateCommentNoticeById(Long commentId, Boolean notice) {
        if (commentMapper.updateCommentNoticeById(commentId, notice) != 1) {
            throw new PersistenceException("操作失败");
        }
    }

    @Override
    public Comment getCommentById(Long id) {
        Comment comment = commentMapper.getCommentById(id);
        if (comment == null) {
            throw new PersistenceException("评论不存在");
        }
        return comment;
    }

    @Override
    public List<Comment> getListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId) {
        List<Comment> comments = commentMapper.getListByPageAndParentCommentId(page, blogId, parentCommentId);
        for (Comment c : comments) {
            //递归查询子评论及其子评论
            List<Comment> replyComments = getListByPageAndParentCommentId(page, blogId, c.getId());
            c.setReplyComments(replyComments);
        }
        return comments;
    }

    @Override
    public List<PageCommentVO> getPageCommentList(Integer page, Long blogId, Long parentCommentId) {
        List<PageCommentVO> comments = getPageCommentListByPageAndParentCommentId(page, blogId, parentCommentId);
        for (PageCommentVO c : comments) {
            List<PageCommentVO> tmpComments = new ArrayList<>();
            getAllReplyComments(tmpComments, c.getReplyComments());
            c.setReplyComments(tmpComments);
        }
        return comments;
    }

    @Override
    public List<PageCommentVO> getReplyCommentList(Long parentCommentId) {
        List<PageCommentVO> comments = commentMapper.getReplyListByParentCommentId(parentCommentId);
        for (PageCommentVO comment : comments) {
            List<PageCommentVO> replyComments = getReplyCommentList(comment.getId());
            comment.setReplyComments(replyComments);
        }
        return comments;
    }

    @Override
    public int countByPageAndIsPublished(Integer page, Long blogId) {
        return commentMapper.countByPageAndIsPublished(page, blogId);
    }

    /**
    * 根据父评论 id 递归查询所有的子评论
    * @param parentCommentId 需要查询子评论的父评论id
    * @return {@link List< Comment>}
    * @author Mashiro
    * @data 2021/5/28 23:35
    *
    */
    private List<Comment> getAllReplyComments(Long parentCommentId){
        List<Comment> comments = commentMapper.getListByParentCommentId(parentCommentId);
        for (Comment comment : comments) {
            List<Comment> replyComments = getAllReplyComments(comment.getId());
            comment.setReplyComments(replyComments);
        }
        return comments;
    }

    /**
    * 递归删除评论
    * @param comment
    * @return
    * @author Mashiro
    * @data 2021/5/28 23:51
    *
    */
    private void delete(Comment comment){
        for (Comment replyComment : comment.getReplyComments()) {
            delete(replyComment);
        }
        if (commentMapper.deleteCommentById(comment.getId()) != 1){
            throw new PersistenceException("评论删除失败");
        }
    }

    /**
     * 将所有子评论递归取出到一个List(tmpComments)中
     *
     * @param comments
     * @return
     */
    private void getAllReplyComments(List<PageCommentVO> tmpComments, List<PageCommentVO> comments) {
        for (PageCommentVO c : comments) {
            tmpComments.add(c);
            getAllReplyComments(tmpComments, c.getReplyComments());
        }
    }

    private List<PageCommentVO> getPageCommentListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId) {
        //根据父评论id查询该评论所属的所有评论
        List<PageCommentVO> comments = commentMapper.getPageCommentListByPageAndParentCommentId(page, blogId, parentCommentId);
        //递归查询comments下所有评论
        for (PageCommentVO c : comments) {
            List<PageCommentVO> replyComments = getPageCommentListByPageAndParentCommentId(page, blogId, c.getId());
            c.setReplyComments(replyComments);
        }
        return comments;
    }
}
