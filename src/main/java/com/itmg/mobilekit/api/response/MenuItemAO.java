package com.itmg.mobilekit.api.response;

import java.util.Date;

public class MenuItemAO extends APIResponseObject {

	private String name;
	private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public Date deteReceived() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		return "MenuItemAO [name=" + name + ", url=" + url + "]";
	}
}
