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
	 * @return 设置列表
	 */
	List<About> getList();

	/**
	 * 更新
	 * @param nameEn  名称
	 * @param value 值
	 * @return 成功与否
	 */
	int updateAbout(String nameEn, String value);

	/**
	 * 查询关于我页面评论开关状态
	 * @return 状态
	 */
	String getAboutCommentEnabled();
}
