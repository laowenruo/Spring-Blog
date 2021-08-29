package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.About;

import java.util.List;

/**
 * @author Ryan
 * @Description: 关于我持久层接口
 *
 */
@Mapper
@Repository
public interface AboutMapper {
	/**
	 * 查询关于我设置
	 * @return
	 */
	List<About> getList();

	/**
	 * 更新
	 * @param nameEn
	 * @param value
	 * @return
	 */
	int updateAbout(String nameEn, String value);

	/**
	 * 查询关于我页面评论开关状态
	 * @return
	 */
	String getAboutCommentEnabled();
}
