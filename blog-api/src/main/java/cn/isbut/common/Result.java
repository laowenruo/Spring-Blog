package cn.isbut.common;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Ryan
 */
@Data
@NoArgsConstructor
public class Result {

    private Integer code;
    private String message;
    private Object data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result create(Integer code, String message){
        return new Result(code,message);
    }

    public static Result create(Integer code, String message, Object data){
        return new Result(code, message, data);
    }

    public static Result success(){
        return new Result(ResultInfo.SUCCESS.getCode(),
                            ResultInfo.SUCCESS.getMessage());
    }
    public static Result success(String message){
        return new Result(ResultInfo.SUCCESS.getCode(),
                            message);
    }
    public static Result success(Object data){
        return new Result(ResultInfo.SUCCESS.getCode(),
                            ResultInfo.SUCCESS.getMessage(),
                            data);
    }
    public static Result error(){
        return new Result(ResultInfo.ERROR.getCode(),
                            ResultInfo.ERROR.getMessage());
    }
    public static Result error(String message){
        return new Result(ResultInfo.ERROR.getCode(),
                message);
    }
    public static Result error(Object data){
        return new Result(ResultInfo.ERROR.getCode(),
                            ResultInfo.ERROR.getMessage(),
                            data);
    }
    public static Result noLogin(){
        return new Result(ResultInfo.NOT_LOGIN.getCode(),
                            ResultInfo.NOT_LOGIN.getMessage());
    }
    public static Result illegalRequest(){
        return new Result(ResultInfo.ILLEGAL_REQUEST.getCode(),
                            ResultInfo.ILLEGAL_REQUEST.getMessage());
    }
    public static Result tokenInvalid(){
        return new Result(ResultInfo.TOKEN_INVALID.getCode(),
                            ResultInfo.ILLEGAL_REQUEST.getMessage());
    }
    public static Result forbidde(){
        return new Result(ResultInfo.FORBIDDEN.getCode(),
                            ResultInfo.FORBIDDEN.getMessage());
    }

}
