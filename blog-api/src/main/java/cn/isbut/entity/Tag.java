package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan
 * @Description: 博客标签
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Tag {
	private Integer id;
	/**
	 * 标签名称
	 */
	private String name;
	/**
	 * 标签颜色(与Semantic UI提供的颜色对应，可选)
	 */
	private String color;
	/**
	 * 该标签下的博客文章
	 */
	private List<Blog> blogs = new ArrayList<>();
}
