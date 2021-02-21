package fr.lyes.gds.Security;

import fr.lyes.gds.Security.filter.AuthTokenFilter;
import fr.lyes.gds.Security.jwt.AuthEntryPointJwt;
import fr.lyes.gds.Security.service.UserDetailServicesImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@ComponentScan
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailServicesImpl userDetailsService;
    @Primary
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private static final Logger logger = LoggerFactory.getLogger(AppSecurityConfig.class);


    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Primary
   @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/gds/auth/**").permitAll();
        http.authorizeRequests().antMatchers("/gds/groupes/**").permitAll();
        http.authorizeRequests().antMatchers("/gds/users/**").permitAll();
        //http.authorizeRequests().antMatchers("/ManagingUsers/**").hasAuthority("Controllers");
        //http.authorizeRequests().antMatchers(HttpMethod.GET, "/listproduits/").hasAuthority("Controllers");
       // http.authorizeRequests().antMatchers(HttpMethod.POST, "/listproduits/").hasAuthority("Controllers");
      //http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/listproduits").hasAuthority("User");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
