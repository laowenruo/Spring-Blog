package cn.isbut.service;

import cn.isbut.entity.Tag;

import java.util.List;

/**
 * @author Ryan
 */
public interface TagService {
	/**
	 * 得到标签列表
	 * @return 标签列表
	 */
	List<Tag> getTagList();

	/**
	 * 得到标签列表
	 * @return 标签列表
	 */
	List<Tag> getTagListNotId();

	/**
	 * 通过博文id得到标签列表
	 * @param blogId 博文id
	 * @return 标签列表
	 */
	List<Tag> getTagListByBlogId(Integer blogId);

	/**
	 * 保存标签对象
	 * @param tag 标签
	 */
	void saveTag(Tag tag);

	/**
	 * 通过标签id得到标签
	 * @param id 标签id
	 * @return 标签对象
	 */
	Tag getTagById(Integer id);

	/**
	 * 通过名字得到标签对象
	 * @param name 标签名字
	 * @return 标签对象
	 */
	Tag getTagByName(String name);

	/**
	 * 删除标签
	 * @param id 标签id
	 */
	void deleteTagById(Integer id);

	/**
	 * 更新标签
	 * @param tag 标签对象
	 */
	void updateTag(Tag tag);
}
