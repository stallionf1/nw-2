package com.itmg.mobilekit.service;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

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
import com.itmg.mobilekit.api.response.MenuItemAO;

public class MenuItemsResponseHandler extends BaseResponseHandler implements ResponseHandler<List<MenuItemAO>> {

	 @Override
	    public  List<MenuItemAO> handleResponse(final HttpResponse response) throws IOException {
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
	        JsonArray countries = object.getAsJsonArray("menu_items");
			
			Type contriesAoType = new TypeToken<List<MenuItemAO>>(){}.getType();
			List<MenuItemAO> parsedList = gson.fromJson(countries, contriesAoType);
			
			return parsedList;
	        
	    }

	
}
