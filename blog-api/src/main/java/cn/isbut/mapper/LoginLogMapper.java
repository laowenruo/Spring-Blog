package cn.isbut.mapper;

import cn.isbut.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 * @Description:
 */
@Mapper
@Repository
public interface LoginLogMapper {

    List<LoginLog> getLoginLogByDate(String startDate, String endDate);

    int saveLoginLog(LoginLog log);

    int deleteLoginLog(Long id);
}
