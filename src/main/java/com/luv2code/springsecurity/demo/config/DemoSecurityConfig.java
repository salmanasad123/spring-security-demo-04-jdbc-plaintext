package com.luv2code.springsecurity.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    // override this method to setup in memory authentication using authentication manager builder
    // we setup in memory authentication manager
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add our users for in memory authentication

        User.UserBuilder users = User.withDefaultPasswordEncoder();

        // creating in memory users, we'll replace with database
        auth.inMemoryAuthentication()
                .withUser(users.username("john").password("test123").roles("EMPLOYEE"));
        auth.inMemoryAuthentication()
                .withUser(users.username("mary").password("test123").roles("EMPLOYEE", "MANAGERS"));
        auth.inMemoryAuthentication()
                .withUser(users.username("susan").password("test123").roles("EMPLOYEE","ADMIN"));
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
