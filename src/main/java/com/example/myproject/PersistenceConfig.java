package com.example.myproject;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

//
@Configuration
@MapperScan("com.example.myproject")
public class PersistenceConfig {

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() throws SQLException {
        oracle.jdbc.pool.OracleDataSource dataSource = new OracleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPortNumber(1521);
        dataSource.setDriverType("thin");
        dataSource.setUser("system");
        dataSource.setPassword("1");
        dataSource.setDatabaseName("FREE");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        return factoryBean.getObject();
    }
}
