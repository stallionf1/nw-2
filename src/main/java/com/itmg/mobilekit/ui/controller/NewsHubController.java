package com.itmg.mobilekit.ui.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.api.response.CategoryAO;
import com.itmg.mobilekit.api.response.CategoryNewsAO;
import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.MenuItemAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.api.response.WeatherData;
import com.itmg.mobilekit.core.common.Config;
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
	
	@RequestMapping("/") 
	public String mainForm(Model uiModel, HttpServletRequest req, HttpServletResponse response) { 
		
		try {			
			String country = req.getParameter("countryItemParam");
			String menuItem = req.getParameter("menuItemParam");
			
			System.out.println("----- sending country param="+country);
			System.out.println("----- sending Menu param="+menuItem);
			
			if (country == null) {
				country = "UA";
			}
			
			if (menuItem != null) {
				menuItem = extractMenuNameFromUrl(menuItem);
			}
			
			List<MenuItemAO> list = service.listMenuItems(country, req.getRemoteAddr());
			uiModel.addAttribute("menuItemsList", list);
			uiModel.addAttribute("countryItemsList", service.listAllCountries(req.getRemoteAddr()));
			
//			WeatherData weather = service.loadWeatherData(req.getRemoteAddr());
//			System.out.println("--- loaded weather" + weather);			
//			uiModel.addAttribute("weatherData", weather.getLocation());
			
			List<NewsContentAO> news = new ArrayList<NewsContentAO>();
			if (menuItem != null) {
				news = service.loadNewsByMenuSectionAndCountry(menuItem, country, req.getRemoteAddr(), "1", "NO");
			} else {
				 news = service.listMainNews(country, "1", "NO", req.getRemoteAddr());	
			}
			uiModel.addAttribute("mainNewsList", news);
			//TODO: clean it and place it to right place!
			HttpSession session = req.getSession(true);
			session.setAttribute("mainNewsList", news);
			
		} catch (MobileKitServiceException e) {
			
			//e.printStackTrace();
		}
		return "mobile_index";
	}
	
	private String extractMenuNameFromUrl(String menuItem) {
		
		int lenght = menuItem.length();
		int x = menuItem.lastIndexOf("/");
		
		String res = menuItem.substring(x+1, lenght);
		//System.out.println("--- menu item = " + res);
		return res;
	}
	
	@RequestMapping("/{news_url}")
	public String showNewsContentDetails(@PathVariable String news_url, Model uiModel, HttpServletRequest req, HttpServletResponse response) {
		try {
			
			HttpSession session = req.getSession();
			List<NewsContentAO> aaa = (List<NewsContentAO>)session.getAttribute("mainNewsList");
		//	System.out.println("---- sesssion list =" + aaa);
			
			String testId = findNewsId(news_url, req.getSession());
			//System.out.println("******************************* \n FOUND newsID is:"+testId + "*******************");
			
			NewsContentAO newsContent = service.loadNewsDetails(testId, "UA", req.getRemoteAddr());
			uiModel.addAttribute("newsObject", newsContent);
			return "news_content";
		} catch (MobileKitServiceException e) {
			e.printStackTrace();
		}
		return "news_content";
		
	}
	
	@RequestMapping("/search")
	public String searchNews(Model uiModel, HttpServletRequest req, HttpServletResponse response) {
		
		try {
			List<NewsContentAO> searched = service.searchNewsBy(
					req.getParameter("searchParam"),
					req.getParameter("countryCode"), 
					req.getParameter("categoryCode"), "1", req.getRemoteAddr());
			
			uiModel.addAttribute("mainNewsList", searched);
			//TODO: clean it and place it to right place!
			HttpSession session = req.getSession();
			session.setAttribute("mainNewsList", searched);
			
		} catch (MobileKitServiceException e) {
			//e.printStackTrace();
		}
		return "mobile_index";
	}
	
	private String findNewsId(String url, HttpSession session) {
		if (session.getAttribute("mainNewsList") != null) {
			List<NewsContentAO> news = (List<NewsContentAO>) session.getAttribute("mainNewsList");
			for (NewsContentAO element : news) {
				if (element.getNews_url().contains(url)) {
					return element.getNews_id();
				}
			}
		}
		return "NOT_FOUND"; //dummy paramenter.
	}

	@RequestMapping("/tmp")
	public void retrieveUsersCountry(HttpServletRequest req, HttpServletResponse response) {
		
		try {
			String usersLocale = service.fetchUsersLocale(req.getRemoteAddr());
			HttpSession session = req.getSession();
			
			session.setAttribute(Config.getInstance().getUsersDeviceLocale(), usersLocale);
			
		} catch (MobileKitServiceException e) {
			
		}
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
	
	
//	@RequestMapping(value="form", method=RequestMethod.GET)
//	public String loadPage(Model m) {
////		m.addAttribute("subscriber", new Subscriber());
//		return "formPage";
//	}
//	
//	@RequestMapping(value="form", method=RequestMethod.POST)
//	public String onPageActionChanged(@ModelAttribute MenuItemAO menuItem, Model m) {
//		m.addAttribute("message", "Successfully saved person: " + menuItem.toString());
//		return "formPage";
//	}

		
}