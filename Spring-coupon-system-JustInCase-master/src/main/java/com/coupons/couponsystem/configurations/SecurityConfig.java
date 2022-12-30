package com.coupons.couponsystem.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf().disable();

      http.authorizeHttpRequests()
              .mvcMatchers("/api/admin/**").hasAuthority("Administrator")
              .mvcMatchers("/api/company/**").hasAuthority("Company")
              .mvcMatchers("/api/customer/**").hasAuthority("Customer")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();

      return http.build();
    }

    //http.authorizeHttpRequests()
    //              .requestMatchers("/api/admin/**").hasAuthority("Administrator")

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  NoOpPasswordEncoder.getInstance();
    }



}

