package cn.isbut.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.isbut.annotation.OperationLogger;
import cn.isbut.common.Result;
import cn.isbut.entity.Visitor;
import cn.isbut.service.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: ryan
 */
@Api(tags = "后台访客管理模块")
@RestController
@RequestMapping("/admin")
public class VisitorAdminController {

    private final VisitorService visitorService;

    public VisitorAdminController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    /**
     * 分页查询访客列表
     *
     * @param date     按最后访问时间查询
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return
     */
    @ApiOperation(value = "分页查询访客列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "操作时间", dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, defaultValue = "10", dataType = "Integer", paramType = "query")

    })
    @GetMapping("/visitors")
    public Result visitors(@RequestParam(defaultValue = "") String[] date,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        String startDate = null;
        String endDate = null;
        if (date.length == 2) {
            startDate = date[0];
            endDate = date[1];
        }
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<Visitor> pageInfo = new PageInfo<>(visitorService.getVisitorListByDate(startDate, endDate));
        return Result.success(pageInfo);
    }

    /**
     * 按id删除访客
     * 按uuid删除Redis缓存
     *
     * @param id   访客id
     * @param uuid 访客uuid
     * @return
     */
    @ApiOperation(value = "删除访客")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "访客id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "uuid", value = "访客uuid", dataType = "String", paramType = "query")
    })
    @OperationLogger(value = "删除访客")
    @DeleteMapping("/visitor")
    public Result delete(@RequestParam Long id, @RequestParam String uuid) {
        visitorService.deleteVisitor(id, uuid);
        return Result.success("删除成功");
    }
}
