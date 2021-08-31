package cn.isbut.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 标签和博客数量
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TagBlogCount {
	private Integer id;
	/**
	 * 标签名
	 */
	private String name;
	/**
	 * 标签下博客数量
	 */
	private Integer value;
}
