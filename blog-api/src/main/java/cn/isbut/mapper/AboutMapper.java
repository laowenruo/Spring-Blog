package cn.isbut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.isbut.entity.About;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Ryan
 */
@Mapper
@Repository
public interface AboutMapper extends BaseMapper {

    /**
    * 更新关于我设置
    * @param nameEn 名字
    * @param value 值
    * @return {@link int}
    * @author ryan
    */
    int updateAbout(String nameEn, String value);

    /**
    * 查询关于我设置
    * @param
    * @return {@link List< About>}
    * @author ryan
    */
    List<About> getList();

    /**
    * 查询关于我页面评论开关状态
    * @param
    * @return {@link String}
    * @author ryan
    */
    String getAboutCommentEnabled();
}
