package cn.isbut.service.impl;

import cn.isbut.exception.NotFoundException;
import cn.isbut.exception.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.isbut.config.RedisKeyConfig;
import cn.isbut.entity.Category;
import cn.isbut.mapper.CategoryMapper;
import cn.isbut.service.CategoryService;
import cn.isbut.service.RedisService;
import cn.isbut.service.TagService;

import java.util.List;

/**
 * @author Ryan
 * @Description: 博客分类业务层实现
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	CategoryMapper categoryMapper;

	TagService tagService;

	RedisService redisService;

	@Override
	public List<Category> getCategoryList() {
		return categoryMapper.getCategoryList();
	}

	@Override
	public List<Category> getCategoryNameList() {
		String redisKey = RedisKeyConfig.CATEGORY_NAME_LIST;
		List<Category> categoryListFromRedis = redisService.getListByValue(redisKey);
		if (categoryListFromRedis != null) {
			return categoryListFromRedis;
		}
		List<Category> categoryList = categoryMapper.getCategoryNameList();
		redisService.saveListToValue(redisKey, categoryList);
		return categoryList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCategory(Category category) {
		if (categoryMapper.saveCategory(category) != 1) {
			throw new PersistenceException("分类添加失败");
		}
		redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_LIST);
	}

	@Override
	public Category getCategoryById(Integer id) {
		Category category = categoryMapper.getCategoryById(id);
		if (category == null) {
			throw new NotFoundException("分类不存在");
		}
		return category;
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryMapper.getCategoryByName(name);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCategoryById(Integer id) {
		if (categoryMapper.deleteCategoryById(id) != 1) {
			throw new PersistenceException("删除分类失败");
		}
		redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_LIST);
		redisService.deleteCacheByKey(RedisKeyConfig.HOME_BLOG_INFO_LIST);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCategory(Category category) {
		if (categoryMapper.updateCategory(category) != 1) {
			throw new PersistenceException("分类更新失败");
		}
		redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_LIST);
		//修改了分类名，可能有首页文章关联了分类，也要更新首页缓存
		redisService.deleteCacheByKey(RedisKeyConfig.HOME_BLOG_INFO_LIST);
	}

	@Autowired
	public void setCategoryMapper(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

	@Autowired
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	@Autowired
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
}
