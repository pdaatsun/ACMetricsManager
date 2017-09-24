package org.sch.issecurity.iam.tools.ACMetricsManager.configuration;

/**
 * Created by XiChen on 9/23/2017.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userSearchFilter("(&(objectClass=user)(sAMAccountName={0}))")
                .contextSource(ldapContextSource());
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
    /*
    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldap://10.251.204.57:389");
            contextSource.setUserDn("CN=IAM2\\, POC2,OU=IT,OU=Accounts,DC=LPCH,DC=NET");
            contextSource.setPassword("WElcome12!@");
            contextSource.setReferral("follow");
            contextSource.afterPropertiesSet();

            LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> ldapAuthenticationProviderConfigurer = auth.ldapAuthentication();

            ldapAuthenticationProviderConfigurer
                   .userSearchFilter("(&(sAMAccountName={0}))")
                    .userSearchBase("OU=Accounts,DC=LPCH,DC=NET")
                    .contextSource(contextSource);
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication()
                .withUser("journaldev")
                .password("jd@123")
                .authorities("ROLE_USER");
    }
*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.antMatchers("/homePage").access("hasRole('ROLE_USER')")
                .antMatchers("/homePage").authenticated()
                .and()
                .formLogin().loginPage("/loginPage")
                .defaultSuccessUrl("/homePage")
                .failureUrl("/loginPage?error")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/loginPage?logout");

    }
}