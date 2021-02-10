package org.vacation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.vacation.filters.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication()
			.userDnPatterns("uid={0},ou=people")
			.groupSearchBase("ou=groups")
			.contextSource()
			.url("ldap://localhost:8389/dc=company,dc=org")
			.and()
			.passwordCompare()
			.passwordAttribute("userPassword");
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.headers().frameOptions().disable(); // for h2 gui after logged in.
		httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll()
				.and()
				.authorizeRequests().antMatchers("/h2-console/**").permitAll()
				.and()
				/**
				 * TODO fix role mapping.
				 */
				.authorizeRequests().antMatchers("/vacation/**").hasRole("USER")
				.and()
				.authorizeRequests().antMatchers("/notification/**").hasRole("USER")
				.and()
				.authorizeRequests().antMatchers("/administration/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.exceptionHandling()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
}
