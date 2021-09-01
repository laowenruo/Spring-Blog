package cn.isbut.service;

import cn.isbut.entity.Category;

import java.util.List;

/**
 * @author Ryan
 */
public interface CategoryService {
	/**
	 * 得到分类列表
	 * @return 分类列表
	 */
	List<Category> getCategoryList();

	/**
	 * 分类列表+名字
	 * @return 结果
	 */
	List<Category> getCategoryNameList();

	/**
	 * 保存分类
	 * @param category 分类
	 */
	void saveCategory(Category category);

	/**
	 * 得到分类by id
	 * @param id 分类id
	 * @return 分类
	 */
	Category getCategoryById(Integer id);

	/**
	 * 得到分类 by name
	 * @param name 分类名
	 * @return 分类
	 */
	Category getCategoryByName(String name);

	/**
	 * 删除分类 by id
	 * @param id 分类id
	 */
	void deleteCategoryById(Integer id);

	/**
	 * 更新分类
	 * @param category 分类
	 */
	void updateCategory(Category category);
}
