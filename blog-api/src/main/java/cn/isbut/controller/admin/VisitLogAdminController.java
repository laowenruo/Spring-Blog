package cn.isbut.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.isbut.annotation.OperationLogger;
import cn.isbut.common.Result;
import cn.isbut.entity.VisitLog;
import cn.isbut.service.VisitLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 后台访问日志管理模块
 * @Author: ryan
 */
@Api(tags = "后台访问日志管理模块")
@RestController
@RequestMapping("/admin")
public class VisitLogAdminController {

    private final VisitLogService visitLogService;

    public VisitLogAdminController(VisitLogService visitLogService) {
        this.visitLogService = visitLogService;
    }

    /**
     * 分页查询访问日志列表
     *
     * @param uuid     按访客标识码模糊查询
     * @param date     按访问时间查询
     * @param pageNum  页码
     * @param pageSize 每页个数
     * @return {@link Result}
     * @author ryan
     */
    @ApiOperation(value = "分页查询访问日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "访客标识码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "data", value = "操作时间", dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, defaultValue = "10", dataType = "Integer", paramType = "query")

    })
    @GetMapping("/visitLogs")
    public Result visitLogs(@RequestParam(defaultValue = "") String uuid,
                            @RequestParam(defaultValue = "") String[] date,
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
        PageInfo<VisitLog> pageInfo = new PageInfo<>(visitLogService.getVisitLogListByUUIDAndDate(StringUtils.trim(uuid), startDate, endDate));
        return Result.success(pageInfo);
    }

    /**
     * 按id删除访问日志
     *
     * @param id 日志id
     * @return {@link Result}
     * @author ryan
     */
    @ApiOperation(value = "删除访问日志")
    @ApiImplicitParam(name = "id", value = "访问日志Id", required = true, dataType = "Long", paramType = "query")
    @OperationLogger(value = "删除访问日志")
    @DeleteMapping("/visitLog")
    public Result delete(@RequestParam Long id) {
        visitLogService.deleteVisitLogById(id);
        return Result.success("删除成功");
    }

}
