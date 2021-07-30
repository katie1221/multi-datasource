package com.example.basicmultidatasource.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * slave数据源的mybatis配置
 * @author qzz
 */
@Configuration
@MapperScan(basePackages = "com.example.basicmultidatasource.mapper.slave",sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class SlaveMybatisConfig {

    /**
     * 配置SqlSessionFactory
     * @param dataSource
     * @return
     */
    @Bean("slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slave")DataSource dataSource) throws Exception {

        //设置数据源
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        //mapper的xml文件位置
        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/slave/*.xml"));
        //对应数据库的entity位置
        mybatisSqlSessionFactoryBean.setTypeAliasesPackage("com.example.basicmultidatasource.entity.slave");
        //返回
        return mybatisSqlSessionFactoryBean.getObject();
    }
}
