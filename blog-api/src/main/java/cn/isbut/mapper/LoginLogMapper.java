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
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<LoginLog> getLoginLogListByDate(String startDate, String endDate);

	/**
	 * 添加日志
	 * @param log
	 * @return
	 */
	int saveLoginLog(LoginLog log);

	/**
	 * 删除日志
	 * @param id
	 * @return
	 */
	int deleteLoginLogById(Integer id);
}
