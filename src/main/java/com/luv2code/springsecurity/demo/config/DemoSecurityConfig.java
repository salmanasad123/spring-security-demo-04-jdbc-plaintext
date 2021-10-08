package com.luv2code.springsecurity.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    // add a reference to our security data source because we want Spring Security to use
    // our data source
    @Autowired
    private DataSource securityDataSource;

    // override this method to setup in memory authentication using authentication manager builder
    // we setup in memory authentication manager
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // now we will use jdbc authentication so remove all the code that creates users in memory
        // tell spring security to use jdbc authentication with our data source,
        // that data source points to those database that we created and populated with users and roles
        auth.jdbcAuthentication().dataSource(securityDataSource);
    }

    // override configure the method that takes http security to reference our custom login form
    // configure security of web-paths in application login and logout
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // any request coming in to our app must be authenticated
        //  .antMatchers("/").hasRole("EMPLOYEE")  --> only user with role employee can access "/"
        // .antMatchers("/leaders/**").hasRole("MANAGER") --> only user with role manager can access /leaders/**  (** means any)

        http.authorizeRequests()
                .antMatchers("/").hasRole("EMPLOYEE")
                .antMatchers("/leaders/**").hasRole("MANAGERS")
                .antMatchers("/systems/**").hasRole("ADMIN")
                .and()
                    .formLogin()
                    .loginPage("/showMyLoginPage")
                    .loginProcessingUrl("/authenticateTheUser")  // this is where spring will submit data for authentication
                    .permitAll()
                .and()
                    .logout()// add support for logout
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied");  // request mapping for access denied
    }
}
