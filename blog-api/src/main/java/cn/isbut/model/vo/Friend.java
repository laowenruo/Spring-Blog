package cn.isbut.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 友链VO
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Friend {
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 站点
	 */
	private String website;
	/**
	 * 头像
	 */
	private String avatar;
}
