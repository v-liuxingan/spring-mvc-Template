package com.Configurations;


import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 取代web.xml的配置
 * */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
    * spring context应用上下文需要的bean
    * */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringRootConfig.class};
    }

    /**
    * DispatcherServlet中MVC上下文相关的Bean
    * */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    /**
    * 请求映射路径
    */
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        //配置profile，激活不同的环境
        servletContext.setInitParameter("spring.profiles.active", "jsp");//这里先使用名为jsp的环境（MVC配置当中）
        super.registerDispatcherServlet(servletContext);
    }


    /**
     * 注册过滤器---指定编码
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceResponseEncoding(true);
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{characterEncodingFilter, hiddenHttpMethodFilter};
    }
}
