package cn.isbut.model.vo;

import cn.isbut.entity.Category;
import cn.isbut.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ryan
 * @Description: 博客简要信息
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BlogInfo {
	private Integer id;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 浏览次数
	 */
	private Integer views;
	/**
	 * 文章字数
	 */
	private Integer words;
	/**
	 * 阅读时长(分钟)
	 */
	private Integer readTime;
	/**
	 * 是否置顶
	 */
	private Boolean top;
	/**
	 * 文章密码
	 */
	private String password;
	/**
	 * 是否私密文章
	 */
	private Boolean privacy;
	/**
	 * 文章分类
	 */
	private Category category;
	/**
	 * 文章标签
	 */
	private List<Tag> tags = new ArrayList<>();
}
