package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.Tag;
import cn.isbut.model.vo.TagBlogCount;

import java.util.List;

/**
 * @author Ryan
 * @Description: 博客标签持久层接口
 *
 */
@Mapper
@Repository
public interface TagMapper {
	/**
	 * 获取所有标签List
	 * @return 标签列表
	 */
	List<Tag> getTagList();

	/**
	 * 获取所有标签List不查询id
	 * @return 标签列表
	 */
	List<Tag> getTagListNotId();

	/**
	 * 按博客id查询List
	 * @param blogId 标签id
	 * @return 标签列表
	 */
	List<Tag> getTagListByBlogId(Integer blogId);

	/**
	 * 添加标签
	 * @param tag 标签
	 * @return 成功与否状态
	 */
	int saveTag(Tag tag);

	/**
	 * 按id查询标签
	 * @param id 标签id
	 * @return 成功与否状态
	 */
	Tag getTagById(Integer id);

	/**
	 * 按名称查询标签
	 * @param name 名称
	 * @return 标签
	 */
	Tag getTagByName(String name);

	/**
	 * 根据id删除标签
	 * @param id 标签id
	 * @return 成功与否状态
	 */
	int deleteTagById(Integer id);

	/**
	 * 更新标签
	 * @param tag 标签
	 * @return 成功与否状态
	 */
	int updateTag(Tag tag);

	/**
	 * 得到该标签下博文数量
	 * @return 标签的博文数量
	 */
	List<TagBlogCount> getTagBlogCount();
}
