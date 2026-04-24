package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Security.JwtAuthFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

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
            
                // 🌍 PUBLIC PAGES (JSP / HOME)
    .antMatchers(
         "/",
        "/home",
        "/login",
        "/signup",
        "/cart",
        "/orders",
        "/css/**",
        "/js/**",
        "/admin",
         "/",
    "/style.css",
    "/**/*.css",
    "/**/*.js",
    "/**/*.png",
    "/**/*.jpg",
    "/**/*.jpeg",

    "/admin-user",
    "/forgot-password",
    "/reset-password",
    "/message",
    "/message/**"
   
    
         
                ).permitAll()
            // 🔓 PUBLIC APIs
                .antMatchers(
                    "/api/users/**",
                    "/api/users/login",
                    "/api/products/allproducts",
                    "/images/**"
                ).permitAll()

           
          
            // Admin APIs
            .antMatchers("/api/products/add").hasAuthority("ADMIN")
            .antMatchers("/api/admin/**").hasAuthority("ADMIN")

            // AUTHENTICATED USER ONLY
            .antMatchers(
                "/message.jsp",   // ✅ Chat page allow
                    "/favicon.ico",
                     "/api/orders/**",
                     "/api/cart/**",
                     "/api/invoice/**",
                     "/api/chat/**"
                    ).authenticated()

  
            // EVERYTHING ELSE
            .anyRequest().authenticated()
        )
         .headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin()) // ✅ important
        )
        
        .logout(logout->logout
            .logoutUrl("/do-logout")
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
        
}
