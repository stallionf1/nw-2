package com.itmg.mobilekit.service;

import java.util.List;

import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.service.exception.MobileKitServiceException;

public interface MobileKitAPIService {
	
	List<CountryAO> listAllCountries() throws MobileKitServiceException;
	
	List<NewsContentAO> listMainNews(String countryCode, String pageID, String fullContent) throws MobileKitServiceException;
	
	List<NewsContentAO> getDetailedNewsContent(String newsID) throws MobileKitServiceException;
	
	void loadHomePageContent() throws MobileKitServiceException;
	
}
