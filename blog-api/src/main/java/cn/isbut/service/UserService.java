package cn.isbut.service;

import cn.isbut.entity.User;

/**
 * @author Ryan
 */
public interface UserService {
	/**
	 * 根据账号密码查询用户
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户
	 */
	User findUserByUsernameAndPassword(String username, String password);
}
