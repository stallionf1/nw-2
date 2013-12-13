package com.itmg.mobilekit.common;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsUpdaterImpl implements NewsUpdater {

	
	private static final Logger logger = LoggerFactory.getLogger(NewsUpdaterImpl.class);
	
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
		System.out.println("--- start updating news content ---- ");
	}
}
