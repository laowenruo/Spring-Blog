package cn.isbut.mapper;

import cn.isbut.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @Description: 用户持久层接口
 * @Author: ryan*
 */

@Mapper
@Repository
public interface UserMapper {

    /**
    * @Description: 根据用户名查询用户(用于登录验证)
    * @param username
    * @return {@link User}
    * @throws
    * @author ryan*
    */
    User findUserByUsername(String username);

    /**
    * 获取博主信息，固定
    * @param id
    * @return {@link User}
    * @author ryan
    */
    User getUserInfo(Long id);

}
