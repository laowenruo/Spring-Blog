package cn.isbut.controller.admin;

import cn.isbut.annotation.OperationLogger;
import cn.isbut.common.Result;
import cn.isbut.service.AboutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description:
 * @Author: ryan
 */
@Api(tags = "后台关于我模块")
@RestController
@RequestMapping("/admin")
public class AboutAdminController {

    private final AboutService aboutService;

    public AboutAdminController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    /**
    * 获取关于我页面配置
    * @param
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "获取关于我页面信息")
    @GetMapping("/about")
    public Result about() {
        return Result.success(aboutService.getAboutSetting());
    }

    /**
    * 修改关于我页面
    * @param map
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "更新关于我页面信息")
    @ApiImplicitParam(name = "map", value = "每一条信息为map，一一对应", required = true, dataType = "Map", paramType = "body")
    @OperationLogger("修改关于我页面")
    @PutMapping("/about")
    public Result updateAbout(@RequestBody Map<String, String> map) {
        aboutService.updateAbout(map);
        return Result.success("修改成功");
    }

}
