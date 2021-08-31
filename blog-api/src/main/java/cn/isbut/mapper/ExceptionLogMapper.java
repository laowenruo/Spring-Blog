package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.ExceptionLog;

import java.util.List;

/**
 * @author Ryan
 * @Description: 异常日志持久层接口
 */
@Mapper
@Repository
public interface ExceptionLogMapper {
	/**
	 * 得到范围内的错误日志
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 异常日志
	 */
	List<ExceptionLog> getExceptionLogListByDate(String startDate, String endDate);

	/**
	 * 保存日志
	 * @param log 异常日志
	 * @return 成功与否状态
	 */
	int saveExceptionLog(ExceptionLog log);

	/**
	 * 删除日志
	 * @param id 异常日志id
	 * @return 成功与否状态
	 */
	int deleteExceptionLogById(Integer id);
}
