package com.itmg.mobilekit.service;

import java.util.List;

import com.itmg.mobilekit.api.response.NewsContentAO;

public interface MobileKitAPIService {
	
	void listAllCountries();
	
	void listMainNews();
	
	List<NewsContentAO> getDetailedNewsContent(String newsID);
	
}
