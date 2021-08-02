package cn.isbut.service;

import cn.isbut.entity.LoginLog;

import java.util.List;

/**
 * @Description:
 * @Author: ryan
 */
public interface LoginLogService {

    List<LoginLog> getLoginLogByDate(String startDate, String endDate);

    void saveLoginLog(LoginLog log);

    void deleteLoginLog(Long id);

}
