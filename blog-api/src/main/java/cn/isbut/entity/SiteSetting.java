package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 站点设置
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SiteSetting {
	private Integer id;
	private String nameEn;
	private String nameZh;
	private String value;
	private Integer type;
}
