package cn.isbut.service;

import org.springframework.scheduling.annotation.Async;
import cn.isbut.entity.Visitor;
import cn.isbut.model.dto.VisitLogUuidTime;

import java.util.List;

/**
 * @author Ryan
 */
public interface VisitorService {
	/**
	 * 根据范围查询访问者
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 访客
	 */
	List<Visitor> getVisitorListByDate(String startDate, String endDate);

	/**
	 * 得到昨日访问
	 * @return 昨日访问
	 */
	List<String> getNewVisitorIpSourceByYesterday();

	/**
	 * 判断uuid是否存在
	 * @param uuid 访客标识码
	 * @return 状态
	 */
	boolean hasUUID(String uuid);

	/**
	 * 异步保存访客
	 * @param visitor 访客
	 */
	@Async
	void saveVisitor(Visitor visitor);

	/**
	 * 通过uuid更新最后一次访问时间
	 * @param dto 访客日志
	 */
	void updatePVAndLastTimeByUUID(VisitLogUuidTime dto);

	/**
	 * 删除访客
	 * @param id 访客id
	 * @param uuid 访客标识码
	 */
	void deleteVisitor(Integer id, String uuid);
}
