package com.project.todoproject.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig  {
    

    private static final String[] PUBLIC_MATCHERS = {
        "/"
    };

public static final String[] PUBLIC_MATCHERS_POST = {
    "/user",
    "/login"
};

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.cors(null).csrf().disable();
    http.authorizeHttpRequests(null);
        .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll();
        .antMatchers(PUBLIC_MATCHERS).permitAll();
        .anyRequest().authenticated();
        
    http.sessionManagement(null).sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    return http.build();


}


}


