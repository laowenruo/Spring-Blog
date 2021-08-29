package cn.isbut.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 关于我
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class About {
	private Integer id;
	private String nameEn;
	private String nameZh;
	private String value;
}
