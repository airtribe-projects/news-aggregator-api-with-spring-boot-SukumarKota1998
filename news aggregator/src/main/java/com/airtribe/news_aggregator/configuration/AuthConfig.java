package com.airtribe.news_aggregator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableMethodSecurity
public class AuthConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            httpSecurity.csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                            .requestMatchers(
                                    "/register",
                                    "/logins",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/swagger-resources/**",
                                    "/webjars/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                    .formLogin(formLogin -> formLogin.defaultSuccessUrl("/hello", true).permitAll())
                    .addFilterBefore(new AuthJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        } catch (Exception e) {
            throw new RuntimeException("Error configuring security filter chain", e);
        }
    }
}
