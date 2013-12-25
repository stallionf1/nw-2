package com.itmg.mobilekit.core.service;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.itmg.mobilekit.api.response.NewsContentAO;

public class NewsResponseHandler extends BaseResponseHandler implements ResponseHandler<List<NewsContentAO>>{

	public NewsResponseHandler(String jsonArguments) {
		super(jsonArguments);
	}
	
	public NewsResponseHandler() {
		super();
	}
	
	  @Override
	    public  List<NewsContentAO> handleResponse(final HttpResponse response) throws IOException {
	        StatusLine statusLine = response.getStatusLine();
	        HttpEntity entity = response.getEntity();
	        if (statusLine.getStatusCode() >= 300) {
	            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
	        }
	        if (entity == null) {
	            throw new ClientProtocolException("Response contains no content");
	        }
	        Gson gson = new GsonBuilder().create();
	        Reader reader = initReaderFromResponse(response);

	        JsonParser parser = new JsonParser();
			JsonObject object = (JsonObject) parser.parse(reader);
	        JsonArray countries = object.getAsJsonArray(getCustomDataName());
			
			Type contriesAoType = new TypeToken<List<NewsContentAO>>(){}.getType();
			List<NewsContentAO> parsedList = gson.fromJson(countries, contriesAoType);
			
			ListIterator<NewsContentAO> litr = parsedList.listIterator();
			while (litr.hasNext()) {
				NewsContentAO element = litr.next();
				if (element.isParsed()) {
					String shortUrl = extractShortUrlFromUrl(element.getNews_url());
					element.setShort_url(shortUrl);
					litr.set(element);
				}
			}
		      
			return parsedList;
	        
	    }
	  private String extractShortUrlFromUrl(String menuItem) {
			
			int lenght = menuItem.length();
			int x = menuItem.lastIndexOf("/");
			
			String res = menuItem.substring(x+1, lenght);
			System.out.println("--- short URL = " + res);
			return res;
		}
}
