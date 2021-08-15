package gov.nih.ncats.clinicaltrial;

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

public class DefaultDataSourceConfig2 {
    @Configuration
    @PropertySource({"classpath:application.properties"})
    @EnableJpaRepositories(
            basePackages = {"ix","gsrs", "gov.nih.ncats"},
            entityManagerFactoryRef = "defaultEntityManager",
            transactionManagerRef = "defaultTransactionManager"
    )
    public class PersistenceUserConfiguration {
        @Autowired
        private Environment env;

        @Bean
        @Primary
        public LocalContainerEntityManagerFactoryBean defaultEntityManager() {
            LocalContainerEntityManagerFactoryBean em
                    = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(defaultDataSource());
            em.setPackagesToScan(
                    new String[]{"ix","gsrs", "gov.nih.ncats"});

            HibernateJpaVendorAdapter vendorAdapter
                    = new HibernateJpaVendorAdapter();
            em.setJpaVendorAdapter(vendorAdapter);
            HashMap<String, Object> properties = new HashMap<>();
            properties.put("hibernate.hbm2ddl.auto",
                    env.getProperty("hibernate.hbm2ddl.auto"));
            properties.put("hibernate.dialect",
                    env.getProperty("hibernate.dialect"));
            em.setJpaPropertyMap(properties);

            return em;
        }

        @Primary
        @Bean
        public DataSource defaultDataSource() {

            DriverManagerDataSource dataSource
                    = new DriverManagerDataSource();
            dataSource.setDriverClassName(
                    env.getProperty("jdbc.driverClassName"));
            dataSource.setUrl(env.getProperty("user.jdbc.url"));
            dataSource.setUsername(env.getProperty("jdbc.user"));
            dataSource.setPassword(env.getProperty("jdbc.pass"));

            return dataSource;
        }

        @Primary
        @Bean
        public PlatformTransactionManager userTransactionManager() {

            JpaTransactionManager transactionManager
                    = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(
                    defaultEntityManager().getObject());
            return transactionManager;
        }
    }
}