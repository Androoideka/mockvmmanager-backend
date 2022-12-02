package io.github.androoideka.vm_manager.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.github.androoideka.vm_manager.services.UserService;

@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SpringSecurityConfiguration(UserService userService, JwtFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/*.js*").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/assets/img/*.png").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/user/login*").permitAll()
                .antMatchers("/user/logout*").authenticated()
                .antMatchers("/user/create*").hasAuthority(PermissionUtil.REPRESENTATIONS[0])
                .antMatchers("/user/edit/*").hasAuthority(PermissionUtil.REPRESENTATIONS[2])
                .antMatchers("/user/delete/*").hasAuthority(PermissionUtil.REPRESENTATIONS[3])
                .antMatchers("/user/**/*").hasAuthority(PermissionUtil.REPRESENTATIONS[1])
                .antMatchers("/machine/search*").hasAuthority(PermissionUtil.REPRESENTATIONS[4])
                .antMatchers("/machine/start/*").hasAuthority(PermissionUtil.REPRESENTATIONS[5])
                .antMatchers("/machine/stop/*").hasAuthority(PermissionUtil.REPRESENTATIONS[6])
                .antMatchers("/machine/restart/*").hasAuthority(PermissionUtil.REPRESENTATIONS[7])
                .antMatchers("/machine/create/*").hasAuthority(PermissionUtil.REPRESENTATIONS[8])
                .antMatchers("/machine/destroy/*").hasAuthority(PermissionUtil.REPRESENTATIONS[9])
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Profile("dev")
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
