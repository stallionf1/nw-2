package com.itmg.mobilekit.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.common.Constants;
import com.itmg.mobilekit.service.exception.MobileKitServiceException;

public class MobileKitAPIServiceImpl implements MobileKitAPIService {

	private final static Logger logger = LoggerFactory.getLogger(MobileKitAPIServiceImpl.class);
	
	@Override
	public List<CountryAO> listAllCountries() throws MobileKitServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NewsContentAO> listMainNews(String countryCode, String pageID,
			String fullContent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NewsContentAO> getDetailedNewsContent(String newsID)throws MobileKitServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadHomePageContent() throws MobileKitServiceException {
		logger.debug("Start loading content for main page.");
		
		final Map<APITypes, HttpResponse> responsesDataMap = new HashMap<APITypes, HttpResponse>();

		// 1. Get Countries
		// 2. Get MenuItems
		// 3. Get Slider News
		// 4. Get Main News
		// 5. Get ReferencedItems

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		CloseableHttpAsyncClient asyncHttpClient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();
	
		try {
			asyncHttpClient.start();
			List<HttpGet> requestsList = getMainPageDataRequestsList();

			final CountDownLatch countLatch = new CountDownLatch(requestsList.size());

			for (final HttpGet apiRequest : requestsList) {
				asyncHttpClient.execute(apiRequest, new FutureCallback<HttpResponse>() {
							@Override
							public void failed(Exception ex) {
								countLatch.countDown();
								logger.error("Failed to execute request: %s. Nested exception is: %s", apiRequest, ex);
							}
							@Override
							public void completed(HttpResponse result) {
								logger.debug("Request %s completed.", apiRequest);
								if (requestSuccess(result)) {
									
									APITypes matchedApiMethodFromResponse = matchApiFromRequest(apiRequest);
									
									responsesDataMap.put(matchedApiMethodFromResponse, result);
									
									try {
										readJSONfromResponse(result);
									} catch (IllegalStateException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (MobileKitServiceException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
								countLatch.countDown();
							}

							@Override
							public void cancelled() {								
								countLatch.countDown();
							}
						});
			}
			countLatch.await();
			logger.debug("HttGet finished all requests.");
	
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} finally {
			try {
				asyncHttpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean requestSuccess(HttpResponse result) {
		return result.getStatusLine().getStatusCode() >= 200 && result.getStatusLine().getStatusCode() < 300;
	}
	
	private Sample readJSONfromResponse(HttpResponse response) throws MobileKitServiceException, IllegalStateException, IOException {
		Reader reader = initReaderFromResponse(response);
		
		Gson gson = new GsonBuilder().create();
		
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(reader);
		JsonArray countries = object.getAsJsonArray("countries");
		
		
		Type contriesAoType = new TypeToken<List<CountryAO>>(){}.getType();
		List<CountryAO> parsedList = gson.fromJson(countries, contriesAoType);
		
		System.out.println("parsed json object is: " + parsedList);
		
		System.out.println("parsed count is: " + parsedList.size());
		return null;

	}
	
	private Reader initReaderFromResponse(HttpResponse response) throws IllegalStateException, IOException {
		HttpEntity entity = response.getEntity();

		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();
		Reader reader = new InputStreamReader(entity.getContent(), charset);

		return reader;
	}
	
	
	private List<HttpGet> getMainPageDataRequestsList() {

		String url = Constants.NEWS_HUB_API_URL + Constants.COUNTRIES_API_NAME + "?" + Constants.NEWS_HUB_TOKEN;

	

		List<HttpGet> list = new ArrayList<HttpGet>();
		list.add(new HttpGet(url));

		return list;
	}

	private APITypes matchApiFromRequest(HttpGet request) {

		String uri = request.getRequestLine().getUri();
		
		//TODO: delete it and do nice!
		if (uri.contains(Constants.COUNTRIES_API_NAME)) return APITypes.GET_COUNTRIES;
		else if (uri.contains(Constants.MENU_ITEMS_API_NAME))  return APITypes.GET_MENU_ITEMS;
		else if (uri.contains(Constants.SLIDE_NEWS_API_NAME))  return APITypes.GET_SLIDER_NEWS;
		else if (uri.contains(Constants.REFERENCED_ITEMS_API_NAME))  return APITypes.GET_REFERENSED_ITEMS;
		
		return APITypes.NOT_SPECIFIED;
	}

	private void test() {
		String link = "http://newshubtest.org/api/getDetailedNewsContent?accessToken=ec5e7622a39ba5a09e87fabcce102851&newsID=1547";

		CloseableHttpClient httpclient = HttpClients.createDefault();

		String responseBody = "failed....";
		try {
			HttpGet httpget = new HttpGet(link);
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};

			responseBody = httpclient.execute(httpget, responseHandler);

		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}
	}
}

class Sample {
	List<CountryAO> countries;

	public List<CountryAO> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryAO> countries) {
		this.countries = countries;
	}

	@Override
	public String toString() {
		return "Sample [countries=" + countries + "]";
	}
	
	
	
}
