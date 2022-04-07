package com.Configurations;

import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Aspect
@MapperScan("com.Dao")
@Configuration
@ComponentScan(basePackages = "com")
public class SpringRootConfig {

}
