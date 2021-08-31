package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.Moment;

import java.util.List;

/**
 * @author Ryan
 * @Description: 博客动态持久层接口
 *
 */
@Mapper
@Repository
public interface MomentMapper {
	/**
	 * 得到动态列表
	 * @return 动态列表
	 */
	List<Moment> getMomentList();

	/**
	 * 给动态点赞
	 * @param momentId 动态id
	 * @return 成功与否状态
	 */
	int addLikeByMomentId(Integer momentId);

	/**
	 * 更新动态发布状态
	 * @param momentId 动态id
	 * @param published 发布状态
	 * @return 成功与否状态
	 */
	int updateMomentPublishedById(Integer momentId, Boolean published);

	/**
	 * 根据id查询动态
	 * @param id 动态id
	 * @return 动态
	 */
	Moment getMomentById(Integer id);

	/**
	 * 按id删除动态
	 * @param id 动态id
	 * @return 成功与否状态
	 */
	int deleteMomentById(Integer id);

	/**
	 * 保存动态
	 * @param moment 动态
	 * @return 成功与否状态
	 */
	int saveMoment(Moment moment);

	/**
	 * 更新动态
	 * @param moment 动态
	 * @return 成功与否状态
	 */
	int updateMoment(Moment moment);
}
