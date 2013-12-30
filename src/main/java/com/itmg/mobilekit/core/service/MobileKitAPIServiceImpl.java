package com.itmg.mobilekit.core.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.HttpContext;
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
import com.itmg.mobilekit.api.response.WeatherData;
import com.itmg.mobilekit.core.common.Config;
import com.itmg.mobilekit.core.common.Constants;
import com.itmg.mobilekit.core.exception.MobileKitServiceException;

public class MobileKitAPIServiceImpl implements MobileKitAPIService {

	private final static Logger logger = Logger.getLogger(MobileKitAPIServiceImpl.class);
	
	@Override
	public List<CountryAO> listAllCountries(String remoteIp) throws MobileKitServiceException {
	
		logger.debug("Start loading countirs list.");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HeaderHttpGet get = new HeaderHttpGet(getCountriesListUrl(), remoteIp);		
		
		try {
			List<CountryAO> myjson = httpclient.execute(get, new CountriesResponseHandler("countries"));		
			httpclient.close();
			logger.debug(String.format("Finished loading countries with number of records: %d", (myjson != null ? myjson.size() : 0)));
			
			return myjson;
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize http request", e);
			throw new MobileKitServiceException("Failed to initialize http request.",	e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse.", e);
			throw new MobileKitServiceException("Failed to read HttpResponse.",	e);
		}
	}
	
	@Override
	public List<MenuItemAO> listMenuItems(String countryCode, String remoteIp) throws MobileKitServiceException {
		logger.debug("Start loading menu items.");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();	
		HeaderHttpGet get = new HeaderHttpGet(getMenuItemsUrl(countryCode), remoteIp);
		
		try {
			List<MenuItemAO> myjson = httpclient.execute(get, new MenuItemsResponseHandler("menu_items"));
			httpclient.close();
			logger.debug(String.format("Finished loading menu items with number of records: %d", (myjson != null ? myjson.size() : 0)));
			
			return myjson;
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize http request", e);
			throw new MobileKitServiceException("Failed to initialize http request.",	e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse.", e);
			throw new MobileKitServiceException("Failed to read HttpResponse.",	e);
		}	
	}
	
	@Override
	public List<NewsContentAO> listSliderNews(String countryCode, String remoteIp) throws MobileKitServiceException {
		logger.debug("Start loading SliderNews.");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();		
		HeaderHttpGet get = new HeaderHttpGet(getSliderNewsUrl(countryCode), remoteIp);
	
		try {
			List<NewsContentAO> myjson = httpclient.execute(get, new NewsResponseHandler("slider_news"));
			httpclient.close();
			logger.debug(String.format("Finished loading slider news with number of records: %d", (myjson != null ? myjson.size() : 0)));
			
			return myjson;
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize HttpRequest.", e);
			throw new MobileKitServiceException("Failed to initialize HttpRequest.",	e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse.", e);
			throw new MobileKitServiceException("Failed to read HttpResponse.",	e);
		}	
	}

	@Override
	public List<NewsContentAO> listMainNews(String countryCode, String pageID, String fullContent, String remoteIp) throws MobileKitServiceException {
		logger.debug("Start loading MainNews.");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HeaderHttpGet get = new HeaderHttpGet(getMainNewsUrl(countryCode, pageID, fullContent), remoteIp);
		
		try {
			List<NewsContentAO> myjson = httpclient.execute(get, new NewsResponseHandler("main_news"));
			httpclient.close();
			logger.debug(String.format("Finished loading main news with number of records: %d", (myjson != null ? myjson.size() : 0)));
			return myjson;	
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize HttpRequest.", e);
			throw new MobileKitServiceException("Failed to initialize HttpRequest.",	e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse.", e);
			throw new MobileKitServiceException("Failed to read HttpResponse.",	e);
		}	
	}

	@Override
	public List<CategoryAO> loadCategoriesByCountry(String countryCode, String remoteIp) throws MobileKitServiceException {
	
		logger.debug("Start loading Categories.");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HeaderHttpGet get = new HeaderHttpGet(getCategoriesByCountryUrl(countryCode), remoteIp);
		
		try {
			List<CategoryAO> myjson = httpclient.execute(get, new CategoriesResponseHandler("categories"));
			httpclient.close();	
			logger.debug(String.format("Finished loading Categories with number of records: %d", (myjson != null ? myjson.size() : 0)));
			return myjson;
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize HttpRequest.", e);
			throw new MobileKitServiceException("Failed to initialize HttpRequest.",	e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse.", e);
			throw new MobileKitServiceException("Failed to read HttpResponse.",	e);
		}
	}
	
	@Override
	public List<CategoryNewsAO> loadCategoryNewsByCategoryAndCountry(String category, String countryCode, String remoteIp) throws MobileKitServiceException {
		
		logger.debug("Start loading News for Category");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HeaderHttpGet get = new HeaderHttpGet(getCategoryNewsUrl(countryCode, category), remoteIp);
		
		try {
			List<CategoryNewsAO> myjson = httpclient.execute(get, new CategoryNewsResponseHandler("news_links"));
			httpclient.close();
			logger.debug(String.format("Finished loading Categories with number of records: %d", (myjson != null ? myjson.size() : 0)));
			return myjson;
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize HttpRequest.", e);
			throw new MobileKitServiceException("Failed to initialize HttpRequest.",	e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse.", e);
			throw new MobileKitServiceException("Failed to read HttpResponse.",	e);
		}		
	}
	
	@Override
	public List<NewsContentAO> loadNewsByMenuSectionAndCountry(String menuSection, String countryCode, String remoteIp, String pageId, String fullContent)
			throws MobileKitServiceException {
		
		logger.debug("Start loading Menu News.");
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
		HeaderHttpGet get = new HeaderHttpGet(getMenuNewsUrl(menuSection, countryCode, fullContent, pageId), remoteIp);
				
		try {
			List<NewsContentAO> myjson = httpclient.execute(get, new NewsResponseHandler("menu_news"));
			httpclient.close();
			logger.debug(String.format("Finished loading Menu News with number of records: %d", (myjson != null ? myjson.size() : 0)));
			return myjson;
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize HttpRequest.", e);
			throw new MobileKitServiceException("Failed to initialize HttpRequest.",	e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse.", e);
			throw new MobileKitServiceException("Failed to read HttpResponse.",	e);
		}
	}
	
	@Override
	public NewsContentAO loadNewsDetails(String newsID, String countryCode, String remoteIp) throws MobileKitServiceException {
		logger.debug("Start loading Detailed content for news.");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();		
		HeaderHttpGet get = new HeaderHttpGet(getDetailedNewsContentUrl(newsID), remoteIp);
		
		try {
			NewsContentAO myjson = httpclient.execute(get, new NewsDetailsResponseHandler());
			httpclient.close();
			logger.debug("Finished loading DetailedNewsContent.");
			return myjson;
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize HttpRequest.", e);
			throw new MobileKitServiceException("Failed to initialize HttpRequest.",	e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse.", e);
			throw new MobileKitServiceException("Failed to read HttpResponse.",	e);
		}
	}
	
	@Override
	public WeatherData loadWeatherData(String locationIp) throws MobileKitServiceException {
		logger.debug(String.format("Start loading weather data for IP=%s", locationIp));
 
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HeaderHttpGet get = new HeaderHttpGet(
				String.format("%s%s", Config.getInstance().getWeatherHost(), Config.getInstance().getWeatherAPI()), locationIp);
		
		try {
			WeatherData data = httpClient.execute(get, new WeatherResponseHandler());
			httpClient.close();
			logger.debug(String.format("Received weather data: %s", data));
			return data;
		} catch (ClientProtocolException e) {
			logger.error("Failed to receive Weather data.", e);
			throw new MobileKitServiceException("Failed to receive Weather data.", e);
		} catch (IOException e) {
			logger.error("Failed to read weather response.", e);
			throw new MobileKitServiceException("Failed to read weather response.", e);
		}
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
	
	@Override
	public String fetchUsersLocale(String usersIp) throws MobileKitServiceException {
		logger.debug(String.format("Start fetching user's locale for IP:%s", usersIp));
		try {			
			HttpURLConnection con = (HttpURLConnection) (new URL(Config.getInstance().getLocale_host())).openConnection();
			con.setInstanceFollowRedirects(false);
			if (usersIp.equals("127.0.0.1")) {
				usersIp = "176.106.1.193";
			}
			con.addRequestProperty(Config.getInstance().getForwardHeader(), usersIp);
			con.connect();
			
			String code = extractLocaleCode(con.getHeaderField("Location"));
			logger.debug(String.format("Fetched user's country as: %s for IP:%s", code, usersIp));
			con.disconnect();
			
			return code;
		} catch (MalformedURLException e) {
			logger.error("Can not get URL connection for Locale check.", e);
			throw new MobileKitServiceException("Can not get URL connection for Locale check.", e);
		} catch (IOException e) {
			logger.error("Failed to read response from Location URL.", e);
			throw new MobileKitServiceException("Failed to read response from Location URL.", e);
		}
	}
	
	@Override
	public List<NewsContentAO> searchNewsBy(String searchParam, String countryCode, String categoryCode, String pageId, String remoteIp)
			throws MobileKitServiceException {
		logger.debug("Start Searching for news.");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();		
		HeaderHttpGet get = new HeaderHttpGet(getSearchNewsLink(searchParam, countryCode, categoryCode, pageId), remoteIp);
		
		try {
			List<NewsContentAO> myjson = httpclient.execute(get, new NewsResponseHandler("searched_news"));
			httpclient.close();
			logger.debug("Finished searching news.");
			return myjson;
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize HttpRequest.", e);
			throw new MobileKitServiceException("Failed to initialize HttpRequest.",	e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse.", e);
			throw new MobileKitServiceException("Failed to read HttpResponse.",	e);
		}
	}
	
	@Override
	public List<NewsContentAO> getTopNews(String countryCode, String remoteIp) throws MobileKitServiceException {
		logger.debug("Start executing TOP_NEWS request");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();		
		HeaderHttpGet get = new HeaderHttpGet(getTopNewsLink(countryCode), remoteIp);
		
		try {
			List<NewsContentAO> myjson = httpclient.execute(get, new NewsResponseHandler("topnews"));
			httpclient.close();
			logger.debug("Finished loading top news.");
			return myjson;
		} catch (ClientProtocolException e) {
			logger.error("Failed to initialize HttpRequest fro TOP_NEWS.", e);
			throw new MobileKitServiceException("Failed to initialize HttpRequest for TOP_NEWS", e);
		} catch (IOException e) {
			logger.error("Failed to read HttpResponse from TOP_NEWS", e);
			throw new MobileKitServiceException("Failed to read HttpResponse from TOP_NEWS", e);
		}
	}

	private String extractLocaleCode(String location) {
		if (location != null) {
			logger.debug(String.format("Get Location response header as:", location == null ? "NULL" : location));
			return  location.substring(location.indexOf("//") +2, location.indexOf("."));
		} else {
			logger.error("Could not get location header!!!");
			return "ua";
		}
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

		//System.out.println("---------" + menu_items_URL);
		
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
	
	//get url's for API calls.
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
		String url = String.format("%s%s%s%s&countryCode=%s&pageID=%s&fullContent=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/getMainPageNews?", Config.getInstance().getToken(), countryCode, pageID, fullContent);		
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
	
	private String getSearchNewsLink(String searchParam, String countryCode, String categoryCode, String pageId) {
		String url = String.format("%s%s%s%s&searchParam=%s&countryCode=%s&category=%s&pageId=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/searchNewsBy?", Config.getInstance().getToken(), searchParam, countryCode, categoryCode == null ? "all" : categoryCode, pageId);
		
		return url;
	}
	
	private String getTopNewsLink(String countryCode) {
		String url = String.format("%s%s%s%s&countryCode=%s", Config.getInstance().getHost(), Config.getInstance().getApi_suffix(), 
				"/getTopNews?", Config.getInstance().getToken(), countryCode);
		return url;
	}
}

final class HeaderHttpGet extends HttpGet {
	
	private static final Logger logger = Logger.getLogger(HeaderHttpGet.class);
	
	public HeaderHttpGet(String url, String ipToAdd) {
		super(url);
		addClientsIp(ipToAdd);
		logger.debug(String.format("Initialized HttpGet From IP=%s with url:%s ",ipToAdd, url));
	}
	
	private void addClientsIp (String ipToAdd) {
		super.addHeader(Config.getInstance().getForwardHeader(), ipToAdd);
	}
}
