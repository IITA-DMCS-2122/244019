package com.skrys.todolistaproject.config;

import org.springframework.beans.factory.annotation.Autowired;
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
        basePackages = "com.skrys.todolistaproject.repositories.pg1",
        entityManagerFactoryRef = "todoLPg1EntityManager",
        transactionManagerRef = "todoLPg1TransactionManager"
)
public class TodoLPg1Config {
    @Autowired
    private Environment env;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean todoLPg1EntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(todoLPg1DataSource());
        em.setPackagesToScan(
                new String[] { "com.skrys.todolistaproject.entity.pg" });

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        //System.out.println(env.getProperty("spring.jpa.hibernate.ddl-auto"));

        //System.out.println(env.getProperty("spring.jpa.properties.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }
    /*
    spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL81Dialect
     */

    @Primary
    @Bean
    public DataSource todoLPg1DataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("pg1.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("pg1.datasource.url"));
        dataSource.setUsername(env.getProperty("pg1.datasource.username"));
        dataSource.setPassword(env.getProperty("pg1.datasource.password"));

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager todoLPg1TransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                todoLPg1EntityManager().getObject());
        return transactionManager;
    }
}