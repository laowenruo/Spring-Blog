package cn.isbut.mapper;

import cn.isbut.vo.TagBlogCountVO;
import cn.isbut.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 标签持久层接口
 * @Author: ryan
 */

@Mapper
@Repository
public interface TagMapper {

    /**
    * @Description: 新增标签
    * @param tag
    * @return {@link Tag}
    * @throws
    * @author ryan
    *
    */
    int saveTag(Tag tag);

    /**
    * @Description: 根据id获取标签
    * @param id
    * @return {@link Tag}
    * @throws
    * @author ryan
    *
    */
    Tag getTagById(@Param("id") Long id);

    /**
    * @Description: 根据name获取标签
    * @param name
    * @return {@link Tag}
    * @throws
    * @author ryan
    *
    */
    Tag getTagByName(@Param("name") String name);

    /**
    * @Description: 获取全部标签list
    * @param
    * @return {@link List< Tag>}
    * @throws
    * @author ryan
    *
    */
    List<Tag> getTagList();

    /**
    * @Description: 查询标签下所有的博客
    * @param
    * @return {@link List< Tag>}
    * @throws
    * @author ryan
    *
    */
    List<Tag> getTagBlogList();

    /**
    * @Description: 更新修改标签
    * @param tag
    * @return {@link int}
    * @throws
    * @author ryan
    *
    */
    int updateTag(Tag tag);

    /**
    * @Description: 删除标签
    * @param id
    * @return {@link int}
    * @throws
    * @author ryan
    *
    */
    int deleteTagById(@Param("id") Long id);

    /**
    * 查询每个标签的博客数量
    *
    * @param
    * @return {@link List<  TagBlogCountVO >}
    *
    * @author ryan
    **/
    List<TagBlogCountVO> getTagBlogCount();

    /**
    * 获取所有标签List不查询id
    * @param
    * @return {@link List< Tag>}
    * @author ryan
    *
    */
    List<Tag> getTagListNotId();

    /**
    * 按博客id查询List
    * @param blogId
    * @return {@link List< Tag>}
    * @author ryan
    *
    */
    List<Tag> getTagListByBlogId(Long blogId);
}
