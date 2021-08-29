package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ryan
 * @Description: 异常日志
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExceptionLog {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 请求uri
	 */
	private String uri;
	/**
	 * 请求方式
	 */
	private String method;
	/**
	 * 请求参数
	 */
	private String param;
	/**
	 * 操作描述
	 */
	private String description;
	/**
	 * 异常信息
	 */
	private String error;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * ip来源
	 */
	private String ipSource;
	/**
	 * 操作系统
	 */
	private String os;
	/**
	 * 浏览器
	 */
	private String browser;
	/**
	 * 操作时间
	 */
	private Date createTime;
	/**
	 * 代理头
	 */
	private String userAgent;

	public ExceptionLog(String uri, String method, String description, String error, String ip, String userAgent) {
		this.uri = uri;
		this.method = method;
		this.description = description;
		this.error = error;
		this.ip = ip;
		this.createTime = new Date();
		this.userAgent = userAgent;
	}
}
