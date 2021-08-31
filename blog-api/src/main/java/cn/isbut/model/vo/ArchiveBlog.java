package cn.isbut.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 归档页面博客简要信息
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ArchiveBlog {
	private Integer id;
	private String title;
	private String day;
	private String password;
	private Boolean privacy;
}
