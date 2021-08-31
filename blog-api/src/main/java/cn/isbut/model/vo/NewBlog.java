package cn.isbut.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 最新推荐博客
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewBlog {
	private Integer id;
	private String title;
	private String password;
	private Boolean privacy;
}
