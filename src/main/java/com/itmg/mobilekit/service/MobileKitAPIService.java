package com.itmg.mobilekit.service;

import java.util.List;

import com.itmg.mobilekit.api.NewsContent;

public interface MobileKitAPIService {
	
	void listAllCountries();
	
	void listMainNews();
	
	List<NewsContent> getDetailedNewsContent(String newsID);
	
}
