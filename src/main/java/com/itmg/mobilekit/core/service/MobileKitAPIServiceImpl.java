package com.itmg.mobilekit.core.service;

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
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.itmg.mobilekit.api.APITypes;
import com.itmg.mobilekit.api.response.CategoryAO;
import com.itmg.mobilekit.api.response.CategoryNewsAO;
import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.MenuItemAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.core.common.Config;
import com.itmg.mobilekit.core.common.Constants;
import com.itmg.mobilekit.core.exception.MobileKitServiceException;

public class MobileKitAPIServiceImpl implements MobileKitAPIService {

	private final static Logger logger = Logger.getLogger(MobileKitAPIServiceImpl.class);
	
	@Override
	public List<CountryAO> listAllCountries(String remoteIp) throws MobileKitServiceException, ClientProtocolException, IOException {
	
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HeaderHttpGet get = new HeaderHttpGet(getCountriesListUrl(), remoteIp);
		
		List<CountryAO> myjson = httpclient.execute(get, new CountriesResponseHandler("countries"));
		httpclient.close();
		
		return myjson;
	}
	
	@Override
	public List<MenuItemAO> listMenuItems(String countryCode, String remoteIp) throws MobileKitServiceException, ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
	
		HeaderHttpGet get = new HeaderHttpGet(getMenuItemsUrl(countryCode), remoteIp);
		List<MenuItemAO> myjson = httpclient.execute(get, new MenuItemsResponseHandler("menu_items"));
		
		httpclient.close();
		
