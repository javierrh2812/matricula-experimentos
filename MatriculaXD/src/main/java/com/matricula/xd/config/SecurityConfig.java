package com.matricula.xd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication()
			.withUser("admin").password("{noop}123").roles("ADMIN", "USER")
			.and()
			.withUser("user").password("{noop}123").roles("USER");		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{		
		http.authorizeRequests()
				.antMatchers("/matriculas/nuevo/**").hasRole("ADMIN")
				.antMatchers("/").hasAnyRole("ADMIN", "USER")
		//.anyRequest().authenticated()		
		.and()
				.formLogin().loginPage("/login")
				.and()
				.exceptionHandling().accessDeniedPage("/error/403")
				;
		
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/h2-console/**");
	   
	}

}