package cn.isbut.service.Impl;

import cn.isbut.exception.NotFoundException;
import cn.isbut.common.RedisKey;
import cn.isbut.entity.Category;
import cn.isbut.mapper.CategoryMapper;
import cn.isbut.service.CategoryService;
import cn.isbut.service.RedisService;
import cn.isbut.service.TagService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Description: 文章分类业务实现层
 * @Author: BeforeOne
 * @Date: Created in 2021/4/25 8:31
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final TagService tagService;
    private final RedisService redisService;

    public CategoryServiceImpl(CategoryMapper categoryMapper, TagService tagService, RedisService redisService) {
        this.categoryMapper = categoryMapper;
        this.tagService = tagService;
        this.redisService = redisService;
    }

    @Transactional
    @Override
    public int saveCategory(Category category) {
        if (categoryMapper.saveCategory(category) != 1){
            throw new PersistenceException("新增分类失败");
        }
        redisService.deleteCacheByKey(RedisKey.CATEGORY_NAME_LIST);
        return 1;
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryMapper.getCategoryById(id);
        if (category == null) {
            throw new NotFoundException("分类不存在");
        }
        return category;
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryMapper.getTypeByName(name);
    }

    @Override
    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }

    @Override
    public List<Category> getCategoryNameList() {
        //先从redis缓存中获取，获取不到再去数据库中查询
        String redisKey = RedisKey.CATEGORY_NAME_LIST;
        List<Category> categoryListFromRedis = redisService.getListByValue(redisKey);
        if (categoryListFromRedis != null){
            return categoryListFromRedis;
        }
        List<Category> categoryList = categoryMapper.getCategoryNameList();
        redisService.saveListToValue(redisKey, categoryList);
        return categoryList;
    }

    @Override
    public int updateCategory(Category category) {
        if (categoryMapper.updateCategory(category) != 1){
            throw new PersistenceException("更新失败");
        }
        redisService.deleteCacheByKey(RedisKey.CATEGORY_NAME_LIST);
        return 1;
    }

    @Override
    public int deleteCategoryById(Long id) {
        if (categoryMapper.deleteCategoryById(id) != 1){
            throw new PersistenceException("删除失败");
        }
        redisService.deleteCacheByKey(RedisKey.CATEGORY_NAME_LIST);
        return 1;
    }
}
