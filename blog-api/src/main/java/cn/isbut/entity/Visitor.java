package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ryan
 * @Description: 访客记录
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Visitor {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * uuid
	 */
	private String uuid;
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
	 * 首次访问时间
	 */
	private Date createTime;
	/**
	 * 最后访问时间
	 */
	private Date lastTime;
	/**
	 * 访问pv
	 */
	private Integer pv;
	/**
	 * 用户代理
	 */
	private String userAgent;

	public Visitor(String uuid, String ip, String userAgent) {
		this.uuid = uuid;
		this.ip = ip;
		Date date = new Date();
		this.createTime = date;
		this.lastTime = date;
		this.pv = 0;
		this.userAgent = userAgent;
	}
}
