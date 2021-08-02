package cn.isbut.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @author Ryan
 */
@Data
@NoArgsConstructor
public class VisitLog {
    private Long id;
    private String uuid;//访客标识码
    private String uri;//请求接口
    private String method;//请求方式
    private String param;//请求参数
    private String behavior;//访问行为
    private String content;//访问内容
    private String remark;//备注
    private String ip;//ip
    private String ipSource;//ip来源
    private String os;//操作系统
    private String browser;//浏览器
    private Integer times;//请求耗时（毫秒）
    private Date createTime;//访问时间
    private String userAgent;

    public VisitLog(String uuid, String uri, String method, String behavior, String content, String remark, String ip, Integer times, String userAgent) {
        this.uuid = uuid;
        this.uri = uri;
        this.method = method;
        this.behavior = behavior;
        this.content = content;
        this.remark = remark;
        this.ip = ip;
        this.times = times;
        this.createTime = new Date();
        this.userAgent = userAgent;
    }
}
