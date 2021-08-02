package cn.isbut.config;

import cn.isbut.common.Result;
import cn.isbut.util.JacksonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 没有登录
 * @author Ryan
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
    * @Description: 给页面返回没有登录这个错误
    * @param httpServletRequest
    * @param httpServletResponse
    * @param e
    * @return
    * @throws
    * @author BeforeOne
    * @data 2021/5/27 9:24
    *
    */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        Result result = Result.noLogin();
        out.write(JacksonUtils.writeValueAsString(result));
        out.flush();
        out.close();
    }
}
