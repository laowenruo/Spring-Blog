package cn.isbut.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Ryan
 * @Description: 分页结果
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PageResult<T> {
	/**
	 * 总页数
	 */
	private Integer totalPage;
	/**
	 * 数据
	 */
	private List<T> list;

	public PageResult(Integer totalPage, List<T> list) {
		this.totalPage = totalPage;
		this.list = list;
	}
}
