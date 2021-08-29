package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ryan
 * @Description: 定时任务日志
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleJobLog {
	/**
	 * 日志id
	 */
	private Integer logId;
	/**
	 * 任务id
	 */
	private Integer jobId;
	/**
	 * spring bean名称
	 */
	private String beanName;
	/**
	 * 方法名
	 */
	private String methodName;
	/**
	 * 参数
	 */
	private String params;
	/**
	 * 任务执行结果
	 */
	private Boolean status;
	/**
	 * 异常信息
	 */
	private String error;
	/**
	 * (单位：毫秒)
	 */
	private Integer times;
	/**
	 * 创建时间
	 */
	private Date createTime;
}
