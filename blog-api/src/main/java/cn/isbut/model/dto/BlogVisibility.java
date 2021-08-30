package cn.isbut.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 博客可见性DTO
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BlogVisibility {
	/**
	 * 赞赏开关
	 */
	private Boolean appreciation;
	/**
	 * 推荐开关
	 */
	private Boolean recommend;
	/**
	 * 评论开关
	 */
	private Boolean commentEnabled;
	/**
	 * 是否置顶
	 */
	private Boolean top;
	/**
	 * 公开或私密
	 */
	private Boolean published;
	/**
	 * 密码保护
	 */
	private String password;
}
