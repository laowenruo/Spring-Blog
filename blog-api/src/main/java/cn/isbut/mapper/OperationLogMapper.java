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
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 成功与否状态
	 */
	List<OperationLog> getOperationLogListByDate(String startDate, String endDate);

	/**
	 * 保存操作日志
	 * @param log 日志
	 * @return 成功与否状态
	 */
	int saveOperationLog(OperationLog log);

	/**
	 * 删除操作日志
	 * @param id 日志id
	 * @return 成功与否状态
	 */
	int deleteOperationLogById(Integer id);
}
