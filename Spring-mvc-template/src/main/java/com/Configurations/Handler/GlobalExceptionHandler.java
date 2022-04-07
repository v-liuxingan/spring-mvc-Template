package com.Configurations.Handler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/**
 * ExceptionHandler被声明在ControllerAdvice当中才能处理所有控制器抛出的异常
 * 如果只需要针对特定的controller，只需单独声明
 * */
@ControllerAdvice("com.Controller")
public class GlobalExceptionHandler {

    /**
     * 具体处理逻辑
     * */
    @ExceptionHandler
    public ModelAndView handleException(Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", new RuntimeException("全局捕获运行时异常"));
        mv.setViewName("error");
        ex.printStackTrace();
        return mv;
    }

}
