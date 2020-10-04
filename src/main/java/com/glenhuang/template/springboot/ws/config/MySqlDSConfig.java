package com.glenhuang.template.springboot.ws.config;

import com.glenhuang.template.springboot.ws.annotation.MySqlDao;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MySqlDSConfig {

    @Bean(name = "mySqlDataSource")
    @ConfigurationProperties(prefix = "datasource.mysql")
    public DataSource mySqlDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("mySqlSqlSessionFactory")
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("mySqlDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dataSource);
        fb.setTypeAliasesPackage("com.glenhuang.template.springboot.ws.mapper.mysql");
        fb.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/META-INF/mapper/mysql/*.xml")
        );
        return fb.getObject();
    }

    @Bean(name = "mySqlTransactionManager")
    public DataSourceTransactionManager ds1TransactionManager(@Qualifier("mySqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mySqlSqlSessionTemplate")
    public SqlSessionTemplate ds1SqlSessionTemplate(@Qualifier("mySqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name="mySqlMapperScannerConfigurer")
    public MapperScannerConfigurer myMapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("mySqlSqlSessionFactory");
        configurer.setBasePackage("com.glenhuang.template.springboot.ws.mapper.mysql");
        configurer.setAnnotationClass(MySqlDao.class);
        return configurer;
    }
}
