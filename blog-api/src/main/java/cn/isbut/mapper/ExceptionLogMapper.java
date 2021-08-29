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
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<ExceptionLog> getExceptionLogListByDate(String startDate, String endDate);

	/**
	 * 保存日志
	 * @param log
	 * @return
	 */
	int saveExceptionLog(ExceptionLog log);

	/**
	 * 删除日志
	 * @param id
	 * @return
	 */
	int deleteExceptionLogById(Integer id);
}
