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
	 * @return
	 */
	List<Moment> getMomentList();

	/**
	 * 给动态点赞
	 * @param momentId
	 * @return
	 */
	int addLikeByMomentId(Integer momentId);

	/**
	 * 更新动态发布状态
	 * @param momentId
	 * @param published
	 * @return
	 */
	int updateMomentPublishedById(Integer momentId, Boolean published);

	/**
	 * 根据id查询动态
	 * @param id
	 * @return
	 */
	Moment getMomentById(Integer id);

	/**
	 * 按id删除动态
	 * @param id
	 * @return
	 */
	int deleteMomentById(Integer id);

	/**
	 * 按id删除动态
	 * @param moment
	 * @return
	 */
	int saveMoment(Moment moment);

	/**
	 * 更新动态
	 * @param moment
	 * @return
	 */
	int updateMoment(Moment moment);
}
