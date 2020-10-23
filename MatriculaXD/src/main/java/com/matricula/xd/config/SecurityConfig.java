package com.matricula.xd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
        build
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
	/*	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication()
			.withUser("admin").password("{noop}123").roles("ADMIN", "USER")
			.and()
			.withUser("user").password("{noop}123").roles("USER");		
	}
	*/
	@Override
	protected void configure(HttpSecurity http) throws Exception{		
		http.authorizeRequests()
				.antMatchers("/api/**").authenticated()
				.antMatchers("/matriculas/**").authenticated()
				.antMatchers("/matriculas/nuevo/**").authenticated()
				.antMatchers("/usuarios/**").authenticated()
				.antMatchers("/alumnos/**").authenticated()
				.antMatchers("/docentes/**").authenticated()
				.antMatchers("/cursos/**").authenticated()
				.antMatchers("/").authenticated()
		//.anyRequest().authenticated()		
				.and()
				.formLogin().loginPage("/login")
				.and()
				.exceptionHandling().accessDeniedPage("/error/403")
				;
				
		http.csrf().disable();
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/h2-console/**");
	   
	}

}
