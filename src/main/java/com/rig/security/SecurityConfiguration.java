package com.rig.security;

import com.rig.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthService authService;
    private final JwtProperties jwtProperties;
    private final ResponseHelper responseHelper;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CorsProperties corsProperties;

    @Override
    public void configure(final HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .anyRequest().authenticated().and().httpBasic().and()
                .addFilterBefore(new CorsFilter(corsProperties), ChannelProcessingFilter.class)
                .addFilterBefore(
                        new LoginFilter(
                                "/api/login",
                                authService,
                                jwtProperties,
                                responseHelper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                        new StatelessAuthenticationFilter(
                                authService,
                                responseHelper), UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
    }
}
