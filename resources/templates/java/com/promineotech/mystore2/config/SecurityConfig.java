package com.promineotech.mystore2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.promineotech.mystore2.service.StudentService;
import com.promineotech.mystore2.service.TeacherService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(StudentService appUserService, TeacherService teacherService, PasswordEncoder passwordEncoder) {
        this.studentService = appUserService;
        this.teacherService = teacherService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/login", "/register", "/forgot-password", "/reset-password", "/create-note").permitAll()
                    .requestMatchers(HttpMethod.GET, "/").permitAll()
                    .requestMatchers("/create-note").permitAll()
                    .requestMatchers("/student/**").hasAuthority("STUDENT")
                    .requestMatchers("/teacher/**").hasAuthority("TEACHER")
                    .anyRequest().authenticated();
        })
                .formLogin(form -> {
                    form.loginPage("/login")
                            .permitAll()
                            .successHandler(authenticationSuccessHandler())
                            .failureHandler((request, response, exception) -> {
                                request.getSession().setAttribute("errorMessage", "Invalid email or password");
                                response.sendRedirect("/login?error=true");
                            });
                })
                .logout(config -> {
                    config.logoutSuccessUrl("/");
                });
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                if (email == null || email.isEmpty()) {
                    log.error("Email cannot be null or empty");
                    throw new UsernameNotFoundException("Email cannot be null or empty");
                }
                log.info("Loading user by username: {}", email);
                UserDetails user = studentService.loadUserByUsername(email);
                if (user == null) {
                    user = teacherService.loadUserByUsername(email);
                }
                if (user == null) {
                    log.error("User not found with email: {}", email);
                    throw new UsernameNotFoundException("Invalid email or password");
                }
                log.info("User found: {}", email);
                return user;
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public CustomAuthenticationSuccessHandler authenticationSuccessHandler() {
        //System.out.println("Creating custom authentication success handler-----------------------");
        return new CustomAuthenticationSuccessHandler();
    }
}
