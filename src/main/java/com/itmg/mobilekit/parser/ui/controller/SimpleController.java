package com.itmg.mobilekit.parser.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SimpleController {
	
	@RequestMapping("/weather")
	public String checkWeather () {
		System.out.println("--Calling simple controller ----");
		return "xxxx";
	}
}