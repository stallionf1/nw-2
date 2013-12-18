package com.itmg.mobilekit.news.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.MenuItemAO;
import com.itmg.mobilekit.exception.MobileKitNewsStorageException;

public class MobileKitNewsStorageImpl implements MobileKitNewsStorage {
	
	private Map<String, List<MenuItemAO>> menuItemsStorage = new HashMap<String, List<MenuItemAO>>();
	
	@Override
	public void putMenuItemsToStorage(List<MenuItemAO> menuItems) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putCountriesListToStorage(List<CountryAO> countries) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MenuItemAO> loadMenuItemsListAO() throws MobileKitNewsStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CountryAO> loadCountriesListAO() throws MobileKitNewsStorageException {
		// TODO Auto-generated method stub
		return null;
	}

}
