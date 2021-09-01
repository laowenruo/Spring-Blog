package cn.isbut.service;

import cn.isbut.entity.Moment;

import java.util.List;

/**
 * @author Ryan
 */
public interface MomentService {
	/**
	 * 得到动态列表
	 * @return 动态列表
	 */
	List<Moment> getMomentList();

	/**
	 * 分页查询动态
	 * @param pageNum 分页
	 * @param adminIdentity 是否管理员
	 * @return 分页动态
	 */
	List<Moment> getMomentVOList(Integer pageNum, boolean adminIdentity);

	/**
	 * 点赞
	 * @param momentId 动态id
	 */
	void addLikeByMomentId(Integer momentId);

	/**
	 * 设置动态可见性
	 * @param momentId 动态id
	 * @param published 可见状态
	 */
	void updateMomentPublishedById(Integer momentId, Boolean published);

	/**
	 * 通过id得到动态
	 * @param id 动态id
	 * @return 动态
	 */
	Moment getMomentById(Integer id);

	/**
	 * 通过id删除动态
	 * @param id 动态id
	 */
	void deleteMomentById(Integer id);

	/**
	 * 保存动态
	 * @param moment 动态
	 */
	void saveMoment(Moment moment);

	/**
	 * 更新动态
	 * @param moment 动态
	 */
	void updateMoment(Moment moment);
}
