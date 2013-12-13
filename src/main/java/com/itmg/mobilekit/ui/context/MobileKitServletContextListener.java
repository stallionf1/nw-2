package com.itmg.mobilekit.ui.context;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.itmg.mobilekit.common.NewsUpdater;
import com.itmg.mobilekit.common.NewsUpdaterImpl;


public class MobileKitServletContextListener implements ServletContextListener  {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println(" ============== \n MobileKit was destroyed. \n =============");

	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println(" ============== \n Starting Updater. \n =============");
		
		NewsUpdater newsUpdater = new NewsUpdaterImpl();
		newsUpdater.update();
		
		System.out.println(" ============== \n MobileKit has been started. \n =============");

	
	}
}
