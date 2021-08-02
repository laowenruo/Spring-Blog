package cn.isbut.config;

import cn.isbut.entity.LoginLog;
import cn.isbut.entity.User;
import cn.isbut.exception.ErrorRequestException;
import cn.isbut.service.LoginLogService;
import cn.isbut.util.IpAddressUtils;
import cn.isbut.util.JwtUtils;
import cn.isbut.util.StringUtils;
import cn.isbut.common.Result;
import cn.isbut.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: BeforeOne
 * @Date: Created in 2021/5/27 9:55
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private LoginLogService loginLogService;
    ThreadLocal<String> currentUsername = new ThreadLocal<>();


    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, LoginLogService loginLogService){
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
        this.loginLogService = loginLogService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        try {
            //判断是否为POST方法，如果不是POST方法请求则抛出异常
            if (!"POST".equals(request.getMethod())){
                throw new ErrorRequestException("请求方法错误");
            }
            //解析请求中的User对象
            User user = JacksonUtils.readValue(request.getInputStream(),User.class);
            currentUsername.set(user.getUsername());
            //Authentication对象有未认证和已认证两种状态
            //参数传入认证管理器（AuthenticationManager）的authenticate方法时，是一个未认证的对象，
            //它从客户端获取用户的身份信息（如用户名，密码），可以是从一个登录页面，也可以从Cookie中获取，
            //并由系统自动构造成一个Authentication对象
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (ErrorRequestException e) {
            response.setContentType("application/json;charset=utf-8");
            Result result  = Result.illegalRequest();
            PrintWriter out =response.getWriter();
            out.write(JacksonUtils.writeValueAsString(request));
            out.flush();
            out.close();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String jwt = JwtUtils.generateToken(authResult.getName(), authResult.getAuthorities());
        response.setContentType("application/json;charset=utf-8");
        User user = (User) authResult.getPrincipal();
        user.setPassword(null);
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", jwt);
        //将对象信息和token返还给前端
        Result result = Result.success(map);
        PrintWriter out = response.getWriter();
        out.write(JacksonUtils.writeValueAsString(result));
        out.flush();
        out.close();
        LoginLog log = handleLog(request, true, "登录成功");
        loginLogService.saveLoginLog(log);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        String msg = failed.getMessage();
        //登录不成功时，会抛出对应的异常
        if (failed instanceof LockedException) {
            msg = "账号被锁定";
        } else if (failed instanceof CredentialsExpiredException) {
            msg = "密码过期";
        } else if (failed instanceof AccountExpiredException) {
            msg = "账号过期";
        } else if (failed instanceof DisabledException) {
            msg = "账号被禁用";
        } else if (failed instanceof BadCredentialsException) {
            msg = "用户名或密码错误";
        }
        PrintWriter out = response.getWriter();
        out.write(JacksonUtils.writeValueAsString(Result.create(401, msg)));
        out.flush();
        out.close();
        LoginLog log = handleLog(request, false, StringUtils.substring(msg, 0, 50));
        loginLogService.saveLoginLog(log);
    }

    /**
     * 设置LoginLog对象属性
     *
     * @param request     请求对象
     * @param status      登录状态
     * @param description 操作描述
     * @return
     */
    private LoginLog handleLog(HttpServletRequest request, boolean status, String description) {
        String username = currentUsername.get();
        currentUsername.remove();
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        LoginLog log = new LoginLog(username, ip, status, description, userAgent);
        return log;
    }

}
