package com.glenhuang.template.springboot.ws.config;

import com.glenhuang.template.springboot.ws.annotation.OracleDao;
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
public class OracleDSConfig {

    @Bean(name = "oracleDataSource")
    @ConfigurationProperties(prefix = "datasource.oracle")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("oracleSqlSessionFactory")
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("oracleDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dataSource);
        fb.setTypeAliasesPackage("com.glenhuang.template.springboot.ws.mapper.oracle");
        fb.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/META-INF/mapper/oracle/*.xml")
        );
        return fb.getObject();
    }

    @Bean(name = "OracleTransactionManager")
    public DataSourceTransactionManager ds1TransactionManager(@Qualifier("oracleDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "oracleSqlSessionTemplate")
    public SqlSessionTemplate ds1SqlSessionTemplate(@Qualifier("oracleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name="oracleMapperScannerConfigurer")
    public MapperScannerConfigurer myMapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("oracleSqlSessionFactory");
        configurer.setBasePackage("com.glenhuang.template.springboot.ws.mapper.oracle");
        configurer.setAnnotationClass(OracleDao.class);
        return configurer;
    }
}
