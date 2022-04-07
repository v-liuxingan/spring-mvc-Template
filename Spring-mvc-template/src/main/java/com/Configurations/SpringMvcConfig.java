package com.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan({"com.Controller"})
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 内部视图解析器，需指定解析器类
     * */
    @Bean
    @Profile("jsp")//环境配置
    public ViewResolver JspResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("WEB-INF/page/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setExposePathVariables(true);
        return viewResolver;
    }


    /**
     * 文件上传解析器
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver upload = new CommonsMultipartResolver();
        upload.setMaxUploadSize(104857600);
        upload.setMaxInMemorySize(4096);
        upload.setDefaultEncoding("UTF-8");
        return upload;
    }

    //swagger2配置
    @Bean
    public Docket createRestApi() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("MVC-UI")//分组命名
                .apiInfo(apiInfo())
                .select()//默认为true,为false则无法看到swagger2-ui的页面
                .apis(RequestHandlerSelectors.basePackage("com.maincode.action.controller"))//可以指定扫描包，扫描具有某些注解的类或者类中方法
                .paths(PathSelectors.any())//路径，any:选择所有路径
                .build()//添加登录认证
                .host("localhost:8080")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                ;;//执行//.securitySchemes(securitySchemes())
//                .securityContexts(securityContexts())

        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试接口")
                .description("")
                .contact(new Contact("liusir", "", "3148493687@qq.com"))
                .version("1.0")
                .build();
    }

    //jwt实现认证和授权需要在http的header中添加一个叫Authorization的头，值为JWT的token；
    //才能调用接口
    private List<ApiKey> securitySchemes() {
        //设置请求头信息，添加上Authorization
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        //name:Api显示的名字，keyname:响应参数的键名，passAs:拼接位置
        //效果：在swaggerui界面多一个name为Authorization的按钮，
        // 可实现在响应头位置加上一个键名为Authorization的响应数据，对应的值需要自己填，在此为一个认证的token(jwt)
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/*/.*"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }

















    //----------------------------------------------------------------------------------------

    /**
     * 开启静态资源访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/html/**","images/**","swagger-ui.html","doc.html")//mapping
                //从前往后依次对应：html资源，图片资源
                .addResourceLocations("/WEB_INF/html","/WEB_INF/images","classpath:/META-INF/resources/")//location
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)));    //cache-period
//swagger-ui
//        swagger界面和doc界面，以及webjars都是swagger2所需的
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        registry.addResourceHandler("doc.html","/swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
    }

    /**
     * 全局资源访问
     * */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
