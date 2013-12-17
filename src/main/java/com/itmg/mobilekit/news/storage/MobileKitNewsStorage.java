package com.itmg.mobilekit.news.storage;

import java.util.List;

import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.MenuItemAO;
import com.itmg.mobilekit.exception.MobileKitNewsStorageException;

public interface MobileKitNewsStorage {

	void putMenuItemsToStorage(List<MenuItemAO> menuItems);
	void putCountriesListToStorage(List<CountryAO> countries);
	
	List<MenuItemAO> loadMenuItemsListAO() throws MobileKitNewsStorageException;
	List<CountryAO> loadCountriesListAO() throws MobileKitNewsStorageException;
	
	
}
