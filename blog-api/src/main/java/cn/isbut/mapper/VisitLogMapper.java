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
	 * @param uuid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<VisitLog> getVisitLogListByUUIDAndDate(String uuid, String startDate, String endDate);

	/**
	 * 查询昨天的所有访问日志
	 * @return
	 */
	List<VisitLogUuidTime> getUUIDAndCreateTimeByYesterday();

	/**
	 * 保存访问日志
	 * @param log
	 * @return
	 */
	int saveVisitLog(VisitLog log);

	/**
	 * 按id删除访问日志
	 * @param id
	 * @return
	 */
	int deleteVisitLogById(Integer id);

	/**
	 * 得到今天访问数量
	 * @return
	 */
	int countVisitLogByToday();
}
