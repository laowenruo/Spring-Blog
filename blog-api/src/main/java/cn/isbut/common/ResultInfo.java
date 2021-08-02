package cn.isbut.common;


/**
 * @author Ryan
 */
public enum ResultInfo {
    SUCCESS(200,"操作成功"),
    ERROR(400,"操作失败"),
    ILLEGAL_REQUEST(401,"非法请求"),
    TOKEN_INVALID(402,"凭证失效"),
    NOT_LOGIN(403,"没有登录"),
    NOT_FOUND(404,"没有找到"),
    FORBIDDEN(405,"没有权限"),
    ;
    private Integer code;
    private String message;

    ResultInfo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
