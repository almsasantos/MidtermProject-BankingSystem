package com.ironhack.MidtermProject.security;

import com.ironhack.MidtermProject.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                // --- USER CONTROLLER SECURITY ---
                .mvcMatchers("/users").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/users/{id}").hasAuthority("ROLE_ADMIN")
                // endregion

                // --- THIRD PARTY CONTROLLER SECURITY ---
                .mvcMatchers(HttpMethod.PATCH,"/debit-transaction").hasAuthority("ROLE_THIRDPARTY")
                .mvcMatchers(HttpMethod.PATCH,"/credit-transaction").hasAuthority("ROLE_THIRDPARTY")
                .mvcMatchers(HttpMethod.PATCH,"/thirdparties/login").hasAuthority("ROLE_THIRDPARTY")
                .mvcMatchers(HttpMethod.PATCH,"/thirdparties/logout").hasAuthority("ROLE_THIRDPARTY")
                .mvcMatchers("/thirdparties").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/thirdparties/{id}").hasAuthority("ROLE_ADMIN")
                // endregion

                // --- ADMIN CONTROLLER SECURITY ---
                .mvcMatchers("/admins").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/admins/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/admins-name/{name}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/admins/unfreeze-account").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/admin/balance").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST,"/admin").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST,"/third-party").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/admins/login").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/admins/logout").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST,"/account/savings").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST,"/account/credit-card").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST,"/account/depending-age").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/debit-balance").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/credit-balance").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/user").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/account").hasAuthority("ROLE_ADMIN")
                // endregion

                // --- ACCOUNT HOLDER CONTROLLER SECURITY ---
                .mvcMatchers("/accountholders/balance").hasAuthority("ROLE_ACCOUNTHOLDER")
                .mvcMatchers(HttpMethod.PATCH,"/accountholders/login").hasAuthority("ROLE_ACCOUNTHOLDER")
                .mvcMatchers(HttpMethod.PATCH,"/accountholders/logout").hasAuthority("ROLE_ACCOUNTHOLDER")
                .mvcMatchers(HttpMethod.PATCH,"/transference").hasAuthority("ROLE_ACCOUNTHOLDER")
                .mvcMatchers(HttpMethod.POST,"/accountholder").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/accountholders").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/accountholders/{id}").hasAuthority("ROLE_ADMIN")
                // endregion

                // --- STUDENT CHECKING CONTROLLER SECURITY ---
                .mvcMatchers("/student-checking-accounts").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/student-checking-accounts/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/student-checking-accounts/status").hasAuthority("ROLE_ADMIN")
                // endregion

                // --- SAVING CONTROLLER SECURITY ---
                .mvcMatchers("/savings").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/savings/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/savings/status").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/savings/minimum-balance").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/savings/interest-rate").hasAuthority("ROLE_ADMIN")
                // endregion

                // --- CREDIT CARD CONTROLLER SECURITY ---
                .mvcMatchers("/credit-cards").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/credit-cards/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/credit-cards/credit-limit").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/credit-cards/interest-rate").hasAuthority("ROLE_ADMIN")
                // endregion

                // --- CHECKING CONTROLLER SECURITY ---
                .mvcMatchers("/checkings").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/checkings/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/checkings/status").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/checkings/minimum-balance").hasAuthority("ROLE_ADMIN")
                .mvcMatchers("/checkings/monthly-maintenance-fee").hasAuthority("ROLE_ADMIN")
                // endregion

                // --- ACCOUNT CONTROLLER SECURITY ---
                .mvcMatchers("/accounts").hasAuthority("ROLE_ADMIN")
                // endregion
                .and().requestCache().requestCache(new NullRequestCache()).and().httpBasic().and().cors().and().csrf().disable();

    }
}