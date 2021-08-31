package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.VisitLog;
import cn.isbut.model.dto.VisitLogUuidTime;

import java.util.List;

/**
 * @author Ryan
 * @Description: 访问日志持久层接口
 *
 */
@Mapper
@Repository
public interface VisitLogMapper {
	/**
	 * 查询日志
	 * @param uuid 访客标识码
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 访客日志列表
	 */
	List<VisitLog> getVisitLogListByUUIDAndDate(String uuid, String startDate, String endDate);

	/**
	 * 查询昨天的所有访问日志
	 * @return 访问日志
	 */
	List<VisitLogUuidTime> getUUIDAndCreateTimeByYesterday();

	/**
	 * 保存访问日志
	 * @param log 访问日志
	 * @return  成功与否状态
	 */
	int saveVisitLog(VisitLog log);

	/**
	 * 按id删除访问日志
	 * @param id 访问id
	 * @return 成功与否状态
	 */
	int deleteVisitLogById(Integer id);

	/**
	 * 得到今天访问数量
	 * @return 数量
	 */
	int countVisitLogByToday();
}
