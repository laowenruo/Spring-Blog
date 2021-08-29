package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.User;

/**
 * @author Ryan
 * @Description: 用户持久层接口
 *
 */
@Mapper
@Repository
public interface UserMapper {
	/**
	 * 按用户名查询User
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
}
