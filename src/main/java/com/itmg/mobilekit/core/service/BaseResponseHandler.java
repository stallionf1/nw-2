package com.itmg.mobilekit.core.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;

public class BaseResponseHandler {

	private String customDataName;
	
	public BaseResponseHandler(String customString) {
		this.customDataName = customString;
	}
	
	public BaseResponseHandler() {
		
	}
	
	public String getCustomDataName() {
		return customDataName;
	}

	public Reader initReaderFromResponse(HttpResponse response) throws IllegalStateException, IOException {
		HttpEntity entity = response.getEntity();

		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();
		Reader reader = new InputStreamReader(entity.getContent(), charset);

		return reader;
	}
	
}
