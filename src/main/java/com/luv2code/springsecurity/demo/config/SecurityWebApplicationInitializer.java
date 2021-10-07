package com.luv2code.springsecurity.demo.config;


import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// use this class to enable spring security filters
// extend this class from AbstractSecurityWebApplicationInitializer and thats all we don't need to do
// anything else
// this is a special class to register spring security filters
// we should extend this class otherwise our security filters will not be registered
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}
