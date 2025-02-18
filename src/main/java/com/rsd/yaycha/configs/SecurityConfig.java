// package com.rsd.yaycha.configs;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;


// import jakarta.servlet.http.HttpServletResponse;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Autowired
//     private UserDetailsService userDetailsService;

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//         return http
//                 .csrf(customizer -> customizer.disable())
//                 .authorizeHttpRequests(request -> request
//                     .requestMatchers("users/sign-up","users/login", "admins/seed")
//                     .permitAll()
//                     .requestMatchers("/admins/**").hasRole("ADMIN")
//                     .anyRequest().authenticated())
//                 .exceptionHandling(exception -> exception
//                 .accessDeniedHandler((request, response, accessDeniedException) -> {
//                     response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                 }))
//                 .httpBasic(Customizer.withDefaults())
//                 .formLogin(formLogin -> formLogin.disable())
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .build();
        
//         // http.formLogin(Customizer.withDefaults());
//     }

//     @Bean
//     public AuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//         provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
//         provider.setUserDetailsService(userDetailsService);;
//         return provider;
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

//         return config.getAuthenticationManager();
//     }

// }
