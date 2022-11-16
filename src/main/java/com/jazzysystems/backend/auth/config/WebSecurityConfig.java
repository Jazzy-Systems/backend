package com.jazzysystems.backend.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jazzysystems.backend.auth.jwt.AuthEntryPointJwt;
import com.jazzysystems.backend.auth.jwt.AuthTokenFilter;
import com.jazzysystems.backend.user.service.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // TODO create list urls for each role, also permit by http method type
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**", "/api/v1/role/**",
                        "api/v1/typeCommunique", "api/v1/person/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "api/v1/communique/").hasAnyRole("ADMIN", "RESIDENT", "GUARD")
                .antMatchers("api/v1/communique/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/apartment/all").hasRole("GUARD")
                .antMatchers("/api/v1/apartment/**").hasRole("ADMIN")
                .antMatchers("/api/v1/apartment/all").hasRole("GUARD")
                .antMatchers("/api/v1/person/apart/**").hasRole("GUARD")
                .antMatchers("/api/v1/company/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/request/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/request/**").hasAnyRole("ADMIN", "RESIDENT")
                .antMatchers(HttpMethod.POST, "/api/v1/request/**").hasAnyRole("RESIDENT")
                .antMatchers(HttpMethod.GET, "/api/v1/pack/mypacks").hasAnyRole("RESIDENT")
                .antMatchers(HttpMethod.DELETE, "/api/v1/pack/**").denyAll()
                .antMatchers("/api/v1/pack/**").hasRole("GUARD")
                .antMatchers("/js/**", "/images/**").permitAll()
                .anyRequest().authenticated();
        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
