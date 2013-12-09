package com.itmg.mobilekit.parser.ui.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Tmp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String link = "http://api.wunderground.com/api/855ee74161c29524/key/geolookup/conditions/lang:en/q/autoip.json?geo_ip=176.106.1.162";
		
		HttpURLConnection request;
		BufferedReader rd = null;
		StringBuilder response = null;
		
		try {
			URL url = new URL(link);
			
			request = (HttpURLConnection) url.openConnection();
			request.setRequestMethod("GET");
			
			request.connect();
			
			System.out.println("--- done");
			
			rd  = new BufferedReader(new InputStreamReader(request.getInputStream()));
			response = new StringBuilder();
			String line = null;
			while ((line = rd.readLine()) != null){
				response.append(line + '\n');
			}
			
			System.out.println("--received: " + response.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
