package com.javarush.javarushfinal.config;

import com.javarush.javarushfinal.service.user.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final MyUserDetailService userDetailsService;

    public SecurityConfig(MyUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/all-parts", "/show-image/{id}", "/{brand}", "/css/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Доступ до /admin тільки для користувачів з роллю ADMIN
                        .requestMatchers("/user/**").hasRole("USER")   // Доступ до /user тільки для користувачів з роллю USER
                        .requestMatchers("/manager/**").hasRole("MANAGER")
                        .anyRequest().authenticated()  // Усі інші запити вимагають автентифікації
                )
                .formLogin(login -> login
                        .loginPage("/show-login-page")
                        .permitAll()
                        .failureUrl("/show-login-page?error=true")
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                ).csrf(csrf -> csrf.ignoringRequestMatchers("/logout"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Використання алгоритму хешування паролів
    }
}