		return myjson;
	}
	
	@Override
	public List<NewsContentAO> listSliderNews(String countryCode, String remoteIp) throws MobileKitServiceException, ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HeaderHttpGet get = new HeaderHttpGet(getSliderNewsUrl(countryCode), remoteIp);
		List<NewsContentAO> myjson = httpclient.execute(get, new NewsResponseHandler("slider_news"));
		
		httpclient.close();
		
		return myjson;
	}

	@Override
	public List<NewsContentAO> listMainNews(String countryCode, String pageID, String fullContent, String remoteIp) throws MobileKitServiceException, ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HeaderHttpGet get = new HeaderHttpGet(getMainNewsUrl(countryCode, pageID, fullContent), remoteIp);
		List<NewsContentAO> myjson = httpclient.execute(get, new NewsResponseHandler("main_news"));
		
		httpclient.close();
		
		return myjson;	
	}

	@Deprecated
	@Override
	public List<NewsContentAO> getDetailedNewsContent(String newsID, String remoteIp)throws MobileKitServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CategoryAO> loadCategoriesByCountry(String countryCode, String remoteIp) throws MobileKitServiceException, ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HeaderHttpGet get = new HeaderHttpGet(getCategoriesByCountryUrl(countryCode), remoteIp);
		List<CategoryAO> myjson = httpclient.execute(get, new CategoriesResponseHandler("categories"));
		
		httpclient.close();
		
		return myjson;
	}
	
	@Override
	public List<CategoryNewsAO> loadCategoryNewsByCategoryAndCountry(String category, String countryCode, String remoteIp) throws MobileKitServiceException, ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HeaderHttpGet get = new HeaderHttpGet(getCategoryNewsUrl(countryCode, category), remoteIp);
		List<CategoryNewsAO> myjson = httpclient.execute(get, new CategoryNewsResponseHandler("news_links"));
		httpclient.close();
		
		return myjson;
	}
	
	@Override
	public List<NewsContentAO> loadNewsByMenuSectionAndCountry(String menuSection, String countryCode, String remoteIp, String pageId, String fullContent) throws MobileKitServiceException, 
																											  ClientProtocolException,
																											  IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
 
		HeaderHttpGet get = new HeaderHttpGet(getMenuNewsUrl(menuSection, countryCode, fullContent, pageId), remoteIp);
		List<NewsContentAO> myjson = httpclient.execute(get, new NewsResponseHandler("menu_news"));
		httpclient.close();
		
		return myjson;
	
	}
	
	@Override
	public NewsContentAO loadNewsDetails(String newsID, String countryCode, String remoteIp) throws MobileKitServiceException, ClientProtocolException,
			IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HeaderHttpGet get = new HeaderHttpGet(getDetailedNewsContentUrl(newsID), remoteIp);
		NewsContentAO myjson = httpclient.execute(get, new NewsDetailsResponseHandler());
		httpclient.close();
		return myjson;
	}

	@Override
	public  Map<APITypes, Object> loadHomePageContent(String remoteIp) throws MobileKitServiceException {
		logger.debug("Start loading content for main page.");
		
		final Map<APITypes, Object> responsesDataMap = new HashMap<APITypes, Object>();

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
								logger.error("Failed to execute request. Nested exception is: ", ex);
							}
							@Override
							public void completed(HttpResponse result) {
								logger.debug("Request " + apiRequest + "completed.");
								if (requestSuccess(result)) {
									
									APITypes requestedAPI = matchApiFromRequest(apiRequest);
									
									System.out.println("--api method that was processed: " + requestedAPI.getApiTypeDesciprion());
										
									try {
										responsesDataMap.put(requestedAPI, readJSONfromResponse(result, requestedAPI));
									} catch (IllegalStateException e) {
										e.printStackTrace();
									} catch (MobileKitServiceException e) {
										e.printStackTrace();
									} catch (IOException e) {
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
			logger.error("Failed to execute HttpClient requests.", e);
			throw new MobileKitServiceException("Failed to execute HttpClient requests.", e);
		} finally {
			try {
				asyncHttpClient.close();
			} catch (IOException e) {
				logger.error("Failed to close HttpClient", e);
			}
		}
		return responsesDataMap;
	}

	private boolean requestSuccess(HttpResponse result) {
		return result.getStatusLine().getStatusCode() >= 200 && result.getStatusLine().getStatusCode() < 300;
	}
	
	private Object readJSONfromResponse(HttpResponse response, APITypes requestedAPI) throws MobileKitServiceException, IllegalStateException, IOException {
		Reader reader = initReaderFromResponse(response);
		
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(reader);
		Gson gson = new GsonBuilder().create();
		
		//TODO: do smth here....
		switch (requestedAPI) {
			case GET_COUNTRIES: {
				JsonArray countries = object.getAsJsonArray("countries");
			    Type contriesAoType = new TypeToken<List<CountryAO>>(){}.getType();
				List<CountryAO> parsedList = gson.fromJson(countries, contriesAoType);
				return parsedList;
			}
			
			case GET_MENU_ITEMS: {
				JsonArray countries = object.getAsJsonArray("menu_items");				
				Type aoType = new TypeToken<List<MenuItemAO>>(){}.getType();
				List<MenuItemAO> parsedList = gson.fromJson(countries, aoType);				
				logger.debug("Received MenuItems list:"+parsedList);				
				return parsedList;
			}

			case GET_SLIDER_NEWS: {
				JsonArray countries = object.getAsJsonArray("slider_news");
				
				Type aoType = new TypeToken<List<MenuItemAO>>(){}.getType();
				List<MenuItemAO> parsedList = gson.fromJson(countries, aoType);
				
				return parsedList;
			}

			case GET_MAIN_NEWS: {
				JsonArray countries = object.getAsJsonArray("main_news");
				
				Type aoType = new TypeToken<List<MenuItemAO>>(){}.getType();
				List<MenuItemAO> parsedList = gson.fromJson(countries, aoType);
				
				return parsedList;
			}
			case GET_REFERENSED_ITEMS:
				break;
			case NOT_SPECIFIED:
				break;
			default:
				break;
		}
	
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

		String countries_URL = Constants.NEWS_HUB_API_URL + Constants.COUNTRIES_API_NAME + "?" + Constants.NEWS_HUB_TOKEN;
		//String menu_items_URL = Constants.NEWS_HUB_API_URL + Constants.COUNTRIES_API_NAME + "?" + Constants.NEWS_HUB_TOKEN;
		//String slider_news_URL = Constants.NEWS_HUB_API_URL + Constants.COUNTRIES_API_NAME + "?" + Constants.NEWS_HUB_TOKEN;
		String menu_items_URL = generateMenuItemsURL("UA");

		System.out.println("---------" + menu_items_URL);
		
		List<HttpGet> list = new ArrayList<HttpGet>();
		list.add(new HttpGet(countries_URL));
		list.add(new HttpGet(menu_items_URL));

		return list;
	}

	private String generateMenuItemsURL(String countryCode) {
		String menu_items_URL = Constants.NEWS_HUB_API_URL + Constants.MENU_ITEMS_API_NAME + "?" + Constants.NEWS_HUB_TOKEN + "&countryCode="+countryCode;
		
		return menu_items_URL;
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
	
	private String getCountriesListUrl() {
		String url = String.format("%s%s%s%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/getCountriesList?",
				Config.getInstance().getToken());
		return url;
	}
	
	private String getMenuItemsUrl(String countryCode) {
		String url = String.format("%s%s%s%s&countryCode=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/getMenuItems?", Config.getInstance().getToken(), countryCode);
		return url;
	}
	
	private String getSliderNewsUrl(String countryCode) {
		String url = String.format("%s%s%s%s&countryCode=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/getSliderNews?", Config.getInstance().getToken(), countryCode);
		return url;
	}
	
	private String getMainNewsUrl(String countryCode, String pageID, String fullContent) {	
		String url = String.format("%s%s%s%s&countryCode=%s&pageId=%s&fullContent=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/getMainPageNews?", Config.getInstance().getToken(), countryCode, fullContent, pageID);		
		return url;
	}
	
	private String getCategoriesByCountryUrl(String countryCode) {		
		String url = String.format("%s%s%s%s&countryCode=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/getReferencedCategoriesList?", Config.getInstance().getToken(), countryCode);
		return url;
	}
	
	private String getCategoryNewsUrl(String countryCode, String categoryName) {
		String url = String.format("%s%s%s%s&countryCode=%s&categoryName=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/getReferencedNewsByCategory?", Config.getInstance().getToken(), countryCode, categoryName);
		return url;
	}
	
	private String getMenuNewsUrl(String menuName, String countryCode, String fullContent, String pageId) {
		String url = String.format("%s%s%s%s&countryCode=%s&fullContent=%s&menuItem=%s&pageId=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/listNewsByMenuItem?", Config.getInstance().getToken(), countryCode, fullContent, menuName, pageId);
		return url;
	}
	
	private String getDetailedNewsContentUrl(String newsId) {
		String url = String.format("%s%s%s%s&newsID=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/getDetailedNewsContent?", Config.getInstance().getToken(), newsId);
		return url;
	}
}

final class HeaderHttpGet extends HttpGet {
	
	public HeaderHttpGet(String url, String ipToAdd) {
		super(url);
		addClientsIp(ipToAdd);
	}
	
	private void addClientsIp (String ipToAdd) {
		super.addHeader(Config.getInstance().getForwardHeader(), ipToAdd);
	}
}