package com.gl.StudentRegistration.security;

import com.gl.StudentRegistration.entity.User;
import com.gl.StudentRegistration.service.UserDetailServiceImpl;
import com.gl.StudentRegistration.service.UserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
		return new UserDetailServiceImpl();
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
		
	}
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
			auth.authenticationProvider(authenticationProvider());
		}
     @Override
    	protected void configure(HttpSecurity http) throws Exception {
    		http.authorizeRequests()
    		.antMatchers("/","/students/save","/students/showFormForAdd","/students/403").hasAnyAuthority("USER","ADMIN")
    		.antMatchers("/students/showFormForUpdate","/students/delete").hasAuthority("ADMIN")
    		.anyRequest().authenticated()
    		.and()
    		.formLogin().loginProcessingUrl("/login").successForwardUrl("/students/list").permitAll()
    		.and()
    		.logout().logoutSuccessUrl("/login").permitAll()
    		.and()
    		.exceptionHandling().accessDeniedPage("/students/403")
    		.and()
    		.cors().and().csrf().disable();
    		
    	}

}
