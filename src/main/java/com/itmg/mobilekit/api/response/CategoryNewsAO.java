package com.itmg.mobilekit.api.response;

import java.util.Date;

public class CategoryNewsAO extends APIResponseObject {

	private String title;
	private String url;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
		return "CategoryNewsAO [title=" + title + ", url=" + url + "]";
	}	
}
