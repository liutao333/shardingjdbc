package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Data
@Component
@ConfigurationProperties(prefix = "spring.shardingsphere.datasource.db0")
public class DataBase0Config {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String databaseName;

    public DataSource createDataSource() {
        DruidDataSource data = new DruidDataSource();
        data.setDriverClassName(getDriverClassName());
        data.setUrl(getUrl());
        data.setUsername(getUsername());
        data.setPassword(getPassword());
        return data;
    }
}
