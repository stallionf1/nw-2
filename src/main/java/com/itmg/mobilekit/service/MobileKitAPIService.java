package com.itmg.mobilekit.service;

import java.util.List;
import java.util.Map;

import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.exception.MobileKitServiceException;

public interface MobileKitAPIService {
	
	List<CountryAO> listAllCountries() throws MobileKitServiceException;
	
	List<NewsContentAO> listMainNews(String countryCode, String pageID, String fullContent) throws MobileKitServiceException;
	
	List<NewsContentAO> getDetailedNewsContent(String newsID) throws MobileKitServiceException;
	
	 Map<APITypes, Object> loadHomePageContent() throws MobileKitServiceException;
	
}
