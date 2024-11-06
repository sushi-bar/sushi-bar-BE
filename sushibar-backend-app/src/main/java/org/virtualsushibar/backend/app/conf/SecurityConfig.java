package org.virtualsushibar.backend.app.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  @Profile("!local")
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authz) -> authz
            .anyRequest().authenticated()
        ).oauth2Login();

    return http.build();
  }

  /**
   * This disabled the authentication/authorization when the profile local is active
   */
  @Bean
  @Profile("local")
  public SecurityFilterChain filterChain_disabled(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authz) -> authz
            .anyRequest().permitAll()  // Allow all requests without authentication
        )
        .csrf().disable()              // Optionally disable CSRF for non-production environments
        .oauth2Login().disable();      // Disable OAuth2 login if not needed locally
    return http.build();
  }

}
