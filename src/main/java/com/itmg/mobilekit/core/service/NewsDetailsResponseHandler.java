package com.itmg.mobilekit.core.service;

import java.io.IOException;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itmg.mobilekit.api.response.NewsContentAO;

public class NewsDetailsResponseHandler extends BaseResponseHandler implements ResponseHandler<NewsContentAO> {

	
private final static Logger logger = Logger.getLogger(CategoriesResponseHandler.class);
	
	public NewsDetailsResponseHandler(String jsonArguments) {
		super(jsonArguments);
	}
	
	public NewsDetailsResponseHandler() {
		super();
	}

	@Override
	public NewsContentAO handleResponse(final HttpResponse response) throws IOException {
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

		//TODO: this is to much...
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(reader);
		
		NewsContentAO detailedNews = gson.fromJson(object, NewsContentAO.class);

		logger.debug(String.format("Received %s news details",detailedNews.getNews_id()));
		return detailedNews;
	}
	
}
