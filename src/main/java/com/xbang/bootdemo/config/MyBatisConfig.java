package com.xbang.bootdemo.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;


@Configuration
public class MyBatisConfig {

    public SqlSessionFactory sqlSessionFactory(org.apache.ibatis.session.Configuration configuration){
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;


    }

   /* @Bean
    public org.apache.ibatis.session.Configuration configuration (){
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.set
    }*/
}
