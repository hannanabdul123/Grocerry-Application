package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Security.JwtAuthFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    public SecurityConfig(JwtAuthFilter jwtAuthFilter){
         this.jwtAuthFilter = jwtAuthFilter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http 
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
                
            // 🔓 PUBLIC APIs
                .antMatchers(
                    "/api/users",
                    "/api/users/login",
                    "/api/products/allproducts"
                ).permitAll()

            // ADMIN ONLY
            .antMatchers("/api/products/add").hasRole("ADMIN")

            // AUTHENTICATED USER ONLY
            .antMatchers("/api/orders/**","/api/cart/**").authenticated()

            // EVERYTHING ELSE
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
        
}
