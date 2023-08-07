package sh.radical.order.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public  class WebSecurity  {
    
    String[] AUTH_WHITELIST = { "/v3/api-docs/**","/actuator/**","/swagger-ui/**","/swagger-ui.html","/health" };
    
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception
    {
        http.cors().and()
        .csrf().disable().authorizeHttpRequests()
        .antMatchers(AUTH_WHITELIST).permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin();
        
        return http.build();
    }
    
}