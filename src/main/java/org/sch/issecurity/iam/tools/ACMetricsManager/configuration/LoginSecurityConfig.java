package org.sch.issecurity.iam.tools.ACMetricsManager.configuration;

/**
 * Created by XiChen on 9/23/2017.
 */

import org.sch.issecurity.iam.tools.ACMetricsManager.service.RestUnauthorizedEntryPoint;
import org.sch.issecurity.iam.tools.ACMetricsManager.utlity.AnalystAuthoritiesPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"org.sch.issecurity.iam.tools.ACMetricsManager.service"})
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler restAuthenticationFailureHandler;

    @Autowired
    private AnalystAuthoritiesPopulator analystAuthoritiesPopulator;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userSearchFilter("(&(objectClass=user)(sAMAccountName={0}))")
                .contextSource(ldapContextSource())
                .ldapAuthoritiesPopulator(analystAuthoritiesPopulator); ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/index.html", "/login.html",
                "/partials/**", "/template/**", "/", "/error/**");
    }

    @Bean
    public BaseLdapPathContextSource ldapContextSource() {
        LdapContextSource bean = new LdapContextSource();
        bean.setUrl("ldap://10.251.204.57:389");
        bean.setBase("OU=Accounts,DC=LPCH,DC=NET");
        bean.setUserDn("CN=IAM2\\, POC2,OU=IT,OU=Accounts,DC=LPCH,DC=NET");
        bean.setPassword("WElcome12!@");
        bean.setPooled(true);
        bean.setReferral("follow");
        return bean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/failure").permitAll()
                .antMatchers("/v2/api-docs").hasAnyAuthority("admin")
                .antMatchers("/users/**").hasAnyAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler)
                .and()
                .formLogin()
                .loginProcessingUrl("/authenticate")
                .successHandler(restAuthenticationSuccessHandler)
                .failureHandler(restAuthenticationFailureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and();

    }
}