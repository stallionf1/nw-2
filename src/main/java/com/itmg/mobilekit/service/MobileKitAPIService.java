package com.itmg.mobilekit.service;

import java.util.List;

import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.NewsContentAO;

public interface MobileKitAPIService {
	
	List<CountryAO> listAllCountries();
	
	List<NewsContentAO> listMainNews(String countryCode, String pageID, String fullContent);
	
	List<NewsContentAO> getDetailedNewsContent(String newsID);
	
}
