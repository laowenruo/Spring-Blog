package cn.isbut.controller;

import cn.isbut.annotation.VisitLogger;
import cn.isbut.common.Result;
import cn.isbut.service.AboutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan
 * @Description:
 * */
@Api(tags = "关于我模块")
@RestController
public class AboutController {
    @Autowired
    private AboutService aboutService;

    @ApiOperation(value = "关于我界面")
    @VisitLogger(behavior = "访问页面", content = "关于我")
    @GetMapping("/about")
    public Result about() {
        return Result.success(aboutService.getAboutInfo());
    }
}
