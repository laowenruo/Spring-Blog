package cn.isbut.service.Impl;


import cn.isbut.exception.NotFoundException;
import cn.isbut.service.TagService;
import cn.isbut.common.RedisKey;
import cn.isbut.entity.Tag;
import cn.isbut.mapper.TagMapper;
import cn.isbut.service.RedisService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 文章分类业务层实现类
 * @Author: BeforeOne
 * @Date: Created in 2021/4/25 11:08
 */

@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final RedisService redisService;

    public TagServiceImpl(TagMapper tagMapper, RedisService redisService) {
        this.tagMapper = tagMapper;
        this.redisService = redisService;
    }

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        if (tagMapper.saveTag(tag) != 1){
            throw new PersistenceException("新增标签失败");
        }
        redisService.deleteCacheByKey(RedisKey.TAG_CLOUD_LIST);
        return 1;
    }

    @Override
    public Tag getTagById(Long id) {
        Tag tag = tagMapper.getTagById(id);
        if (tag == null) {
            throw new NotFoundException("标签不存在");
        }
        return tag;
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> getTagList() {
        return tagMapper.getTagList();
    }

    @Override
    public List<Tag> getTagBlogList() {
        return tagMapper.getTagBlogList();
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        if (tagMapper.updateTag(tag) != 1) {
            throw new PersistenceException("标签更新失败");
        }
        redisService.deleteCacheByKey(RedisKey.TAG_CLOUD_LIST);
        return 1;
    }

    @Transactional
    @Override
    public int deleteTagById(Long id) {
        if (tagMapper.deleteTagById(id) != 1) {
            throw new PersistenceException("标签删除失败");
        }
        redisService.deleteCacheByKey(RedisKey.TAG_CLOUD_LIST);
        return 1;
    }

    @Override
    public List<Tag> getTagListNotId() {
        String redisKey = RedisKey.TAG_CLOUD_LIST;
        List<Tag> tagListFromRedis = redisService.getListByValue(redisKey);
        if (tagListFromRedis != null){
            return tagListFromRedis;
        }
        List<Tag> tagList = tagMapper.getTagListNotId();
        redisService.saveListToValue(redisKey, tagList);
        return tagList;
    }

    @Override
    public List<Tag> getTagListByBlogId(Long blogId) {
        return tagMapper.getTagListByBlogId(blogId);
    }

    @Override
    public List<Tag> getTagByString(String text) {
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = convertToList(text);
        for (Long aLong : longs) {
            tags.add(tagMapper.getTagById(aLong));
        }
        return tags;
    }

    /**
    * @Description: 把前端的tagIds字符串转换为list集合
    * @param ids
    * @return {@link List<Long>}
    * @throws
    * @author BeforeOne
    * @data 2021/4/26 9:22
    *
    */
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

}
