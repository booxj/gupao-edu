package com.booxj.gp.spring.v1.demo.service.impl;


import com.booxj.gp.spring.v1.demo.service.IDemoService;
import com.booxj.gp.spring.v1.mvcframework.annotation.GPService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name;
	}

}
