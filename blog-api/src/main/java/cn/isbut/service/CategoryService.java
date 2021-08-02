package cn.isbut.service;

import cn.isbut.entity.Category;

import java.util.List;
/**
 * @Description: 文章分类业务层接口
 * @Author: ryan
 * @Date: Created in 2021/4/25 8:25
 */
public interface CategoryService {

    /**
     * @Description: 新增分类
     * @param category  分类对象
     * @return {@link int}
     * @throws
     * @author ryan
     * @data 2021/4/25 8:19
     *
     */
    int saveCategory(Category category);

    /**
     * @Description: 通过id获取分类
     * @param id
     * @return {@link Category}
     * @throws
     * @author ryan
     * @data 2021/4/25 8:20
     *
     */
    Category getCategoryById(Long id);

    /**
     * @Description: 通过Name获取分类
     * @param name
     * @return {@link Category}
     * @throws
     * @author ryan
     * @data 2021/4/25 8:20
     *
     */
    Category getCategoryByName(String name);

    /**
     * @Description: 获取所有分类，生成List
     * @param
     * @return {@link List<Category>}
     * @throws
     * @author ryan
     * @data 2021/4/25 8:20
     *
     */
    List<Category> getCategoryList();

    /**
     * 获取所有分类List不查询id
     * @param
     * @return {@link List< Category>}
     * @author ryan
     * @data 2021/5/28 10:19
     *
     */
    List<Category> getCategoryNameList();

    /**
     * @Description: 根据id修改分类信息
     * @param category
     * @return {@link int}
     * @throws
     * @author ryan
     * @data 2021/4/25 8:22
     *
     */
    int updateCategory(Category category);

    /**
     * @Description: 根据id删除分类
     * @param id
     * @return {@link int}
     * @throws
     * @author ryan
     * @data 2021/4/25 8:23
     *
     */
    int deleteCategoryById(Long id);
}
