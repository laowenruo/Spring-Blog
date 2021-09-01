package cn.isbut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.isbut.entity.Category;
import cn.isbut.entity.CityVisitor;
import cn.isbut.entity.Tag;
import cn.isbut.entity.VisitRecord;
import cn.isbut.mapper.BlogMapper;
import cn.isbut.mapper.CategoryMapper;
import cn.isbut.mapper.CityVisitorMapper;
import cn.isbut.mapper.CommentMapper;
import cn.isbut.mapper.TagMapper;
import cn.isbut.mapper.VisitLogMapper;
import cn.isbut.mapper.VisitRecordMapper;
import cn.isbut.model.vo.CategoryBlogCount;
import cn.isbut.model.vo.TagBlogCount;
import cn.isbut.service.DashboardService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 仪表盘业务层实现
 *
 */
@Service
public class DashboardServiceImpl implements DashboardService {

	BlogMapper blogMapper;

	CommentMapper commentMapper;

	CategoryMapper categoryMapper;

	TagMapper tagMapper;

	VisitLogMapper visitLogMapper;

	VisitRecordMapper visitRecordMapper;

	CityVisitorMapper cityVisitorMapper;

	/**
	 * 查询最近30天的记录
	 */
	private static final int visitRecordLimitNum = 30;

	@Override
	public int countVisitLogByToday() {
		return visitLogMapper.countVisitLogByToday();
	}

	@Override
	public int getBlogCount() {
		return blogMapper.countBlog();
	}

	@Override
	public int getCommentCount() {
		return commentMapper.countComment();
	}

	@Override
	public Map<String, List> getCategoryBlogCountMap() {
		//查询分类id对应的博客数量
		List<CategoryBlogCount> categoryBlogCountList = blogMapper.getCategoryBlogCountList();
		//查询所有分类的id和名称
		List<Category> categoryList = categoryMapper.getCategoryList();
		//所有分类名称的List
		List<String> legend = new ArrayList<>();
		for (Category category : categoryList) {
			legend.add(category.getName());
		}
		//分类对应的博客数量List
		List<CategoryBlogCount> series = new ArrayList<>();
		if (categoryBlogCountList.size() == categoryList.size()) {
			Map<Integer, String> m = new HashMap<>(16);
			for (Category c : categoryList) {
				m.put(c.getId(), c.getName());
			}
			for (CategoryBlogCount c : categoryBlogCountList) {
				c.setName(m.get(c.getId()));
				series.add(c);
			}
		} else {
			Map<Integer, Integer> m = new HashMap<>(16);
			for (CategoryBlogCount c : categoryBlogCountList) {
				m.put(c.getId(), c.getValue());
			}
			for (Category c : categoryList) {
				CategoryBlogCount categoryBlogCount = new CategoryBlogCount();
				categoryBlogCount.setName(c.getName());
				Integer count = m.get(c.getId());
				if (count == null) {
					categoryBlogCount.setValue(0);
				} else {
					categoryBlogCount.setValue(count);
				}
				series.add(categoryBlogCount);
			}
		}
		Map<String, List> map = new HashMap<>(2);
		map.put("legend", legend);
		map.put("series", series);
		return map;
	}

	@Override
	public Map<String, List> getTagBlogCountMap() {
		//查询标签id对应的博客数量
		List<TagBlogCount> tagBlogCountList = tagMapper.getTagBlogCount();
		//查询所有标签的id和名称
		List<Tag> tagList = tagMapper.getTagList();
		//所有标签名称的List
		List<String> legend = new ArrayList<>();
		for (Tag tag : tagList) {
			legend.add(tag.getName());
		}
		//标签对应的博客数量List
		List<TagBlogCount> series = new ArrayList<>();
		if (tagBlogCountList.size() == tagList.size()) {
			Map<Integer, String> m = new HashMap<>(16);
			for (Tag t : tagList) {
				m.put(t.getId(), t.getName());
			}
			for (TagBlogCount t : tagBlogCountList) {
				t.setName(m.get(t.getId()));
				series.add(t);
			}
		} else {
			Map<Integer, Integer> m = new HashMap<>(16);
			for (TagBlogCount t : tagBlogCountList) {
				m.put(t.getId(), t.getValue());
			}
			for (Tag t : tagList) {
				TagBlogCount tagBlogCount = new TagBlogCount();
				tagBlogCount.setName(t.getName());
				Integer count = m.get(t.getId());
				if (count == null) {
					tagBlogCount.setValue(0);
				} else {
					tagBlogCount.setValue(count);
				}
				series.add(tagBlogCount);
			}
		}
		Map<String, List> map = new HashMap<>(2);
		map.put("legend", legend);
		map.put("series", series);
		return map;
	}

	@Override
	public Map<String, List> getVisitRecordMap() {
		List<VisitRecord> visitRecordList = visitRecordMapper.getVisitRecordListByLimit(visitRecordLimitNum);
		List<String> date = new ArrayList<>(visitRecordList.size());
		List<Integer> pv = new ArrayList<>(visitRecordList.size());
		List<Integer> uv = new ArrayList<>(visitRecordList.size());
		for (int i = visitRecordList.size() - 1; i >= 0; i--) {
			VisitRecord visitRecord = visitRecordList.get(i);
			date.add(visitRecord.getDate());
			pv.add(visitRecord.getPv());
			uv.add(visitRecord.getUv());
		}
		Map<String, List> map = new HashMap<>(4);
		map.put("date", date);
		map.put("pv", pv);
		map.put("uv", uv);
		return map;
	}

	@Override
	public List<CityVisitor> getCityVisitorList() {
		return cityVisitorMapper.getCityVisitorList();
	}

	@Autowired
	public void setBlogMapper(BlogMapper blogMapper) {
		this.blogMapper = blogMapper;
	}

	@Autowired
	public void setCommentMapper(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Autowired
	public void setCategoryMapper(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

	@Autowired
	public void setTagMapper(TagMapper tagMapper) {
		this.tagMapper = tagMapper;
	}

	@Autowired
	public void setVisitLogMapper(VisitLogMapper visitLogMapper) {
		this.visitLogMapper = visitLogMapper;
	}

	@Autowired
	public void setVisitRecordMapper(VisitRecordMapper visitRecordMapper) {
		this.visitRecordMapper = visitRecordMapper;
	}

	@Autowired
	public void setCityVisitorMapper(CityVisitorMapper cityVisitorMapper) {
		this.cityVisitorMapper = cityVisitorMapper;
	}
}
