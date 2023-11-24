package com.hello.configs;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mysql.cj.jdbc.Driver;

import lombok.experimental.var;
@Configuration

@EnableWebMvc
@ComponentScan(basePackages = "com.hello.controller")
public class MvcConfig  implements WebMvcConfigurer{
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
}
@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
registry.jsp().prefix("/view/").suffix(".jsp");
	}
@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addInterceptors(registry);
	}
@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	registry.addViewController("/").setViewName("home");
	registry.addViewController("/admin").setViewName("admin");

	registry.addViewController("/member").setViewName("member");

	}
@Bean
DataSource dataSource() {
	var dataSource=new BasicDataSource();
	dataSource.setDriverClassName(Driver.class.getName());
	dataSource.setPassword("securityuserpwd");
	dataSource.setUrl("jdbc:mysql://localhost:3306/security");
	dataSource.setUsername("securityuser");
	return dataSource;
}
}
