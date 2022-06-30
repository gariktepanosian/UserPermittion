package com.epam.config;


import com.epam.model.Role;
import com.epam.security.JwtAuthenticationEntryPoint;
import com.epam.security.JwtAuthenticationTokenFilter;
import com.epam.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String ADMIN = Role.ADMIN.name();
    private final String MANAGER = Role.MANAGER.name();
    private final String USER = Role.USER.name();

    @Qualifier("currentUserDetailServiceImpl")
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHeader;

    @Value("${path.admin}")
    private String ADMIN_PATH;
    @Value("${path.manager}")
    private String MANAGER_PATH;
    @Value("${path.user}")
    private String USER_PATH;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint unauthorizedHeader) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHeader = unauthorizedHeader;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHeader)
                .and()
                .authorizeRequests()
                .antMatchers(ADMIN_PATH)
                .hasRole(ADMIN)
                .antMatchers(MANAGER_PATH)
                .hasAnyRole(ADMIN, MANAGER)
                .antMatchers(USER_PATH)
                .hasAnyRole(ADMIN, MANAGER, USER)
                .anyRequest()
                .permitAll();
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        http
                .headers()
                .cacheControl();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
