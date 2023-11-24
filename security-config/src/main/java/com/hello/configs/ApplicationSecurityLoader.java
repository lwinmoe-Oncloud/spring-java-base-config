package com.hello.configs;

import java.util.EnumSet;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import jakarta.servlet.DispatcherType;

public class ApplicationSecurityLoader extends AbstractSecurityWebApplicationInitializer{
	@Override
	protected EnumSet<DispatcherType> getSecurityDispatcherTypes() {
		// TODO Auto-generated method stub
		return EnumSet.of(DispatcherType.ASYNC,DispatcherType.REQUEST); 
	}

}
