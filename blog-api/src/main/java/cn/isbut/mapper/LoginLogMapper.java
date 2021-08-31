package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.LoginLog;

import java.util.List;

/**
 * @author Ryan
 * @Description: 登录日志持久层接口
 *
 */
@Mapper
@Repository
public interface LoginLogMapper {
	/**
	 * 查询日志
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 登录日志
	 */
	List<LoginLog> getLoginLogListByDate(String startDate, String endDate);

	/**
	 * 添加日志
	 * @param log 日志
	 * @return 成功与否状态
	 */
	int saveLoginLog(LoginLog log);

	/**
	 * 删除日志
	 * @param id 日志id
	 * @return 成功与否状态
	 */
	int deleteLoginLogById(Integer id);
}
