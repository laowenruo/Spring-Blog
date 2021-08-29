package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.OperationLog;

import java.util.List;

/**
 * @author Ryan
 * @Description: 操作日志持久层接口
 */
@Mapper
@Repository
public interface OperationLogMapper {
	/**
	 * 得到范围内的操作日志
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<OperationLog> getOperationLogListByDate(String startDate, String endDate);

	/**
	 * 保存操作日志
	 * @param log
	 * @return
	 */
	int saveOperationLog(OperationLog log);

	/**
	 * 删除操作日志
	 * @param id
	 * @return
	 */
	int deleteOperationLogById(Integer id);
}
