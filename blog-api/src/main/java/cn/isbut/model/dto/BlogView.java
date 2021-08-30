package cn.isbut.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 博客浏览量
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BlogView {
	private Integer id;
	private Integer views;
}
