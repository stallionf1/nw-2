package com.itmg.mobilekit.ui.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.exception.MobileKitServiceException;
import com.itmg.mobilekit.service.MobileKitAPIService;

@Controller
@RequestMapping("/news")
public class NewsHubController {

	private final static Logger logger = Logger.getLogger(NewsHubController.class);
	
	@Autowired
	private MobileKitAPIService service;
	
	@RequestMapping("/api")
	public ResponseEntity<String> handleMainPageContent(HttpServletRequest req, HttpServletResponse response) {
		logger.debug("Executing <handleMainPageContent> method.");
		
		try {
			Map<APITypes, Object> mainPageContent = service.loadHomePageContent();
			logger.debug("Parsed content is" + mainPageContent);
			return new ResponseEntity<String>(mainPageContent.keySet().toString(), HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			logger.error("Failed to execute handleMainPageContent method.", e);
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
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
