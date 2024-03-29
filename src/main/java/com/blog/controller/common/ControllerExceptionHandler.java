package com.blog.controller.common;

import com.blog.config.SettingsConfig;
import com.blog.pojo.WebhookMessage;
import com.blog.util.WxChatbotClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 非状态码跳转到自定义的error页面
 * 状态码自动处理到相对应的状态码页面
 * 拦截所有controller抛出的异常，对异常进行统一的处理
 * @author Ryan
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @Resource
    private SettingsConfig settings;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 表示该方法可以处理所有类型异常
     * @param request 请求
     * @param e 异常
     * @return 错误页面
     * @throws Exception 异常
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        //日志打印异常信息
        logger.error("Request url: {}, Request ip: {}, Exception: {} ", request.getRequestURI(), request.getRemoteAddr(), e.getMessage());
        //不处理带有ResponseStatus注解的异常
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        if (!"0".equals(settings.getWx_Webhook())){
            WebhookMessage message = new WebhookMessage();
            message.setText("博客异常请求通知\n请求url:" + request.getRequestURI() + "\n请求ip:" + request.getRemoteAddr() + "\n错误原因:" + e.getMessage());
            WxChatbotClient.send(settings.getWx_Webhook(), message);
        }
        //返回异常信息到自定义error页面
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURI());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }

}
