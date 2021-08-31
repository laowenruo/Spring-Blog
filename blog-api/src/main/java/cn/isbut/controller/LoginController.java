package cn.isbut.controller;

import cn.isbut.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.entity.User;
import cn.isbut.model.dto.LoginInfo;
import cn.isbut.model.vo.Result;
import cn.isbut.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 前台登录
 */
@RestController
public class LoginController {

	UserService userService;

	/**
	 * 登录成功后，签发博主身份Token
	 *
	 * @param loginInfo
	 * @return
	 */
	@PostMapping("/login")
	public Result login(@RequestBody LoginInfo loginInfo) {
		User user = userService.findUserByUsernameAndPassword(loginInfo.getUsername(), loginInfo.getPassword());
		if (!"ROLE_admin".equals(user.getRole())) {
			return Result.create(403, "无权限");
		}
		user.setPassword(null);
		String jwt = JwtUtils.generateToken("admin:" + user.getUsername());
		Map<String, Object> map = new HashMap<>(2);
		map.put("user", user);
		map.put("token", jwt);
		return Result.ok("登录成功", map);
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
