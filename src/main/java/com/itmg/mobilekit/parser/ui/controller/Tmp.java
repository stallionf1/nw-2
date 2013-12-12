package com.itmg.mobilekit.parser.ui.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class Tmp {

	public static class User {

		private int age = 29;
	
		public User() {
			
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
		
	
	}

	private static void testJson() {
		String json = "{\"age\":29,\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"],\"name\":\"mkyong\"}";
		
		
		ObjectMapper mapper = new ObjectMapper();

		try {

			// read from file, convert it to user class
			User user = mapper.readValue(json, User.class);

			// display to console
			System.out.println(user);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		testJson();
		
		//restTemplateTes();

		// String link =
		// "http://api.wunderground.com/api/855ee74161c29524/key/geolookup/conditions/lang:en/q/autoip.json?geo_ip=176.106.1.162";
		//
		// HttpURLConnection request;
		// BufferedReader rd = null;
		// StringBuilder response = null;
		//
		// try {
		// URL url = new URL(link);
		//
		// request = (HttpURLConnection) url.openConnection();
		// request.setRequestMethod("GET");
		//
		// request.connect();
		//
		// System.out.println("--- done");
		//
		// rd = new BufferedReader(new
		// InputStreamReader(request.getInputStream()));
		// response = new StringBuilder();
		// String line = null;
		// while ((line = rd.readLine()) != null){
		// response.append(line + '\n');
		// }
		//
		// System.out.println("--received: " + response.toString());
		//
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private static void restTemplateTes() {

		String link = "http://api.wunderground.com/api/855ee74161c29524/key/geolookup/conditions/lang:en/q/autoip.json?geo_ip=176.106.1.162";

		RestTemplate rest = new RestTemplate();
		rest.getForObject(link, SomeResponse.class);
	}

	class SomeResponse {

	}

}
