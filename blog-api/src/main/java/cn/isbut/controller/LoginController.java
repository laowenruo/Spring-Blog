package cn.isbut.controller;

import cn.isbut.dto.LoginInfoDTO;
import cn.isbut.util.JwtUtils;
import cn.isbut.common.Result;
import cn.isbut.entity.User;
import cn.isbut.service.SiteSettingService;
import cn.isbut.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan
 * @Description: 前台登录
 */
@Api(tags = "登录模块")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private SiteSettingService siteSettingService;

    /**
    * @Description: 前台登录，携带 admin token
    * @param loginInfoDTO
    * @return {@link Result}
    * @throws
    *
    */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginInfoDTO loginInfoDTO){
        User user = userService.findUserByUsernameAndPassword(loginInfoDTO.getUsername(), loginInfoDTO.getPassword());
        if (!"ROLE_admin".equals(user.getRole())){
            return Result.forbidde();
        }
        user.setPassword(null);
        String jwt = JwtUtils.generateToken("admin:" + user.getUsername());
        Map<String,Object> map =new HashMap<>();
        map.put("user", user);
        map.put("token", jwt);
        return Result.success(map);
    }

    @ApiOperation(value = "查询网页后缀信息")
    @GetMapping("/webTitleSuffix")
    public Result getWebTitleSuffix() {
        String webTitleSuffix = siteSettingService.getWebTitleSuffix();
        Map<String, Object> map = new HashMap<>();
        map.put("webTitleSuffix", webTitleSuffix);
        return Result.success(map);
    }

}
