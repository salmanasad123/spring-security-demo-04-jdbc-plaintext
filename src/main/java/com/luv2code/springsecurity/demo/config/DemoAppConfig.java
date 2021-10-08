package com.luv2code.springsecurity.demo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Logger;

// this is our configuration class and this replaces our spring-mvc-demo-servlet.xml
// provide support for annotation-driven
// packages to scan took look for controllers and support classes replaces @Component-scan in xml
// read the properties file using annotation @PropertySource, resources is the standard maven directory
// for web apps and any resources are automatically copied to the classpath during maven build as a result
// we can make use of that file
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.luv2code.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig {

    // setup a variable to hold the properties read from our properties file. this environment will hold the
    // data read from properties file, so we read the information and place in this variable to read from it
    // this is autowired via @PropertySource
    @Autowired
    private Environment env;

    // setup a logger for diagnostics
    private Logger logger = Logger.getLogger(getClass().getName());

    // define a bean for view resolver, by this view resolve spring will know about the directory
    // to look for views in which directory it should look for views
    @Bean
    public ViewResolver viewResolver() {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // set pre-fix on view resolver
        viewResolver.setPrefix("/WEB-INF/view/");
        // set suffix on view resolver
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    // define a bean for our security data source, set data for spring DataSource
    @Bean
    public DataSource securityDataSource() {
        // create connection pool
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

        // set the jdbc driver class
        try {
            // read the database config from our properties file
            securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        // log connection properties, logging the info from properties file
        logger.info(env.getProperty(" >>> jdbcUrl :" + env.getProperty("jdbc.url")));
        logger.info(env.getProperty(" >>> jdbcUsername: " + env.getProperty("jdbc.user")));

        // set the database connection properties (jdbc url, username and password)
        securityDataSource.setJdbcUrl(env.getProperty("jdbcUrl"));
        securityDataSource.setJdbcUrl(env.getProperty("username"));
        securityDataSource.setJdbcUrl(env.getProperty("password"));

        // set the connection pool properties, read databaseConfigs
        securityDataSource.setInitialPoolSize(getIntProperty(env.getProperty("connection.pool.initialPoolSize")));
        securityDataSource.setMinPoolSize(getIntProperty(env.getProperty("connection.pool.minPoolSize")));
        securityDataSource.setMaxPoolSize(getIntProperty(env.getProperty("connection.pool.maxPoolSize")));
        securityDataSource.setMaxIdleTime(getIntProperty(env.getProperty("connection.pool.maxIdleTime")));

        return securityDataSource;
    }

    // need a helper method to read environment properties and convert it to int, because when you read
    // property it always comes as a string
    public int getIntProperty(String propertyName) {

        String propVal = env.getProperty(propertyName);

        // now convert the string to int
        int intPropVal = Integer.parseInt(propVal);

        return intPropVal;
    }
}
