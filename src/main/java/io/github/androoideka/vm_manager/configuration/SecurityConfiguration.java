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
                        .requestMatchers("/user/create*").hasAuthority(PermissionUtil.REPRESENTATIONS[0])
                        .requestMatchers("/user/edit/*").hasAuthority(PermissionUtil.REPRESENTATIONS[2])
                        .requestMatchers("/user/delete/*").hasAuthority(PermissionUtil.REPRESENTATIONS[3])
                        .requestMatchers("/user/**").hasAuthority(PermissionUtil.REPRESENTATIONS[1])
                        .requestMatchers("/machine/search*").hasAuthority(PermissionUtil.REPRESENTATIONS[4])
                        .requestMatchers("/machine/start/*").hasAuthority(PermissionUtil.REPRESENTATIONS[5])
                        .requestMatchers("/machine/stop/*").hasAuthority(PermissionUtil.REPRESENTATIONS[6])
                        .requestMatchers("/machine/restart/*").hasAuthority(PermissionUtil.REPRESENTATIONS[7])
                        .requestMatchers("/machine/create/*").hasAuthority(PermissionUtil.REPRESENTATIONS[8])
                        .requestMatchers("/machine/destroy/*").hasAuthority(PermissionUtil.REPRESENTATIONS[9])
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
