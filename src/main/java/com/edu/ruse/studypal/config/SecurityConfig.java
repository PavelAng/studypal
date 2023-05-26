package com.edu.ruse.studypal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.SecurityFilterChainRegistration;
import org.springframework.security.config.web.servlet.HttpSecurityDsl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.SecurityFilterChainFactory;

@EnableWebSecurity
public class SecurityConfig implements SecurityFilterChainFactory {

    @Bean
    public SecurityFilterChain createFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/login").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll());

        return http.build();
    }

    @Override
    public SecurityFilterChain createFilterChain(SecurityFilterChainRegistration registration) throws Exception {
        return createFilterChain(registration.getHttp());
    }
}
