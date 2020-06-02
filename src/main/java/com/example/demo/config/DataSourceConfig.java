package com.example.demo.config;


import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
public class DataSourceConfig {

    @Autowired
    private DataBase0Config dataBase0Config;

    @Autowired
    private DataBase1Config dataBase1Config;

    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    public DataSource getDataSource() throws SQLException {
        return buildDataSource();
    }

    private DataSource buildDataSource() throws SQLException {
        //分库设置
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        //添加两个数据库database0和database1
        dataSourceMap.put(dataBase0Config.getDatabaseName(), dataBase0Config.createDataSource());
        dataSourceMap.put(dataBase1Config.getDatabaseName(), dataBase1Config.createDataSource());

        KeyGeneratorConfiguration keyGeneratorConfiguration = new KeyGeneratorConfiguration("SNOWFLAKE","id");//主键生成策略
        List<TableRuleConfiguration> tableRuleConfigurations = new ArrayList<TableRuleConfiguration>();

        //分表设置，大致思想就是将查询虚拟表,根据一定规则映射到真实表中去
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("message", "db$->{0..1}.message_$->{0..1}");//分表规则
        tableRuleConfiguration.setKeyGeneratorConfig(keyGeneratorConfiguration);//设定主键分库策略
        StandardShardingStrategyConfiguration standardShardingStrategyConfiguration = new StandardShardingStrategyConfiguration("accept_id", new PreciseShardingAlgorithm() {
            @Override
            public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
                Long value = Long.parseLong(preciseShardingValue.getValue().toString());
                //自定义分表规则，根据需求添加分表方法，减轻服务器压力
                if (value % 2 == 0) {
                    return "message_0";
                } else {
                    return "message_1";
                }
            }
        });
        tableRuleConfiguration.setTableShardingStrategyConfig(standardShardingStrategyConfiguration);
        tableRuleConfigurations.add(tableRuleConfiguration);
        //分库分表策略
        ShardingRuleConfiguration configuration = new ShardingRuleConfiguration();
        configuration.setTableRuleConfigs(tableRuleConfigurations);//分表规则集
        configuration.setDefaultKeyGeneratorConfig(keyGeneratorConfiguration);//默认主键生成策略
        configuration.setDefaultDataSourceName(dataBase0Config.getDatabaseName());//不分库分表情况下的数据源制定--默认数据库
        configuration.setDefaultTableShardingStrategyConfig(standardShardingStrategyConfiguration);//默认的分表规则
        //单键分库规则
        StandardShardingStrategyConfiguration strategyConfiguration = new StandardShardingStrategyConfiguration("id", new PreciseShardingAlgorithm() {
            @Override
            public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
                Long value = Long.parseLong(preciseShardingValue.getValue().toString());
                if (value % 2 == 0) {
                    return dataBase0Config.getDatabaseName();
                } else {
                    return dataBase1Config.getDatabaseName();
                }
            }
        });
        configuration.setDefaultDatabaseShardingStrategyConfig(strategyConfiguration);
        Properties properties = new Properties();
        properties.setProperty(dataBase0Config.getDatabaseName(),dataBase0Config.createDataSource().toString());
        properties.setProperty(dataBase1Config.getDatabaseName(),dataBase1Config.createDataSource().toString());
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, configuration, null);
        return dataSource;
    }

}
