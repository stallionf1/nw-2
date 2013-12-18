package com.itmg.mobilekit.core.service;

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
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.itmg.mobilekit.api.response.CategoryAO;

public class CategoriesResponseHandler extends BaseResponseHandler implements ResponseHandler<List<CategoryAO>>{

	private final static Logger logger = Logger.getLogger(CategoriesResponseHandler.class);
	
	public CategoriesResponseHandler(String jsonArguments) {
		super(jsonArguments);
	}
	
	public CategoriesResponseHandler() {
		super();
	}

	@Override
	public List<CategoryAO> handleResponse(final HttpResponse response) throws IOException {
		logger.debug("Mapping response content from httpResponse.");
		
		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		
		if (statusLine.getStatusCode() >= 300) {
			logger.error("Error while gettign response from NesHub. Error code:" + (statusLine.getStatusCode()));
			throw new HttpResponseException(statusLine.getStatusCode(),statusLine.getReasonPhrase());
		}
		if (entity == null) {
			logger.error("Empty response received.");
			throw new ClientProtocolException("Response contains no content");
		}
		
		Gson gson = new GsonBuilder().create();
		Reader reader = initReaderFromResponse(response);

		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(reader);
		JsonArray categories = object.getAsJsonArray(getCustomDataName());

		Type contriesAoType = new TypeToken<List<CategoryAO>>() {}.getType();
		List<CategoryAO> parsedList = gson.fromJson(categories, contriesAoType);

		logger.debug(String.format("Received %d number of categories records.", parsedList.size()));
		return parsedList;
	}
	
}
