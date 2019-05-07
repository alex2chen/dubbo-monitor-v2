package com.github.middleware.dubbo.monitor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment env;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(env.getProperty("root.username")).password(env.getProperty("root.password")).roles("USER")
                .and().withUser(env.getProperty("user.username")).password(env.getProperty("user.password")).roles("USER", "ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭CSRF支持
        //http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
        http.csrf().disable().authorizeRequests().antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/registry/op/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                //.and().formLogin()
                .and().httpBasic()
                .and().logout().deleteCookies("remove").invalidateHttpSession(false).logoutUrl("/login?logout").logoutSuccessUrl("/");
    }
}
