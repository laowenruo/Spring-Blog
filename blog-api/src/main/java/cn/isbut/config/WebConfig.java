package cn.isbut.config;

import cn.isbut.interceptor.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ryan
 * @Description: 配置CORS跨域支持、拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    AccessLimitInterceptor accessLimitInterceptor;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedHeaders("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
				.maxAge(3600);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessLimitInterceptor);
	}

	@Autowired
	public void setAccessLimitInterceptor(AccessLimitInterceptor accessLimitInterceptor) {
		this.accessLimitInterceptor = accessLimitInterceptor;
	}
}
