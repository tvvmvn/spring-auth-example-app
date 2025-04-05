package net.enjoy.springboot.registrationlogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
  
  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
    // to access h2 console
    http.headers().frameOptions().disable();

    http
        .csrf().disable()
        // Access control to Urls
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/h2-ui/**", "/register/**").permitAll()
            .anyRequest().authenticated()
        )
        // Login (Form login)
        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/", true)
            .permitAll())
        // Logout
        .logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .permitAll());
            
    return http.build();
  }
  
  // Configure authentication manager.
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    
    // AuthenticationManager (responsible for login process)
    auth
        .userDetailsService(userDetailsService) // to get an user from login email 
        .passwordEncoder(passwordEncoder()); // to encode login password to compare with original
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}