package com.itmg.mobilekit.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.MenuItemAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.exception.MobileKitServiceException;

public interface MobileKitAPIService {
	
	//Done
	List<CountryAO> listAllCountries() throws  MobileKitServiceException, ClientProtocolException, IOException;
	//Done
	List<MenuItemAO> listMenuItems(String countryCode) throws MobileKitServiceException, ClientProtocolException, IOException;
	//Done
	List<NewsContentAO> listSliderNews(String countryCode) throws MobileKitServiceException, ClientProtocolException, IOException;
	
	List<NewsContentAO> listMainNews(String countryCode, String pageID, String fullContent) throws MobileKitServiceException, ClientProtocolException, IOException;
	
	List<NewsContentAO> getDetailedNewsContent(String newsID) throws MobileKitServiceException;
	
	Map<APITypes, Object> loadHomePageContent() throws MobileKitServiceException;
	
}
