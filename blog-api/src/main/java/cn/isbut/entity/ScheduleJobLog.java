package cn.isbut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Ryan
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleJobLog {
    private Long logId;//日志id
    private Long jobId;//任务id
    private String beanName;//spring bean名称
    private String methodName;//方法名
    private String params;//参数
    private Boolean status;//任务执行结果
    private String error;//异常信息
    private Integer times;//耗时(单位：毫秒)
    private Date createTime;//创建时间
}
