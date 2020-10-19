package com.summer.security.config;

import com.summer.security.interceptor.MyAccessDecisionManager;
import com.summer.security.interceptor.MyFilterInvocationSecurityMetadataSource;
import com.summer.security.interceptor.handler.MyAccessDeniedHandler;
import com.summer.security.interceptor.handler.MyAuthenticationFailureHandler;
import com.summer.security.interceptor.handler.MyAuthenticationSuccessHandler;
import com.summer.security.interceptor.handler.MyLogoutSuccessHandler;
import com.summer.security.service.impl.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsUtils;

/**
 *spring-security权限管理的核心配置
 */
@Configuration //用于定义配置类，可替换xml配置文件被注解的类内部包含有一个或多个被@Bean注解的方法，
               // 这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
               // 并用于构建bean定义，初始化Spring容器。
@EnableWebSecurity //这是一个组合类 可以点进去看一下
                    //1: 加载了WebSecurityConfiguration配置类, 配置安全认证策略。
                   //2: 加载了AuthenticationConfiguration, 配置了认证信息。
@EnableGlobalMethodSecurity(prePostEnabled = true)// Spring Security默认是禁用注解的，要想开启注解，
// 需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解，
// 并在该类中将AuthenticationManager定义为Bean。
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;// 决策决策器

    @Autowired
    private MyFilterInvocationSecurityMetadataSource filterMetadataSource;//权限过滤器

    /**
     *配置userDetails的数据源，密码加密格式
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService)
                .passwordEncoder(new BCryptPasswordEncoder());// 实现自定义登录校验
    }

    /**
     *
     *配置放行的资源
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/index.html", "/static/**", "/login_p", "/favicon.ico")
                // 给 swagger 放行；不需要权限能访问的资源
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/images/**", "/webjars/**", "/v2/api-docs", "/configuration/ui", "/configuration/security");

    }

    /**
     *
     * 拦截配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 使其支持跨域
                .requestMatchers(CorsUtils:: isPreFlightRequest).permitAll()
                // 其他路径需要授权访问
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterMetadataSource);
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin().loginPage("/login_p").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureHandler(new MyAuthenticationFailureHandler())
                .successHandler(new MyAuthenticationSuccessHandler())
                .permitAll()
                .and()
                // 退出登录后的默认路径
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .permitAll()
                .and()
                .csrf().disable() //关闭csrf
                .exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());
    }



}
