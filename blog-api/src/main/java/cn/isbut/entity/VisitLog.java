package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ryan
 * @Description: 访问日志
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VisitLog {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 访客标识码
	 */
	private String uuid;
	/**
	 * 请求接口
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
	 * 访问行为
	 */
	private String behavior;
	/**
	 * 访问内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String remark;
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
	 * 请求耗时（毫秒）
	 */
	private Integer times;
	/**
	 * 访问时间
	 */
	private Date createTime;
	/**
	 * 代理
	 */
	private String userAgent;

	public VisitLog(String uuid, String uri, String method, String behavior, String content, String remark, String ip, Integer times, String userAgent) {
		this.uuid = uuid;
		this.uri = uri;
		this.method = method;
		this.behavior = behavior;
		this.content = content;
		this.remark = remark;
		this.ip = ip;
		this.times = times;
		this.createTime = new Date();
		this.userAgent = userAgent;
	}
}
