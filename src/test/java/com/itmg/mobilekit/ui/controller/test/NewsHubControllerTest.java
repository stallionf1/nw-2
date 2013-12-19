package com.itmg.mobilekit.ui.controller.test;

import com.itmg.mobilekit.core.exception.MobileKitServiceException;
import com.itmg.mobilekit.core.service.MobileKitAPIService;
import com.itmg.mobilekit.core.service.MobileKitAPIServiceImpl;

public class NewsHubControllerTest {

	
	
	//dummy
	
	public static void main(String[] args) {
		MobileKitAPIService service = new MobileKitAPIServiceImpl();
		
		try {
			service.loadHomePageContent("dd");
		} catch (MobileKitServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
