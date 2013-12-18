package com.itmg.mobilekit.api.response;

import java.util.Date;

public class CategoryAO extends APIResponseObject {

	private String localized_name;
	private String name;
	
	public String getLocalized_name() {
		return localized_name;
	}
	public void setLocalized_name(String localized_name) {
		this.localized_name = localized_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	Date deteReceived() {
		// TODO Auto-generated method stub
		return null;
	}
}
