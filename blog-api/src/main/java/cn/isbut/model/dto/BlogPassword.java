package cn.isbut.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ryan
 * @Description: 受保护文章密码DTO
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BlogPassword {
	private Integer blogId;
	private String password;
}
