package com.kmj.apiProject.common;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class DbConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        // XML 매퍼 파일 경로 수동 지정
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/kmj/apiProject/**/dao/mapper/*.xml");

        sessionFactory.setMapperLocations(resources); // 경로 설정

        return sessionFactory.getObject();
    }
}