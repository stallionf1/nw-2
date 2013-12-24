package com.itmg.mobilekit.core.service;

import java.util.List;
import java.util.Map;

import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.api.response.CategoryAO;
import com.itmg.mobilekit.api.response.CategoryNewsAO;
import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.MenuItemAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.api.response.WeatherData;
import com.itmg.mobilekit.core.exception.MobileKitServiceException;

public interface MobileKitAPIService {
	
	
	/** 
	 * @name:
	 * @param:
	 * @return: 
	 * */
	List<CountryAO> listAllCountries(String remoteIp) throws  MobileKitServiceException;
	
	/** 
	 * @name:
	 * @param:
	 * @return: 
	 * */
	List<MenuItemAO> listMenuItems(String countryCode, String remoteIp) throws MobileKitServiceException;
	
	/** 
	 * @name:
	 * @param:
	 * @return: 
	 * */
	List<NewsContentAO> listSliderNews(String countryCode, String remoteIp) throws MobileKitServiceException;
	
	/** 
	 * @name:
	 * @param:
	 * @return: 
	 * */
	List<NewsContentAO> listMainNews(String countryCode, String pageID, String fullContent, String remoteIp) throws MobileKitServiceException;
	
	/** 
	 * @name:
	 * @param:
	 * @return: 
	 * */
	List<NewsContentAO> getDetailedNewsContent(String newsID, String remoteIp) throws MobileKitServiceException;
	
	Map<APITypes, Object> loadHomePageContent(String remoteIp) throws MobileKitServiceException;
	
	List<CategoryAO> loadCategoriesByCountry(String countryCode, String remoteIp) throws  MobileKitServiceException;
	
	List<CategoryNewsAO> loadCategoryNewsByCategoryAndCountry(String category, String countryCode, String remoteIp) throws  MobileKitServiceException;

	List<NewsContentAO> loadNewsByMenuSectionAndCountry(String menuSection, String countryCode, String remoteIp, String pageId, String fullContent) throws MobileKitServiceException;
	
	NewsContentAO loadNewsDetails(String newsID, String countryCode, String remoteIp) throws  MobileKitServiceException;

	WeatherData loadWeatherData(String locationIp) throws MobileKitServiceException;

	String fetchUsersLocale(String usersIp) throws MobileKitServiceException;
	
}
