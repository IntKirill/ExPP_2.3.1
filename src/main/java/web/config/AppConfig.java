package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Configuration
    @PropertySource("classpath:db.properties")
    @EnableTransactionManagement
    @ComponentScan(value = "web")
    public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(env.getRequiredProperty("db.entity.package"));

        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getHibernateProperties());
        return em;
    }


    public Properties getHibernateProperties() {
        Properties hibernateProperties = new Properties();
        try {

            InputStream is = this.getClass().getClassLoader()
                    .getResourceAsStream("hibernate.properties");
            hibernateProperties.load(is);
            return hibernateProperties;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load hibernate.properties", e);
        }
    }
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}

//        @Bean
//        public LocalSessionFactoryBean getSessionFactory() {
//            LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
//            factoryBean.setDataSource(getDataSource());
//
//            Properties props=new Properties();
//            props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//            props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//
//            factoryBean.setHibernateProperties(props);
//
//            factoryBean.setAnnotatedClasses(User.class);
//            return factoryBean;
//        }
//
//        @Bean
//        public HibernateTransactionManager getTransactionManager() {
//            HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//            transactionManager.setSessionFactory(getSessionFactory().getObject());
//            return transactionManager;
//        }


