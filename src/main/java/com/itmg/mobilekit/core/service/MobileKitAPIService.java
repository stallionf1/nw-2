package com.itmg.mobilekit.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.api.response.CategoryAO;
import com.itmg.mobilekit.api.response.CategoryNewsAO;
import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.MenuItemAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.core.exception.MobileKitServiceException;

public interface MobileKitAPIService {
	
	
	List<CountryAO> listAllCountries() throws  MobileKitServiceException, ClientProtocolException, IOException;
	
	List<MenuItemAO> listMenuItems(String countryCode) throws MobileKitServiceException, ClientProtocolException, IOException;
	
	List<NewsContentAO> listSliderNews(String countryCode) throws MobileKitServiceException, ClientProtocolException, IOException;
	
	List<NewsContentAO> listMainNews(String countryCode, String pageID, String fullContent) throws MobileKitServiceException, ClientProtocolException, IOException;
	
	List<NewsContentAO> getDetailedNewsContent(String newsID) throws MobileKitServiceException;
	
	Map<APITypes, Object> loadHomePageContent() throws MobileKitServiceException;
	
	
	List<CategoryAO> loadCategoriesByCountry(String countryCode) throws  MobileKitServiceException, ClientProtocolException, IOException;
	List<CategoryNewsAO> loadCategoryNewsByCategoryAndCountry(String category, String countryCode) throws  MobileKitServiceException, ClientProtocolException, IOException;

	List<NewsContentAO> loadNewsByMenuSectionAndCountry(String menuSection, String countryCode) throws  MobileKitServiceException, 
																										ClientProtocolException, 
																										IOException;
	NewsContentAO loadNewsDetails(String newsID, String countryCode) throws  MobileKitServiceException, ClientProtocolException, IOException;


}
