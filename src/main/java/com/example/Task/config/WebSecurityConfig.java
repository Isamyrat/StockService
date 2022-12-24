package com.example.Task.config;

import com.example.Task.jwt.AuthEntryPointJwt;
import com.example.Task.jwt.JwtAuthFilter;
import com.example.Task.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final UserServiceImpl userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().disable()
            .authorizeHttpRequests((auths) -> {
                                       try {
                                           auths
                                               .requestMatchers("/", "/user/registration")
                                               .permitAll()
                                               .requestMatchers("/user/**").hasRole("ADMIN")
                                               .anyRequest().authenticated()
                                               .and()
                                               .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/").failureForwardUrl("/").failureUrl("/")
                                               .and()
                                               .logout().permitAll().logoutSuccessUrl("/");
                                       } catch (Exception e) {
                                           e.printStackTrace();
                                       }
                                   }
                                  )
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
