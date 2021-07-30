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
 * master数据源的mybatis配置
 *
 * sqlSessionFactoryRef 即本配置中的注入的 SqlSessionFactory
 * @author qzz
 */
@Configuration
@MapperScan(basePackages = "com.example.basicmultidatasource.mapper.master",sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterMybatisConfig {

    /**
     * 配置SqlSessionFactory
     * @param dataSource
     * @return
     */
    @Bean("masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("master")DataSource dataSource) throws Exception {

        //设置数据源
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        //mapper的xml文件位置
        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/master/*.xml"));
        //对应数据库的entity位置
        mybatisSqlSessionFactoryBean.setTypeAliasesPackage("com.example.basicmultidatasource.entity.master");
        //返回
        return mybatisSqlSessionFactoryBean.getObject();
    }
}
