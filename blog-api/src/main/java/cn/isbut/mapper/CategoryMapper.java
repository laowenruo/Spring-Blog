package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.Category;

import java.util.List;

/**
 * @author Ryan
 * @Description: 博客分类持久层接口
 *
 */
@Mapper
@Repository
public interface CategoryMapper {
	/**
	 * 获取所有分类List
	 * @return
	 */
	List<Category> getCategoryList();

	/**
	 * 获取所有分类List不查询id
	 * @return
	 */
	List<Category> getCategoryNameList();

	/**
	 * 添加分类
	 * @param category
	 * @return
	 */
	int saveCategory(Category category);

	/**
	 * 按id查询分类
	 * @param id
	 * @return
	 */
	Category getCategoryById(Integer id);

	/**
	 * 按name查询分类
	 * @param name
	 * @return
	 */
	Category getCategoryByName(String name);

	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	int deleteCategoryById(Integer id);

	/**
	 * 更新分类
	 * @param category
	 * @return
	 */
	int updateCategory(Category category);
}
