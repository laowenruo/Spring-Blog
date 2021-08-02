package cn.isbut.config;

import cn.isbut.util.JwtUtils;
import cn.isbut.common.Result;
import cn.isbut.util.JacksonUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * @author Ryan
 */
public class JwtFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //非后台请求跳过
        if (!request.getRequestURI().startsWith("/admin")){
            filterChain.doFilter(request, servletResponse);
            return;
        }
        //从请求头中获取token
        String jwt = request.getHeader("Authorization");
        if (JwtUtils.judgeTokenIsExist(jwt)){
            try {
                //获取token有效部分（去除Bearer ）
                Claims claims = JwtUtils.getTokenBody(jwt);
                //获取 jwt所面向的用户
                String username = claims.getSubject();
                //获取权限
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
                //验证权限
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,null,grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("application/json;charset=utf-8");
                Result result = Result.tokenInvalid();
                PrintWriter out = response.getWriter();
                out.write(JacksonUtils.writeValueAsString(result));
                out.flush();
                out.close();
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
