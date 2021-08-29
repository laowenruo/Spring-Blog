package cn.isbut.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 城市访客数量
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CityVisitor {
	/**
	 * 城市名称
	 */
	private String city;
	/**
	 * 独立访客数量
	 */
	private Integer uv;
}
