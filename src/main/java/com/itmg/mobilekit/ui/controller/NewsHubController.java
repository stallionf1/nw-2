package com.itmg.mobilekit.ui.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private String getCountryFromSession(HttpSession session) {
		return (String)session.getAttribute(Config.getInstance().getSessionCountry());
	}
	
	private void updateSessionCountry(String countryValue, HttpSession session) {
		session.setAttribute(Config.getInstance().getSessionCountry(), countryValue);
	}
	
	private void updateSessionCategory(String categoryValue, HttpSession session) {
		session.setAttribute(Config.getInstance().getSessionCategory(), categoryValue);
	}
	
	private String getCategoryFromSession(HttpSession session) {
		return (String) session.getAttribute(Config.getInstance().getSessionCategory());
	}
	
	@RequestMapping("/") 
	public String mainForm(Model uiModel, HttpServletRequest req, HttpServletResponse response) {
		
		try {			
			HttpSession session = req.getSession();			
			String searchCountry = "";
			String sessionCountry = getCountryFromSession(req.getSession());
			String requestCountry = req.getParameter("countryItemParam");
			
			searchCountry = (requestCountry == null) ? sessionCountry : requestCountry;
			updateSessionCountry(searchCountry, session);
			
			String searchCategory = "all"; //by default - all.
			String sessionCategory = getCategoryFromSession(session);
			String requestCategory = req.getParameter("menuItemParam");
			
			if(sessionCategory == null && requestCategory == null) {
				searchCategory = "all";
			} else {			
				searchCategory = (requestCategory != null) ? extractMenuNameFromUrl(requestCategory) : sessionCategory;
			}
			
			if (requestCountry != null) {
				//user is changing country. Reset category
				searchCategory = "all";
			}
			
			updateSessionCategory( searchCategory, session);
						
			List<MenuItemAO> list = service.listMenuItems(searchCountry, req.getRemoteAddr());
			uiModel.addAttribute("menuItemsList", list);
			uiModel.addAttribute("countryItemsList", service.listAllCountries(req.getRemoteAddr()));
			
			WeatherData weather = service.loadWeatherData(req.getRemoteAddr());
			
			if (weather != null) {
				uiModel.addAttribute("weatherData", weather);
			}
			
			List<NewsContentAO> news = new ArrayList<NewsContentAO>();
			List<NewsContentAO> topNews = new ArrayList<NewsContentAO>();
			
			if (requestCategory != null) {
				news = service.loadNewsByMenuSectionAndCountry(searchCategory, searchCountry, req.getRemoteAddr(), "1", "NO");
			} else {
				 news = service.listMainNews(searchCountry, "1", "NO", req.getRemoteAddr());
				 topNews = service.getTopNews(getSessionCountry(session), req.getRemoteAddr());
				 uiModel.addAttribute("topNews", topNews);
			}
			
			uiModel.addAttribute("mainNewsList", news);  
			
			List<NewsContentAO> sessionNews= new ArrayList<NewsContentAO>(news);
			sessionNews.addAll(topNews);
			
			session.setAttribute("mainNewsList", sessionNews);
			
		} catch (MobileKitServiceException e) {
			logger.fatal("Failed to get data fro main page!", e);
			return "some_error_page";
		}
		return "mobile_index";
	}
	
	private boolean userHasChangedCountry(String sessionCountry, String requestCountry) {	
		if (requestCountry != null) {
			
		}
		return !sessionCountry.equals(requestCountry) ;
	}

	private String getSessionCountry(HttpSession session) {
		Object res = session.getAttribute(Config.getInstance().getSessionCountry());
		return res != null ? (String)res : "ua";
	}
	
	private String extractMenuNameFromUrl(String menuItem) {
		
		int lenght = menuItem.length();
		int x = menuItem.lastIndexOf("/");
		
		String res = menuItem.substring(x+1, lenght);
		
		return res;
	}
	
	@RequestMapping("/{news_url}")
	public String showNewsContentDetails(@PathVariable String news_url, Model uiModel, HttpServletRequest req, HttpServletResponse response) {
		try {
		 
			String testId = findNewsId(news_url, req.getSession());
			uiModel.addAttribute("currentNewsId", testId);
			
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
			
			String searchParam = cleanSearchKeyword(req.getParameter("searchParam"));
			
			List<NewsContentAO> searched = service.searchNewsBy(
					searchParam,
					getSessionCountry(req.getSession()), 
					req.getParameter("categoryCode"), "1", req.getRemoteAddr());
			
			uiModel.addAttribute("mainNewsList", searched);
			
			HttpSession session = req.getSession();
			session.setAttribute("mainNewsList", searched);
			session.setAttribute("searchParam", searchParam);
			
		} catch (MobileKitServiceException e) {
			logger.error("Failed to do search.", e);
			return "some_error_page";
		}
		return "search_results";
	}
	
	private String cleanSearchKeyword(String keyword) {		
		return (keyword == null) ? "" : keyword.trim().replaceAll("[!@#$%^&*()_+=|?]", "").replaceAll(" ", "%20"); 
	}
	
	@RequestMapping("/load_more_search_results")
	public void loadMoreSearchresults(HttpServletRequest req, HttpServletResponse response) {
		String pageId = req.getParameter("data");
		try {			
			HttpSession session = req.getSession();
			
			List<NewsContentAO> searched = service.searchNewsBy(
					(String)session.getAttribute("searchParam"),
					getCountryFromSession(session), 
					req.getParameter("categoryCode"), pageId, req.getRemoteAddr());
			
			List<NewsContentAO> sessionNnews = (List<NewsContentAO>)session.getAttribute("mainNewsList");
			
			sessionNnews.addAll(searched);
			session.setAttribute("mainNewsList", sessionNnews);
			
			response.setContentType( "text/html" );
			response.setCharacterEncoding( "UTF-8" );
			
			PrintWriter out = response.getWriter();
			for (NewsContentAO item : searched) {
				writeNewsObject(item, out);
			}
			out.close();
			
		} catch (MobileKitServiceException e) {
			logger.error("Failed to do search.", e);
			
		} catch (IOException e) {
			logger.error("Failed oto write out response fro search next page.", e);
		}		
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
	
	@RequestMapping("/load_more_news")
	public void loadMoreNews (HttpServletRequest req, HttpServletResponse response) {
		String data = req.getParameter("data");
		try {
			
			HttpSession session = req.getSession();
			
			String searchCountry = getCountryFromSession(req.getSession());					
			String searchCategory = getCategoryFromSession(session);
			
			List<NewsContentAO> moreNewsList = new ArrayList<NewsContentAO>();
			if (!searchCategory.equalsIgnoreCase("all")) {
				moreNewsList = service.loadNewsByMenuSectionAndCountry(searchCategory, searchCountry, req.getRemoteAddr(), data, "NO");
			} else {
				moreNewsList = service.listMainNews(searchCountry, data, "NO", req.getRemoteAddr());	
			}
			
			response.setContentType( "text/html" );
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter out = response.getWriter();
			
			List<NewsContentAO> sessioNnews = (List<NewsContentAO>)session.getAttribute("mainNewsList");
			
			sessioNnews.addAll(moreNewsList);
			session.setAttribute("mainNewsList", sessioNnews);
			
			for (NewsContentAO item : moreNewsList) {				
				writeNewsObject(item, out);
			}			
			out.close();
		} catch (MobileKitServiceException e1) {			
			logger.error("Failed to load more news.", e1);
		}		
		catch (IOException e) {			
			logger.error("Failed to write out response for LoadMoreNews.", e);
		}	
	}
	
	private void writeNewsObject(NewsContentAO item, PrintWriter out) {
		out.write("<div class=\"news-item\">");
		if (item.isParsed()) {
			out.write("<a href=" + item.getShort_url() + "class=\"block left\"><img class=\"left\" width=\"140\" src="
					+ item.getImg_src() + " alt=" + item.getImg_alt()
					+ "/></a>");
		} else {
			out.write("<a href=" + item.getNews_url() + "class=\"block left\"><img class=\"left\" width=\"140\" src="
					+ item.getImg_src() + " alt=" + item.getImg_alt()
					+ "/></a>");
		}
	
		out.write("<span class=\"date block\">"+item.getDate_updated()+" </span>");
		
		if (item.isParsed()) {
			out.write("<a href=" + item.getShort_url() + " class=\"news-title block\">"
					+ item.getNews_title() + "</a>");
		} else {
			out.write("<a href=" + item.getNews_url() + " class=\"news-title block\">"
					+ item.getNews_title() + "</a>");
		}
	
		out.write("<p>");
		out.write("<span>" + item.getNews_content() + "</span>");
		out.write("</p>");				
		out.write("</div>");	
	}
	
	@RequestMapping("/load_previous_news")
	public void loadPreviousNews (Model uiModel, HttpServletRequest req, HttpServletResponse response) {
	
		//TODO: create news data storage.
		String currentNewsId = req.getParameter("data");
		System.out.println("------------ current news is=" +currentNewsId);
		
		
		if (currentNewsId != null) {
			List<NewsContentAO> currentSessionNews = (List<NewsContentAO>)req.getSession().getAttribute("mainNewsList");
			for (NewsContentAO news : currentSessionNews) {
				if (news.getNews_id().equals(currentNewsId)) {
					int currentIndex = currentSessionNews.indexOf(news);
					System.out.println("-- current index="+currentIndex);
					int previousIndex = currentIndex-1;
					System.out.println("-- previous index="+previousIndex);
					
					NewsContentAO previousNews = currentSessionNews.get(previousIndex);
					System.out.println("---- previous news index = "+previousNews.getNews_id());
					try {
						NewsContentAO fullNewsContent = service.loadNewsDetails(previousNews.getNews_id(), "UA", req.getRemoteAddr());
						
						System.out.println("---- loaded full content NEWS =" + fullNewsContent);
						response.setContentType( "text/html" );
						response.setCharacterEncoding( "UTF-8" );
						
						PrintWriter out = response.getWriter();
						
						out.write("<img class=\"left\" width=\"140\" src="+fullNewsContent.getImg_src()+""
								+ "alt="+fullNewsContent.getImg_alt()+" />"
								+ "<span class=\"date block\">"+fullNewsContent.getDate_updated()+"</span>"
								+ "<p><b>"+fullNewsContent.getNews_title()+"</b></p><p>"
								+ "<span>"+fullNewsContent.getNews_content()+"</span></p>");
						out.write("<input type=\"hidden\" name=\"newsId\" value="+fullNewsContent.getNews_id()+"/>");
						out.close();
						
						
						uiModel.addAttribute("currentNewsId", fullNewsContent.getNews_id());
						
					} catch (MobileKitServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@RequestMapping("/load_next_news")
	public void loadNextNews (Model uiModel, HttpServletRequest req, HttpServletResponse response) {
		System.out.println("----------------- load_next_news");
		
		String data = req.getParameter("data");
		System.out.println("--- data from request parameters:" + data);
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