package com.pycloux.webtest.htmxdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.pycloux.webtest.htmxdemo.bean.CalculatorState;

@Configuration
public class HtmxdemoConfig {
	@Bean
	@SessionScope
	public CalculatorState getState() {
	    return new CalculatorState();
	}
}
