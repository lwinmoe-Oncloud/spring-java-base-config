package com.hello.configs;


import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity

public class SecurityConfig{
@Autowired
DataSource dataSource;
	
	@Bean
	SecurityFilterChain homeFilters(HttpSecurity http) throws Exception {
		http.securityMatcher("/").authorizeHttpRequests(request->{
			request.anyRequest().permitAll();
		});
		return http.build();
	}
	@Bean
	SecurityFilterChain ResourceFilter(HttpSecurity http) throws Exception {
		http.securityMatcher("/resources/**")
		.authorizeHttpRequests(request -> {
			request.anyRequest().permitAll();
		});
	return http.build();
	}
	@Bean
	SecurityFilterChain homeFilter(HttpSecurity http)throws Exception{
	
//		http.authorizeHttpRequests(request->{
			
		
//			request.requestMatchers("/admin/**").hasAuthority("Admin");
//		request.requestMatchers("/member/**").hasAnyAuthority("Admin","Member");
http.authorizeHttpRequests(request->{
	request.requestMatchers("/admin/**").hasAuthority("Admin");
	request.requestMatchers("/member/**").hasAuthority("Member");
});


//		});
//	
		http.formLogin(Customizer.withDefaults());
		http.logout(Customizer.withDefaults());
		 
		return http.build();
	}
//	@Bean
//	SecurityFilterChain memberFilter(HttpSecurity http)throws Exception{
//	
//		http.authorizeHttpRequests(request->{//Don't Use tHIS Because This is because there is more than one mappable servlet in your servlet context: {o
//			request.requestMatchers("/").permitAll();
//			request.requestMatchers("/resources/**").permitAll();
//			request.requestMatchers("/admin/**").hasAuthority("Admin");
//			request.requestMatchers("/member/**").hasAnyAuthority("Admin","Member");
//			request.anyRequest().denyAll();
//		});
	
//		http.securityMatcher("/member/**")
//		.authorizeHttpRequests(request -> {
//			request.anyRequest().hasAnyAuthority("Member");
//		});
//		
//		return http.build();
//	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean 
	AuthenticationManager authenticationManager(HttpSecurity http,PasswordEncoder encoder) throws Exception {
		var builder=http.getSharedObject(AuthenticationManagerBuilder.class);
		var provider=new DaoAuthenticationProvider(encoder);
		provider.setUserDetailsService(userDetailsManager());
		
		builder.authenticationProvider(provider);
		
		builder.authenticationProvider(MemberProvider(encoder));
		return builder.build();
	}
	@Bean
	InMemoryUserDetailsManager userDetailsManager() {
		return new InMemoryUserDetailsManager(List.of(
		User.withUsername("admin@gmail.com").password("{noof}admin").authorities("Admin").build(),
		User.withUsername("member@gmail.com").password("{noof}member").authorities("Member").build()
				
				
				)
				);
	}
private	AuthenticationProvider  MemberProvider(PasswordEncoder encoder) {
	var userDetailsManager=new JdbcUserDetailsManager(dataSource);
	userDetailsManager.setUsersByUsernameQuery("select email username,password,true from member where email=?");
	userDetailsManager.setAuthoritiesByUsernameQuery("select email username,role from member where email=?");
	
		var dataProvider=new DaoAuthenticationProvider(encoder);
		dataProvider.setUserDetailsService(userDetailsManager);
		
		return   dataProvider;
	}
	
}
