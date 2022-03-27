package com.skrys.todolistaproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(
        basePackages = "com.skrys.todolistaproject.repositories.pg2",
        entityManagerFactoryRef = "todoLPg2EntityManager",
        transactionManagerRef = "todoLPg2TransactionManager"
)
public class TodoLPg2Config {
    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean todoLPg2EntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(todoLPg2DataSource());
        em.setPackagesToScan(
                new String[] { "com.skrys.todolistaproject.entity.pg" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource todoLPg2DataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("pg2.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("pg2.datasource.url"));
        dataSource.setUsername(env.getProperty("pg2.datasource.username"));
        dataSource.setPassword(env.getProperty("pg2.datasource.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager todoLPg2TransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                todoLPg2EntityManager().getObject());
        return transactionManager;
    }
}