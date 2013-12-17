package com.itmg.mobilekit.api.response;

import java.util.Date;
import java.util.List;

public class CountryAO implements APIResponseObject {

	private String code;
	private String name;
	private String url;
	
	private List<CountryAO> countries;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
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
	
	public List<CountryAO> getCountries() {
		return countries;
	}
	public void setCountries(List<CountryAO> countries) {
		this.countries = countries;
	}
	
	@Override
	public String toString() {
		return "CountryAO [code=" + code + ", name=" + name + ", url=" + url
				+ "]";
	}
	
	
}
