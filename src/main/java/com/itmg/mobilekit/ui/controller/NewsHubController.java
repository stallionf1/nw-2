package com.itmg.mobilekit.ui.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.service.MobileKitAPIService;
import com.itmg.mobilekit.service.exception.MobileKitServiceException;

@Controller
@RequestMapping("/news")
public class NewsHubController {

	@Autowired
	private MobileKitAPIService service;
	
	@RequestMapping("/api")
	public ResponseEntity<String> handleMainPageContent(HttpServletRequest req, HttpServletResponse response) {
		System.out.println("--- executing handleMainPageContent method -------");
		
		try {
			Map<APITypes, Object> mainPageContent = service.loadHomePageContent();
		} catch (MobileKitServiceException e) {
		
			e.printStackTrace();
		}
		
		//WrappedObject homeContent = service.loadHomePageContent();
		//return new ResponseEntity<String>(homeContent.getHtml(), HttpStatus.OK);
		
		
		return new ResponseEntity<String>("all_main_page_data", HttpStatus.OK);
	}
	
	
	//Test executions...
	@RequestMapping("/countries")
	public ResponseEntity<String> listAllCountries(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			service.listAllCountries();
		} catch (MobileKitServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("countries_list_called", HttpStatus.OK);
	}
	
	@RequestMapping("/slider_news")
	public ResponseEntity<String> listAllSliderNews(HttpServletRequest req, HttpServletResponse response) {
		
		return new ResponseEntity<String>("slider_news_called", HttpStatus.OK);
	}
}
