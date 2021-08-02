package cn.isbut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ryan
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfoDTO {
    private String username;
    private String password;
}
