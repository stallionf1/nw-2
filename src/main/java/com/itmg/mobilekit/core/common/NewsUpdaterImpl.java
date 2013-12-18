package com.itmg.mobilekit.core.common;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.itmg.mobilekit.core.storage.NewsHubContentStorage;

public class NewsUpdaterImpl implements NewsUpdater {
	
	//TODO: init with spring.
	private NewsHubContentStorage newsHubContentStorage;
	
	private static final Logger logger = Logger.getLogger(NewsUpdaterImpl.class);
	
	@Override
	public void update() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
			
				updateNewsContent();
				
			}
		}, 0, 1000L * 60 * 1);
		
	}
	
	private void updateNewsContent() {
		logger.info("Start updating news conent.");
		//System.out.println("--- start updating news content ---- ");
		//newsHubContentStorage.updateNewsHubContent();
	}
}
