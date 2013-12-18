package com.itmg.mobilekit.core.storage;

import java.util.List;

import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.MenuItemAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.api.response.CategoryNewsAO;

public interface NewsHubContentStorage {

	//General start
	void updateNewsHubContent();
	
	//Method to get data from stroage and return it to requested controller call.
	List<CountryAO> loadCountriesList();
	List<MenuItemAO> loadMenuItems(String country);
	List<NewsContentAO> loadMainNews(String country);
	List<NewsContentAO> loadSliderNews(String country);
	List<CategoryNewsAO> loadCategoriesItems(String country);
	
}
