package ng.tellerassist.tellerassist.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity()
public class TellerAssistSecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    @Qualifier("tellerAssistUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("**/**").permitAll().
                and().
                userDetailsService(this.userDetailsService).
                formLogin().
                loginPage("/").
                permitAll().
                loginProcessingUrl("/login").
                usernameParameter("tellerAssistUsername").
                passwordParameter("tellerAssistPassword").
                defaultSuccessUrl("/").and().
                logout().
                logoutUrl("/logout").
                logoutSuccessUrl("/logoutForm").and().
                exceptionHandling();

    }


}
