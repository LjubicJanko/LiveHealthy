package live.healthy.config;

import live.healthy.security.auth.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfigurer extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private TokenAuthenticationFilter securityFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                securityFilter, BasicAuthenticationFilter.class
        );
    }
}