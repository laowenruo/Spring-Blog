package cn.isbut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Ryan
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog {
    private Long id;
    private String username;
    private String ip;
    private String ipSource;
    private String os;
    private String browser;
    private Boolean status;
    private String description;
    private Date createTime;
    private String userAgent;

    public LoginLog(String username, String ip, Boolean status, String description, String userAgent){
        this.username = username;
        this.ip = ip;
        this.status = status;
        this.description = description;
        this.createTime = new Date();
        this.userAgent = userAgent;
    }
}
