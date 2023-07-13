package com.mikpolv.intensive28.homework.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("com.mikpolv.intensive28.homework.spring")
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@EnableWebMvc
public class MySpringConfig implements WebMvcConfigurer {

  private final ApplicationContext applicationContext;
  private final Environment environment;

  @Autowired
  public MySpringConfig(ApplicationContext applicationContext, Environment environment) {
    this.applicationContext = applicationContext;
    this.environment = environment;
  }

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(applicationContext);
    templateResolver.setPrefix("/WEB-INF/views/");
    templateResolver.setSuffix(".html");
    templateResolver.setCharacterEncoding("UTF-8");
    return templateResolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.setEnableSpringELCompiler(true);
    return templateEngine;
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(templateEngine());
    resolver.setCharacterEncoding("UTF-8");
    registry.viewResolver(resolver);
  }

  private static final String PROPERTY_DRIVER = "hibernate.connection.driver_class";
  private static final String PROPERTY_URL = "hibernate.connection.url";
  private static final String PROPERTY_USERNAME = "hibernate.connection.username";
  private static final String PROPERTY_PASSWORD = "hibernate.connection.password";
  private static final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
  private static final String PROPERTY_DIALECT = "hibernate.dialect";

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setUrl(environment.getProperty(PROPERTY_URL));
    ds.setUsername(environment.getProperty(PROPERTY_USERNAME));
    ds.setPassword(environment.getProperty(PROPERTY_PASSWORD));
    ds.setDriverClassName(Objects.requireNonNull(environment.getProperty(PROPERTY_DRIVER)));
    return ds;
  }

  private Properties hibernateProps() {
    Properties properties = new Properties();
    properties.setProperty(PROPERTY_DIALECT, environment.getProperty(PROPERTY_DIALECT));
    properties.setProperty(PROPERTY_SHOW_SQL, environment.getProperty(PROPERTY_SHOW_SQL));
    return properties;
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.mikpolv.intensive28.homework.spring.persistence");
    sessionFactory.setHibernateProperties(hibernateProps());
    return sessionFactory;
  }

  @Bean
  public HibernateTransactionManager transactionManager() {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory().getObject());
    return transactionManager;
  }
}
