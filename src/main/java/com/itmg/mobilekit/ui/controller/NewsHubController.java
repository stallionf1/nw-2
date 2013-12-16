package com.itmg.mobilekit.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itmg.mobilekit.service.MobileKitAPIService;

@Controller
@RequestMapping("/news")
public class NewsHubController {

	@Autowired
	private MobileKitAPIService service;
	
	@RequestMapping("/")
	public ResponseEntity<String> handleMainPageContent(HttpServletRequest req, HttpServletResponse response) {
		System.out.println("--- executing handleMainPageContent method -------");
		
		//WrappedObject homeContent = service.loadHomePageContent();
		//return new ResponseEntity<String>(homeContent.getHtml(), HttpStatus.OK);
		
		
		return new ResponseEntity<String>("all_main_page_data", HttpStatus.OK);
	}
	
	
	//Test executions...
	@RequestMapping("/countries")
	public ResponseEntity<String> listAllCountries(HttpServletRequest req, HttpServletResponse response) {
		
		service.listAllCountries();
		
		return new ResponseEntity<String>("countries_list_called", HttpStatus.OK);
	}
	
	@RequestMapping("/slider_news")
	public ResponseEntity<String> listAllSliderNews(HttpServletRequest req, HttpServletResponse response) {
		
		return new ResponseEntity<String>("slider_news_called", HttpStatus.OK);
	}
}
