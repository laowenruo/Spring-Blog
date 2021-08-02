package cn.isbut.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.isbut.annotation.OperationLogger;
import cn.isbut.common.Result;
import cn.isbut.entity.OperationLog;
import cn.isbut.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 后台操作日志管理模块
 * @Author: ryan
 */
@Api(tags = "后台操作日志管理模块")
@RestController
@RequestMapping("/admin")
public class OperationLogServiceController {

    private final OperationLogService operationLogService;

    public OperationLogServiceController(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    /**
     * 分页查询操作日志列表
     *
     * @param date     按操作时间查询
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return {@link Result}
     * @author ryan
     */
    @ApiOperation(value = "分页查询操作日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "操作时间", dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, defaultValue = "10", dataType = "Integer", paramType = "query")

    })
    @GetMapping("/operationLogs")
    public Result operationLogs(@RequestParam(defaultValue = "") String[] date,
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
        PageInfo<OperationLog> pageInfo = new PageInfo<>(operationLogService.getOperationLogListByDate(startDate, endDate));
        return Result.success(pageInfo);
    }

    /**
     * 按id删除操作日志
     *
     * @param id 日志id
     * @return {@link Result}
     * @author ryan
     */
    @ApiOperation(value = "删除操作日志")
    @ApiImplicitParam(name = "id", value = "操作日志Id", required = true, dataType = "Long", paramType = "query")
    @OperationLogger(value = "删除操作日志")
    @DeleteMapping("/operationLog")
    public Result delete(@RequestParam Long id) {
        operationLogService.deleteOperationLogById(id);
        return Result.success("删除成功");
    }
}