package com.luv2code.springsecurity.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

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
    @Autowired
    private Environment env;

    // setup a logger for diagnostics
    private Logger logger = Logger.getLogger(getClass().getName());

    // define a bean for view resolver, by this view resolve spring will know about the directory
    // to look for views in which directory it should look for views
    @Bean
    public ViewResolver viewResolver(){

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // set pre-fix on view resolver
        viewResolver.setPrefix("/WEB-INF/view/");
        // set suffix on view resolver
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
