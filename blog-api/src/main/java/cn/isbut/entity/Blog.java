package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ryan
 * @Description: 博客文章
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Blog {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章首图，用于随机文章展示
	 */
	private String firstPicture;
	/**
	 * 文章正文
	 */
	private String content;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 公开或私密
	 */
	private Boolean published;
	/**
	 * 推荐开关
	 */
	private Boolean recommend;
	/**
	 * 赞赏开关
	 */
	private Boolean appreciation;
	/**
	 * 评论开关
	 */
	private Boolean commentEnabled;
	/**
	 * 是否置顶
	 */
	private Boolean top;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
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
	 * 密码保护
	 */
	private String password;
	/**
	 * 文章作者
	 */
	private User user;
	/**
	 * 文章分类
	 */
	private Category category;
	/**
	 * 文章标签
	 */
	private List<Tag> tags = new ArrayList<>();
}
