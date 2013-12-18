package com.itmg.mobilekit.common;

import java.util.Arrays;
import java.util.List;

public interface Constants {

	static String NEWS_HUB_TOKEN = "accessToken=ec5e7622a39ba5a09e87fabcce102851";
	
	//static String NEWS_HUB_TOKEN_VALUE = "ec5e7622a39ba5a09e87fabcce102851"; 
	
	static String NEWS_HUB_API_URL = "http://newshubtest.org/api/";
	
	static String COUNTRIES_API_NAME = "getCountriesList";
	static String MENU_ITEMS_API_NAME = "getMenuItems";
	static String SLIDE_NEWS_API_NAME = "getSliderNews";
	static String REFERENCED_ITEMS_API_NAME = "getReferencedCategoriesList";
	static String MAIN_NEWS_API = "getMainPageNews";
	
	// API methods:
	
	// getCountriesList  (String accessToken)
	// getMenuItems (String countryCode)
    // getSliderNews(String countryCode)
	
	// listNewsByMeuItem (String menuItem, String countryCode, bool fullContent, String pageId, String accessToken)
	// getReferencedNewsByCategory: (String categoryName, String countryCode)
	// getReferencedCategoriesList: (String countryCode)
	

	static String countriesAO = "countries_ao";
	static String menuItemsAO = "menu_items_ao";
	static String sliderNewsAO = "slider_news_ao";
	static String mainNewsAO = "main_news_ao";
	static String referencedItemsNameAO = "referenced_tems_ao";
	
	static List<String> API_METHODS = Arrays.asList(new String[] {COUNTRIES_API_NAME, MENU_ITEMS_API_NAME, SLIDE_NEWS_API_NAME, REFERENCED_ITEMS_API_NAME});
}
