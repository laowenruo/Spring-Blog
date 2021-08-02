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
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionLog {
    private Long id;
    private String uri;//请求接口
    private String method;//请求方式
    private String param;//请求参数
    private String description;//操作描述
    private String error;//异常信息
    private String ip;//ip
    private String ipSource;//ip来源
    private String os;//操作系统
    private String browser;//浏览器
    private Date createTime;//操作时间
    private String userAgent;

    public ExceptionLog(String uri, String method, String description, String error, String ip, String userAgent) {
        this.uri = uri;
        this.method = method;
        this.description = description;
        this.error = error;
        this.ip = ip;
        this.createTime = new Date();
        this.userAgent = userAgent;
    }
}
