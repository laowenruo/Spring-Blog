package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 访问记录
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VisitRecord {
	private Integer id;
	/**
	 * 访问量
	 */
	private Integer pv;
	/**
	 * 独立用户
	 */
	private Integer uv;
	/**
	 * 日期
	 */
	private String date;

	public VisitRecord(Integer pv, Integer uv, String date) {
		this.pv = pv;
		this.uv = uv;
		this.date = date;
	}
}
