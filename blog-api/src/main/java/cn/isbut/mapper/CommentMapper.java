package cn.isbut.mapper;

import cn.isbut.dto.CommentDTO;
import cn.isbut.vo.PageCommentVO;
import cn.isbut.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 评论持久层接口
 * @Author: ryan
 * @Date: Created in 2021/4/28 14:25
 */

@Mapper
@Repository
public interface CommentMapper {

    /**
     * 保存评论
     * @param commentDTO 评论DTO对象
     * @return {@link int}
     * @throws
     * @author ryan*
     */
    int saveComment(CommentDTO commentDTO);

    /**
     * 通过评论id删除评论
     * @param commentId  评论id
     * @return {@link int}
     * @throws
     * @author ryan
     *
     */
    int deleteCommentById(Long commentId);

    /**
    * 根据博客Id删除评论
    * @param blogId  博客Id
    * @return {@link int}
    * @author ryan
    *
    */
    int deleteCommentsByBlogId(Long blogId);

    /**
    * 更新评论
    * @param comment 评论对象
    * @return {@link int}
    * @author ryan
    *
    */
    int updateComment(Comment comment);

    /**
    * 更新评论公开状态
    * @param commentId 评论Id
    * @return {@link int}
    * @author ryan
    *
    */
    int updateCommentPublishedById(Long commentId, Boolean published);

    /**
    * 更新评论接收邮件提醒状态
    * @param commentId 评论Id
    * @param notice 是否开启邮件通知
    * @return {@link int}
    * @author ryan
    *
    */
    int updateCommentNoticeById(Long commentId, Boolean notice);

    /**
    * 按id查询评论
    * @param id 评论Id
    * @return {@link Comment}
    * @author ryan
    *
    */
    Comment getCommentById(Long id);

    /**
    * 按页面和父评论id查询评论List
    * @param page 所属页面
    * @param blogId 博客Id
    * @param parentCommentId 父评论Id
    * @return {@link List< Comment>}
    * @author ryan
    *
    */
    List<Comment> getListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId);

    /**
    * 按父评论id查询评论List
    * @param parentCommentId 父评论Id
    * @return {@link List< Comment>}
    * @author ryan
    *
    */
    List<Comment> getListByParentCommentId(Long parentCommentId);
    List<PageCommentVO> getReplyListByParentCommentId(Long parentCommentId);

    /**
    * 查询页面展示的评论List
    * @param page 所属页面
    * @param blogId 评论Id
    * @param parentCommentId 父评论Id
    * @return {@link List< PageCommentVO>}
    * @author ryan
    *
    */
    List<PageCommentVO> getPageCommentListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId);

    /**
     * 获取总评论数目
     * @param
     * @return {@link Integer}
     * @throws
     * @author ryan
     *
     */
    Integer countComment();

    /**
    * 按页面查询评论数量
    * @param page 所属页面
    * @param blogId 博客Id
    * @return {@link Integer}
    * @author ryan
    *
    */
    Integer countByPageAndIsPublished(Integer page, Long blogId);
}
