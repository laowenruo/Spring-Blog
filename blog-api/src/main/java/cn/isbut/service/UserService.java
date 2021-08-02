package cn.isbut.service;

import cn.isbut.entity.User;

/**
 * @author Ryan
 */
public interface UserService {

    User findUserByUsernameAndPassword(String username, String password);

    User getUserInfo(Long id);

}
