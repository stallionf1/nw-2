package com.itmg.mobilekit.ui.controller.test;

import com.itmg.mobilekit.exception.MobileKitServiceException;
import com.itmg.mobilekit.service.MobileKitAPIService;
import com.itmg.mobilekit.service.MobileKitAPIServiceImpl;

public class NewsHubControllerTest {

	
	
	//dummy
	
	public static void main(String[] args) {
		MobileKitAPIService service = new MobileKitAPIServiceImpl();
		
		try {
			service.loadHomePageContent();
		} catch (MobileKitServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}