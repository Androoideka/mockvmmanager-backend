package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services.UserService;

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
                .antMatchers("/user/create*").hasAuthority(PermissionUtil.REPRESENTATIONS[0])
                .antMatchers("/user/edit/*").hasAuthority(PermissionUtil.REPRESENTATIONS[2])
                .antMatchers("/user/delete/*").hasAuthority(PermissionUtil.REPRESENTATIONS[3])
                .antMatchers("/user/logout*").authenticated()
                .antMatchers("/user/login*").permitAll()
                .antMatchers("/user/**/*").hasAuthority(PermissionUtil.REPRESENTATIONS[1])
                .antMatchers("/machine/search*").hasAuthority(PermissionUtil.REPRESENTATIONS[4])
                .antMatchers("/machine/start/*").hasAuthority(PermissionUtil.REPRESENTATIONS[5])
                .antMatchers("/machine/stop/*").hasAuthority(PermissionUtil.REPRESENTATIONS[6])
                .antMatchers("/machine/restart/*").hasAuthority(PermissionUtil.REPRESENTATIONS[7])
                .antMatchers("/machine/create/*").hasAuthority(PermissionUtil.REPRESENTATIONS[8])
                .antMatchers("/machine/destroy/*").hasAuthority(PermissionUtil.REPRESENTATIONS[9])
                .antMatchers("/error/list*").authenticated()
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
}
