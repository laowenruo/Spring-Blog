package cn.isbut.aspect;

import cn.isbut.annotation.OperationLogger;
import cn.isbut.annotation.VisitLogger;
import cn.isbut.entity.ExceptionLog;
import cn.isbut.service.ExceptionLogService;
import cn.isbut.util.AopUtils;
import cn.isbut.util.IpAddressUtils;
import cn.isbut.util.StringUtils;
import cn.isbut.util.JacksonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * 异常切面类
 * @author Ryan
 */
@Component
@Aspect
public class ExceptionLogAspect {
    @Autowired
    private ExceptionLogService exceptionLogService;

    /**
     * 配置切入点(所有controller下的异常都会被记录)
     */
    @Pointcut("execution(* cn.isbut.controller..*.*(..))")
    public void logPointcut() {
    }

    @AfterThrowing(value = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Exception e) {
        //抛出异常后设置对象属性，并且保存至数据库
        ExceptionLog log = handleLog(joinPoint, e);
        exceptionLogService.saveExceptionLog(log);
    }

    /**
     * 设置ExceptionLog对象属性
     *
     * @return
     */
    private ExceptionLog handleLog(JoinPoint joinPoint, Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String ip = IpAddressUtils.getIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        //todo 使用swagger后，可以直接使用注解上的内容作为 ExceptionLog 的 description
        String description = getDescriptionFromAnnotations(joinPoint);
        String error = StringUtils.getStackTrace(e);
        ExceptionLog log = new ExceptionLog(uri, method, description, error, ip, userAgent);
        Map<String, Object> requestParams = AopUtils.getRequestParams(joinPoint);
        log.setParam(StringUtils.substring(JacksonUtils.writeValueAsString(requestParams), 0, 2000));
        return log;
    }

    private String getDescriptionFromAnnotations(JoinPoint joinPoint) {
        String description = "";
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        OperationLogger operationLogger = method.getAnnotation(OperationLogger.class);
        if (operationLogger != null) {
            description = operationLogger.value();
            return description;
        }
        VisitLogger visitLogger = method.getAnnotation(VisitLogger.class);
        if (visitLogger != null) {
            description = visitLogger.behavior();
            return description;
        }
        return description;
    }

}
