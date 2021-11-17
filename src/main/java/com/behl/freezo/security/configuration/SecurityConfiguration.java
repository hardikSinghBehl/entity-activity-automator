package com.behl.freezo.security.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.behl.freezo.security.CustomUserDetailService;
import com.behl.freezo.security.constant.ApiPathExclusion;
import com.behl.freezo.security.filter.JwtAuthenticationFilter;
import com.behl.freezo.security.filter.LoggedUserDetailStorageFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetialService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LoggedUserDetailStorageFilter loggedUserDetailStorageFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetialService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(List.of(ApiPathExclusion.values()).stream().map(apiPath -> apiPath.getPath())
                        .toArray(String[]::new))
                .permitAll().anyRequest().authenticated().and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(loggedUserDetailStorageFilter, JwtAuthenticationFilter.class);
    }

}