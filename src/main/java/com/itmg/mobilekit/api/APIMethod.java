package com.itmg.mobilekit.api;

public class APIMethod {

	private String name = "getMainNews";
	private String[] params;
	
	private APITypes type;
	
	//getMainNews?pageId=p1&countryCode=UA&accessToken=88hhshdkjha74-2hsdf
	
	//http://newshubtest.org/api/getDetailedNewsContent?newsID=1547&accessToken=ec5e7622a39ba5a09e87fabcce102851
	//http://newshubtest.org/api/getMainPageNews?fullContent=no&pageID=2&countryCode=ua&accessToken=ec5e7622a39ba5a09e87fabcce102851

	void smile() {
//	  APIMethod method = new APIMethod();
//	  method.name=""
		
		String token = "ec5e7622a39ba5a09e87fabcce102851";
		String newsID= "1547";
	
		String url = String.format("%s?newsID=%s&accessToken=%s", name, newsID, token);
	}
	
	public APIMethod(APITypes type) {
		this.type = type;
	}
	
	public String createApiURL (APIMethod apiToCall) {
		
		
		return "";
	}
}
