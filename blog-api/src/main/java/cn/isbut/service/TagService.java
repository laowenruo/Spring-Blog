package cn.isbut.service;

import cn.isbut.entity.Tag;

import java.util.List;

/**
 * @Description: 标签业务层接口
 * @Author: ryan
 * @Date: Created in 2021/4/25 11:09
 */
public interface TagService {

    /**
     * @Description: 新增标签
     * @param tag
     * @return {@link Tag}
     * @throws
     * @author ryan
     * @data 2021/4/25 10:56
     *
     */
    int saveTag(Tag tag);

    /**
     * @Description: 根据id获取标签
     * @param id
     * @return {@link Tag}
     * @throws
     * @author ryan
     * @data 2021/4/25 10:57
     *
     */
    Tag getTagById(Long id);

    /**
     * @Description: 根据name获取标签
     * @param name
     * @return {@link Tag}
     * @throws
     * @author ryan
     * @data 2021/4/25 11:01
     *
     */
    Tag getTagByName(String name);

    /**
     * @Description: 获取全部标签
     * @param
     * @return {@link List < Tag>}
     * @throws
     * @author ryan
     * @data 2021/4/25 11:02
     *
     */
    List<Tag> getTagList();

    /**
     * @Description: 查询标签下所有的博客
     * @param
     * @return {@link List< Tag>}
     * @throws
     * @author ryan
     * @data 2021/4/25 11:04
     *
     */
    List<Tag> getTagBlogList();

    /**
     * @Description: 更新修改标签
     * @param tag
     * @return {@link int}
     * @throws
     * @author ryan
     * @data 2021/4/25 11:06
     *
     */
    int updateTag(Tag tag);

    /**
     * @Description: 删除标签
     * @param id
     * @return {@link int}
     * @throws
     * @author ryan
     * @data 2021/4/25 11:06
     *
     */
    int deleteTagById(Long id);

    /**
    * @Description: 从字符串中获取tag集合
    * @param text
    * @return {@link List< Tag>}
    * @throws
    * @author ryan
    * @data 2021/4/26 9:19
    *
    */
    List<Tag> getTagByString(String text);

    /**
     * 获取所有标签List不查询id
     * @param
     * @return {@link List< Tag>}
     * @author Mashiro
     * @data 2021/5/28 10:07
     *
     */
    List<Tag> getTagListNotId();

    /**
     * 按博客id查询List
     * @param blogId
     * @return {@link List< Tag>}
     * @author Mashiro
     * @data 2021/5/28 10:06
     *
     */
    List<Tag> getTagListByBlogId(Long blogId);

}
