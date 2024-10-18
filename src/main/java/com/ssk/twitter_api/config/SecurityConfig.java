package com.ssk.twitter_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;  // Service to load user-specific data for authentication
    private final JwtFilter jwtFilter;  // Custom JWT filter for token authentication

    // Constructor to inject UserDetailsService and JwtFilter
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);  // 12 log rounds = 4096 iterations
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);  // Set UserDetailsService for retrieving user data
        provider.setPasswordEncoder(bCryptPasswordEncoder());  // Set BCrypt as the password encoder
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF (Cross-Site Request Forgery) as the API will be stateless using JWT
        http.csrf(AbstractHttpConfigurer::disable);

        // Configure URL access rules
        http.authorizeHttpRequests(req ->
                req
                        .requestMatchers("/register", "/login").permitAll()  // Allow access to register and login endpoints
                        // .requestMatchers("/admin").hasRole("USER")
                        // .requestMatchers("/manager").hasRole("MANAGER")
                        // .requestMatchers("/employee").hasRole("EMPLOYEE")
                        .anyRequest().authenticated()  // All other endpoints require authentication
        );

        // Session management configuration to be stateless (JWT tokens are stateless)
        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add the custom JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the security filter chain
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
