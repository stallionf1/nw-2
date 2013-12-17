package com.itmg.mobilekit.api;

public enum APITypes {
	
	GET_COUNTRIES("getCountries"),
	GET_MAIN_NEWS("getMainNews"),
	GET_MENU_ITEMS("geMenuItems"),
	GET_SLIDER_NEWS("getSliderNews"),
	GET_REFERENSED_ITEMS("getReferencedItems"),
	NOT_SPECIFIED("not_supported_api_type_name");

	private String apiTypeDesciprion;
	
	APITypes(String name) {
		this.apiTypeDesciprion = name;
	}
	
	public String getApiTypeDesciprion() {
		return this.apiTypeDesciprion;
	}
}
