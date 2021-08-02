package cn.isbut.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.isbut.annotation.OperationLogger;
import cn.isbut.common.Result;
import cn.isbut.entity.ScheduleJob;
import cn.isbut.entity.ScheduleJobLog;
import cn.isbut.service.ScheduleJobService;
import cn.isbut.util.common.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description: 后台定时任务管理模块
 * @Author: ryan
 */
@Api(tags = "后台定时任务管理模块")
@RestController
@RequestMapping("/admin")
public class ScheduleJobAdminController {

    private final ScheduleJobService scheduleJobService;

    public ScheduleJobAdminController(ScheduleJobService scheduleJobService) {
        this.scheduleJobService = scheduleJobService;
    }

    /**
    * 添加定时任务
    * @param scheduleJob 定时任务实体类
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "添加定时任务")
    @OperationLogger("添加定时任务")
    @PostMapping("/job")
    public Result saveJob(@RequestBody ScheduleJob scheduleJob){
        scheduleJob.setStatus(false);
        scheduleJob.setCreateTime(new Date());
        ValidatorUtils.validateEntity(scheduleJob);
        scheduleJobService.saveJob(scheduleJob);
        return Result.success("添加定时任务成功");
    }

    /**
    * 删除定时任务
    * @param jobId 定时任务id
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "删除定时任务")
    @ApiImplicitParam(name = "jobId", value = "定时任务id", dataType = "Long", paramType = "query")
    @OperationLogger("删除定时任务")
    @DeleteMapping("/job")
    public Result deleteJob(@RequestParam Long jobId){
        scheduleJobService.deleteJobById(jobId);
        return Result.success("删除定时任务成功");
    }

    /**
    * 修改定时任务
    * @param scheduleJob 定时任务实体类
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "修改定时任务")
    @OperationLogger("修改定时任务")
    @PutMapping("/job")
    public Result updateJob(@RequestBody ScheduleJob scheduleJob){
        scheduleJob.setStatus(false);
        ValidatorUtils.validateEntity(scheduleJob);
        scheduleJobService.updateJob(scheduleJob);
        return Result.success("修改定时任务成功");
    }
    /**
    * 修改定时任务状态
    * @param jobId 定时任务id
    * @param status 定时任务状态
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "修改定时任务状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobId", value = "定时任务Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "是否开启", required = true, dataType = "Boolean", paramType = "query")
    })
    @OperationLogger("修改定时任务状态")
    @PutMapping("/job/status")
    public Result updateJobStatus(@RequestParam Long jobId, @RequestParam Boolean status){
        scheduleJobService.updateJobStatusById(jobId, status);
        return Result.success("修改定时任务状态成功");
    }

    /**
    * 立即执行定时任务
    * @param jobId 定时任务id
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "立即执行定时任务")
    @ApiImplicitParam(name = "jobId", value = "定时任务id", dataType = "Long", paramType = "query")
    @OperationLogger("立即执行定时任务")
    @PostMapping("/job/run")
    public Result runJob(@RequestParam Long jobId){
        scheduleJobService.runJobById(jobId);
        return Result.success("提交执行");
    }

    /**
    * 定时任务列表
    * @param pageNum 页码
    * @param pageSize 每页个数
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "定时任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    @GetMapping("/jobs")
    public Result jobList(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ScheduleJob> pageInfo = new PageInfo<>(scheduleJobService.getJobList());
        return Result.success(pageInfo);
    }

    /**
    * 定时任务日志列表
    * @param date 显示的时间区域
    * @param pageNum 页码
    * @param pageSize 每页个数
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "定时任务日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "操作时间", dataType = "String[]", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, defaultValue = "10", dataType = "Integer", paramType = "query")

    })
    @GetMapping("/job/logs")
    public Result logs(@RequestParam(defaultValue = "") String[] date,
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
        PageInfo<ScheduleJobLog> pageInfo = new PageInfo<>(scheduleJobService.getJobLogListByDate(startDate, endDate));
        return Result.success(pageInfo);
    }

    /**
    * 删除定时任务日志
    * @param logId 定时任务日志id
    * @return {@link Result}
    * @author ryan
    */
    @ApiOperation(value = "删除定时任务日志")
    @ApiImplicitParam(name = "logId", value = "定时任务日志Id", required = true, dataType = "Long", paramType = "query")
    @OperationLogger(value = "删除定时任务日志")
    @DeleteMapping("/job/log")
    public Result delete(@RequestParam Long logId) {
        scheduleJobService.deleteJobLogByLogId(logId);
        return Result.success("删除成功");
    }
}

