package cn.isbut.service.Impl;

import cn.isbut.mapper.*;
import cn.isbut.vo.CategoryBlogCountVO;
import cn.isbut.vo.TagBlogCountVO;
import cn.isbut.entity.Category;
import cn.isbut.entity.CityVisitor;
import cn.isbut.entity.Tag;
import cn.isbut.entity.VisitRecord;
import com.mashiro.mapper.*;
import cn.isbut.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 仪表盘业务层实现
 * @Author: BeforeOne
 * @Date: Created in 2021/5/27 20:04
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    private final BlogMapper blogMapper;
    private final CommentMapper commentMapper;
    private final VisitLogMapper visitLogMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final VisitRecordMapper visitRecordMapper;
    private final CityVisitorMapper cityVisitorMapper;

    private static final Integer visitRecordLimit = 30;

    public DashboardServiceImpl(BlogMapper blogMapper, CommentMapper commentMapper, VisitLogMapper visitLogMapper, CategoryMapper categoryMapper, TagMapper tagMapper, VisitRecordMapper visitRecordMapper, CityVisitorMapper cityVisitorMapper) {
        this.blogMapper = blogMapper;
        this.commentMapper = commentMapper;
        this.visitLogMapper = visitLogMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.visitRecordMapper = visitRecordMapper;
        this.cityVisitorMapper = cityVisitorMapper;
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
    public int countVisitLogByToday() {
        return visitLogMapper.countVisitLogByToday();
    }

    @Override
    public Map<String, List> getCategoryBlogCountMap() {
        //获取每个分类Id下文章的数目，此时name为null
        List<CategoryBlogCountVO> categoryBlogCountVOList = blogMapper.getCategoryBlogCountList();
        //获取所有分类的名称和Id
        List<Category> categoryList = categoryMapper.getCategoryList();
        //所有分类名称
        List<String> legend = new ArrayList<>();
        for (Category category : categoryList) {
            legend.add(category.getName());
        }
        //分类名称对应博客数目
        List<CategoryBlogCountVO> series = new ArrayList<>();
        //如果每个分类下都有对应的博客
        if (categoryBlogCountVOList.size() == categoryList.size()){
            Map<Long, String> m = new HashMap<>();
            //将所有分类的Id和名称以 key-value 方式存储
            for (Category category : categoryList) {
                m.put(category.getId(), category.getName());
            }
            //因为每个分类下都有博客，所以可以通过 m 给 categoryBlogCountVOList 的 name 都赋上name，存入scallop中
            for (CategoryBlogCountVO categoryBlogCountVO : categoryBlogCountVOList) {
                categoryBlogCountVO.setName(m.get(categoryBlogCountVO.getId()));
                    series.add(categoryBlogCountVO);
            }
        }else {
            Map<Long, Integer> m = new HashMap<>();
            //将含有博客的分类的id和value 以 key-value 方式存储
            for (CategoryBlogCountVO categoryBlogCountVO : categoryBlogCountVOList) {
                m.put(categoryBlogCountVO.getId(), categoryBlogCountVO.getValue());
            }
            //遍历所有分类，新建一个CategoryBlogCountVO对象，给每个对象设置name和value
            //value的值从 m 中取出，如果 m 中没有对应id的分类，则count为空，即将这个分类的博客数目设置为0
            //最后每个对象都放入 series
            for (Category category : categoryList) {
                CategoryBlogCountVO categoryBlogCountVO = new CategoryBlogCountVO();
                categoryBlogCountVO.setName(category.getName());
                Integer count = m.get(category.getId());
                if (count == null){
                    categoryBlogCountVO.setValue(0);
                }else {
                    categoryBlogCountVO.setValue(count);
                }
                series.add(categoryBlogCountVO);
            }
        }
        Map<String, List> map = new HashMap<>();
        map.put("legend", legend);
        map.put("series", series);
        return map;
    }

    @Override
    public Map<String, List> getTagBlogCountMap() {
        //获取标签对应博客数量
        List<TagBlogCountVO> tagBlogCountVOList = tagMapper.getTagBlogCount();
        //获取所有标签Id和名称
        List<Tag> tagList = tagMapper.getTagList();
        //获取标签名字
        List<String> legend = new ArrayList<>();
        for (Tag tag : tagList) {
            legend.add(tag.getName());
        }
        //标签名称对应博客数量
        List<TagBlogCountVO> series = new ArrayList<>();
        //判断每个标签下面是否都有博客, 逻辑同分类
        if (tagBlogCountVOList.size() == tagList.size()){
            Map<Long, String> m = new HashMap<>();
            for (Tag tag : tagList) {
                m.put(tag.getId(), tag.getName());
            }
            for (TagBlogCountVO tagBlogCountVO : tagBlogCountVOList) {
                tagBlogCountVO.setName(m.get(tagBlogCountVO.getId()));
                series.add(tagBlogCountVO);
            }
        }else {
            Map<Long, Integer> m = new HashMap<>();
            for (TagBlogCountVO tagBlogCountVO : tagBlogCountVOList) {
                m.put(tagBlogCountVO.getId(),tagBlogCountVO.getValue());
            }
            for (Tag tag : tagList) {
                TagBlogCountVO tagBlogCountVO = new TagBlogCountVO();
                tagBlogCountVO.setName(tag.getName());
                Integer count = m.get(tag.getId());
                if (count == null){
                    tagBlogCountVO.setValue(0);
                }else {
                    tagBlogCountVO.setValue(count);
                }
                series.add(tagBlogCountVO);
            }
        }
        Map<String, List> map = new HashMap<>();
        map.put("legend", legend);
        map.put("series", series);
        return map;
    }

    @Override
    public Map<String, List> getVisitRecordMap() {
        Map<String, List> map = new HashMap<>();
        //获取所有 访客记录 (pv,uv,data)
        List<VisitRecord> visitRecordListByLimitList = visitRecordMapper.getVisitRecordListByLimit(visitRecordLimit);
        //分别创建三个list存放 data，pv，uv
        List<String> date = new ArrayList<>(visitRecordListByLimitList.size());
        List<Integer> pv = new ArrayList<>(visitRecordListByLimitList.size());
        List<Integer> uv = new ArrayList<>(visitRecordListByLimitList.size());
        //将 所有访客记录 分别存放到对应数据list中
        for (int i = visitRecordListByLimitList.size() - 1; i>=0; i--){
            VisitRecord visitRecord = visitRecordListByLimitList.get(i);
            date.add(visitRecord.getDate());
            pv.add(visitRecord.getPv());
            uv.add(visitRecord.getUv());
        }
        map.put("date",date);
        map.put("pv",pv);
        map.put("uv",uv);
        return map;
    }

    @Override
    public List<CityVisitor> getCityVisitorList() {
        return cityVisitorMapper.getCityVisitorLis();
    }

}
