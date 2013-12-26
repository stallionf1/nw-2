package com.itmg.mobilekit.ui.context;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.itmg.mobilekit.core.common.NewsUpdater;
import com.itmg.mobilekit.core.common.NewsUpdaterImpl;


public class MobileKitServletContextListener implements ServletContextListener  {
	private NewsUpdater newsUpdater = new NewsUpdaterImpl();
		
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//System.out.println(" ============== \n MobileKit was destroyed. \n =============");
	//	newsUpdater.stopUpdating();
		
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	//	System.out.println(" ============== \n Starting Updater. \n =============");
				
		//newsUpdater.update();
		
	//	System.out.println(" ============== \n MobileKit has been started. \n =============");

	
	}
}
