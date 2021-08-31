package cn.isbut.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 关键字搜索博客
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SearchBlog {
	private Integer id;
	private String title;
	private String content;
}
