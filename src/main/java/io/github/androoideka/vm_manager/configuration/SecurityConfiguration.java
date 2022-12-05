package io.github.androoideka.vm_manager.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.github.androoideka.vm_manager.services.UserService;

@Configuration
@EnableGlobalAuthentication
public class SecurityConfiguration {

    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfiguration(UserService userService, JwtFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/index.html").permitAll()
                        .requestMatchers("/*.js*").permitAll()
                        .requestMatchers("/*.css").permitAll()
                        .requestMatchers("/assets/img/*.png").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
                        .requestMatchers("/user/login*").permitAll()
                        .requestMatchers("/user/logout*").authenticated()
                        .requestMatchers("/user/create*").hasAuthority(PermUtil.AUTHORITIES.get(0).getAuthority())
                        .requestMatchers("/user/edit/*").hasAuthority(PermUtil.AUTHORITIES.get(2).getAuthority())
                        .requestMatchers("/user/delete/*").hasAuthority(PermUtil.AUTHORITIES.get(3).getAuthority())
                        .requestMatchers("/user/**").hasAuthority(PermUtil.AUTHORITIES.get(1).getAuthority())
                        .requestMatchers("/machine/search*").hasAuthority(PermUtil.AUTHORITIES.get(4).getAuthority())
                        .requestMatchers("/machine/start/*").hasAuthority(PermUtil.AUTHORITIES.get(5).getAuthority())
                        .requestMatchers("/machine/stop/*").hasAuthority(PermUtil.AUTHORITIES.get(6).getAuthority())
                        .requestMatchers("/machine/restart/*").hasAuthority(PermUtil.AUTHORITIES.get(7).getAuthority())
                        .requestMatchers("/machine/create/*").hasAuthority(PermUtil.AUTHORITIES.get(8).getAuthority())
                        .requestMatchers("/machine/destroy/*").hasAuthority(PermUtil.AUTHORITIES.get(9).getAuthority())
                        .anyRequest().authenticated())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Profile("dev")
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200", "http://127.0.0.1:4200"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
