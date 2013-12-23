package com.itmg.mobilekit.ui.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.api.response.CategoryAO;
import com.itmg.mobilekit.api.response.CategoryNewsAO;
import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.MenuItemAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.api.response.WeatherData;
import com.itmg.mobilekit.core.exception.MobileKitServiceException;
import com.itmg.mobilekit.core.service.MobileKitAPIService;

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
			Map<APITypes, Object> mainPageContent = service.loadHomePageContent(req.getRemoteAddr());
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
			List<CountryAO> list = service.listAllCountries(req.getRemoteAddr());	
			HttpHeaders h = new HttpHeaders();
			h.add("Content-type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>(list.toString(), h, HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			return new ResponseEntity<String>("countries_list_called_ERROR", HttpStatus.OK);
		}
	}
	
	@RequestMapping("/menu_items")
	public ResponseEntity<String> listMenuItems(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			List<MenuItemAO> list = service.listMenuItems("UA", req.getRemoteAddr());	
			HttpHeaders h = new HttpHeaders();
			h.add("Content-type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>(list.toString(), h, HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			return new ResponseEntity<String>("menu_items_called_ERROR", HttpStatus.OK);
		}
	}
	
	@RequestMapping("/slider_news")
	public ResponseEntity<String> listSliderNews(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			List<NewsContentAO> list = service.listSliderNews("RU", req.getRemoteAddr());	
			HttpHeaders h = new HttpHeaders();
			h.add("Content-type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>(list.toString(), h, HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			return new ResponseEntity<String>("slider_news_items_called_ERROR", HttpStatus.OK);
		} 
	}
	
	@RequestMapping("/main_news")
	public ResponseEntity<String> listMainNews(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			String page = req.getParameter("page");
			System.out.println("--- page parameter = " + page);
			if (page == null) {
				page = "1";
			}
			List<NewsContentAO> list = service.listMainNews("UA", page, "NO", req.getRemoteAddr());	
			HttpHeaders h = new HttpHeaders();
			h.add("Content-type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>(list.toString(), h, HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			return new ResponseEntity<String>("failed_to_load_main_news_ERROR", HttpStatus.OK);
		}
	}
	

	@RequestMapping("/categories")
	public ResponseEntity<String> listCategories(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			List<CategoryAO> list = service.loadCategoriesByCountry("UA", req.getRemoteAddr());	
			HttpHeaders h = new HttpHeaders();
			h.add("Content-type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>(list.toString(), h, HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			return new ResponseEntity<String>("failed_to_load_categories_ERROR", HttpStatus.OK);
		}
	}
	
	@RequestMapping("/category_news")
	public ResponseEntity<String> listCategoryNews(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			List<CategoryNewsAO> list = service.loadCategoryNewsByCategoryAndCountry("world", "UA", req.getRemoteAddr());	
			HttpHeaders h = new HttpHeaders();
			h.add("Content-type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>(list.toString(), h, HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			return new ResponseEntity<String>("failed_to_load_category_news_ERROR", HttpStatus.OK);
		}
	}
	
	@RequestMapping("/menu_news")
	public ResponseEntity<String> listMenuNews(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			List<NewsContentAO> list = service.loadNewsByMenuSectionAndCountry("world", "UA", req.getRemoteAddr(), "p1", "YES");	
			HttpHeaders h = new HttpHeaders();
			h.add("Content-type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>(list.toString(), h, HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			return new ResponseEntity<String>("failed_to_load_menu_news_ERROR", HttpStatus.OK);
		}
	}
	
	@RequestMapping("/news_details")
	public ResponseEntity<String> listNewsDetails(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			NewsContentAO newsData = service.loadNewsDetails("16411", "UA", req.getRemoteAddr());	
			HttpHeaders h = new HttpHeaders();
			h.add("Content-type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>(newsData.toString(), h, HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			return new ResponseEntity<String>("failed_to_load_news_details_ERROR", HttpStatus.OK);
		}
	}
	
	@RequestMapping("/weather")
	public ResponseEntity<String> showWeather(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			WeatherData data = service.loadWeatherData(req.getRemoteAddr());	
			HttpHeaders h = new HttpHeaders();
			h.add("Content-type", "text/html;charset=UTF-8");
			return new ResponseEntity<String>(data.toString(), h, HttpStatus.OK);
			
		} catch (MobileKitServiceException e) {
			return new ResponseEntity<String>("failed_to_load_weather_ERROR", HttpStatus.OK);
		}
	}
	
	@RequestMapping("/index") 
	public String mainForm(Model uiModel, HttpServletRequest req, HttpServletResponse response) { 
		
		try {
			List<MenuItemAO> list = service.listMenuItems("UA", req.getRemoteAddr());
			uiModel.addAttribute("menuItemsList", list);
			
			List<CountryAO> countries = service.listAllCountries(req.getRemoteAddr());
			
			uiModel.addAttribute("countryItemsList", countries);
			//No need to add it here, because it has been already added in menuItem();
			//uiModel.addAttribute("menuItem", new MenuItemAO());
			
		} catch (MobileKitServiceException e) {
			
			System.out.println(e);
		}	

		return "mobile_index";
	}
	
	@RequestMapping("/")
	public void retrieveUsersCountry(HttpServletRequest req, HttpServletResponse response) {
		System.out.println("----calling / method to get users locale.");
	}
	
	//Method for all forms.
	@ModelAttribute("menuItem")
	public MenuItemAO menuItem() {
		return new MenuItemAO();
	}
	@ModelAttribute("countryItem")
	public CountryAO countryItem() {
		return new CountryAO();
	} 
		
}