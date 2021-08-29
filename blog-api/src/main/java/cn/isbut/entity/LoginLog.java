package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ryan
 * @Description: 登录日志
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginLog {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 用户名称
	 */
	private String username;
	/**
	 *ip
	 */
	private String ip;
	/**
	 * ip来源
	 */
	private String ipSource;
	/**
	 *操作系统
	 */
	private String os;
	/**
	 * 浏览器
	 */
	private String browser;
	/**
	 * 登录状态
	 */
	private Boolean status;
	/**
	 * 操作信息
	 */
	private String description;
	/**
	 * 操作时间
	 */
	private Date createTime;
	/**
	 *
	 */
	private String userAgent;

	public LoginLog(String username, String ip, boolean status, String description, String userAgent) {
		this.username = username;
		this.ip = ip;
		this.status = status;
		this.description = description;
		this.createTime = new Date();
		this.userAgent = userAgent;
	}
}