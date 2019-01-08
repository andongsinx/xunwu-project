package com.focus.config;

import com.focus.security.AuthProvider;
import com.focus.security.LoginAuthFailHandler;
import com.focus.security.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * @Description：
 * @Author：shadow
 * @Date：ceate in 15:39 2018/12/31
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //资源访问权限
        http.authorizeRequests().antMatchers("/admin/login").permitAll()//管理员登陆入口
                .antMatchers("/static/**").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().loginProcessingUrl("/login").failureHandler(authFailHandler()).and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/logout/page")
                // .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl("/logout/page")
                .deleteCookies("JSESSIONID").invalidateHttpSession(true).and()
                .exceptionHandling().authenticationEntryPoint(loginUrlEntryPoint())
                .accessDeniedPage("/403").and()
                .headers().frameOptions().disable().and()
                .csrf().disable();
    }


    /**
     * 自定义认证策略
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider()).eraseCredentials(true);
    }

    @Bean
    public AuthProvider authProvider() {
        return new AuthProvider();
    }

    @Bean
    public LoginUrlEntryPoint loginUrlEntryPoint() {
        return new LoginUrlEntryPoint("/user/login");
    }

    @Bean
    public LoginAuthFailHandler authFailHandler() {
        return new LoginAuthFailHandler(loginUrlEntryPoint());
    }
}
