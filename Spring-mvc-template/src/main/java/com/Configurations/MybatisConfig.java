package com.Configurations;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * spring集成mybatis全注解配置
 * */
@Configuration
@MapperScan("com.Dao")
@PropertySource("classpath:application.properties")//导入了配置文件路径
public class MybatisConfig {
    // sqlSessionFactory（创建以开启会话）
    @Bean
    public SqlSessionFactory registerSqlSessionFactory(@Autowired DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    // dataSource（数据库连接池）
    @Bean
    public DataSource dataSource(@Value("${jdbc.url}") String url,
                                 @Value("${jdbc.username}") String userName,
                                 @Value("${jdbc.password}") String password) {
        return new DriverManagerDataSource(url, userName, password);
    }

}
