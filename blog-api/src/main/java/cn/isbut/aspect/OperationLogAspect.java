package cn.isbut.aspect;

import cn.isbut.annotation.OperationLogger;
import cn.isbut.entity.OperationLog;
import cn.isbut.service.OperationLogService;
import cn.isbut.util.AopUtils;
import cn.isbut.util.JacksonUtils;
import cn.isbut.util.JwtUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import cn.isbut.util.IpAddressUtils;
import cn.isbut.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Ryan
 * @Description: AOP记录操作日志
 */
@Component
@Aspect
public class OperationLogAspect {

    OperationLogService operationLogService;
	/**
	 * 防止多线程冲突，采用ThreadLocal，并且用完后手动释放
	 */
	ThreadLocal<Long> currentTime = new ThreadLocal<>();

	/**
	 * 配置切入点
	 */
	@Pointcut("@annotation(operationLogger)")
	public void logPointcut(OperationLogger operationLogger) {
	}

	/**
	 * 配置环绕通知
	 *
	 * @param joinPoint 对象
	 * @return 结果
	 * @throws Throwable 异常
	 */
	@Around(value = "logPointcut(operationLogger)", argNames = "joinPoint,operationLogger")
	public Object logAround(ProceedingJoinPoint joinPoint, OperationLogger operationLogger) throws Throwable {
		currentTime.set(System.currentTimeMillis());
		Object result = joinPoint.proceed();
		int times = (int) (System.currentTimeMillis() - currentTime.get());
		//手动释放，防止内存泄漏
		currentTime.remove();
		OperationLog operationLog = handleLog(joinPoint, operationLogger, times);
		operationLogService.saveOperationLog(operationLog);
		return result;
	}

	/**
	 * 获取HttpServletRequest请求对象，并设置OperationLog对象属性
	 *
	 * @param operationLogger 操作日志注解
	 * @param times 接口耗时
	 * @return 操作日志
	 */
	private OperationLog handleLog(ProceedingJoinPoint joinPoint, OperationLogger operationLogger, int times) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		assert attributes != null;
		HttpServletRequest request = attributes.getRequest();
		String username = JwtUtils.getTokenBody(request.getHeader("Authorization")).getSubject();
		String uri = request.getRequestURI();
		String method = request.getMethod();
		String description = operationLogger.value();
		String ip = IpAddressUtils.getIpAddress(request);
		String userAgent = request.getHeader("User-Agent");
		OperationLog log = new OperationLog(username, uri, method, description, ip, times, userAgent);
		Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
		log.setParam(StringUtils.substring(JacksonUtils.writeValueAsString(requestParams), 0, 2000));
		return log;
	}

	@Autowired
	public void setOperationLogService(OperationLogService operationLogService){
		this.operationLogService=operationLogService;
	}
}