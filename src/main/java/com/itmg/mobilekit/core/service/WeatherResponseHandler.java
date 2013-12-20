package com.itmg.mobilekit.core.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.itmg.mobilekit.api.response.WeatherData;
import com.itmg.mobilekit.core.common.Config;

public class WeatherResponseHandler implements ResponseHandler<WeatherData> {

	private static final Logger logger = Logger.getLogger(WeatherResponseHandler.class);

	@Override
	public WeatherData handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

		logger.debug("Mapping response content Weather from httpResponse.");

		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();

		if (statusLine.getStatusCode() >= 300) {
			logger.error("Error while gettign response from NesHub for Weather. Error code:" + (statusLine.getStatusCode()));
			throw new HttpResponseException(statusLine.getStatusCode(),	statusLine.getReasonPhrase());
		}
		if (entity == null) {
			logger.error("Empty Weather response received.");
			throw new ClientProtocolException("Weather response contains no content");
		}
		String html = EntityUtils.toString(response.getEntity());
		WeatherData weatherData = new WeatherData();
		Document doc = Jsoup.parse(html);
		
		for (Element el : doc.getAllElements()) {
			if (el.className().equals("weather-img")) {				
				weatherData.setImgUrl(String.format("%s%s", Config.getInstance().getWeatherHost(), el.attr("src")));
			}
			if (el.className().equals("degree")) {
				weatherData.setDegree(el.text());
			}
			if (el.className().equals("left")) {
				weatherData.setLocation(el.text());
			}
		}		
		return weatherData;
	}
}
