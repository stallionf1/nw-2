package com.itmg.mobilekit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import com.itmg.mobilekit.api.response.CountryAO;
import com.itmg.mobilekit.api.response.NewsContentAO;
import com.itmg.mobilekit.common.Constants;

public class MobileKitAPIServiceImpl implements MobileKitAPIService {

	@Override
	public List<CountryAO> listAllCountries() {
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
	public List<NewsContentAO> getDetailedNewsContent(String newsID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadHomePageContent() {
		// TODO Auto-generated method stub

		final Map<String, HttpResponse> responsesDataMap = new HashMap<String, HttpResponse>();

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
								System.out.println("!!!!!!!!!!!!  failed !!!!!!!!!");
								// ------
								countLatch.countDown();
							}

							@Override
							public void completed(HttpResponse result) {
								
								
								try {
									System.out.println("---- response received!" + result.getEntity().getContent().toString());
								} catch (IllegalStateException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								System.out.println("----------- code answered="+result.getStatusLine().getStatusCode());
								
								switch (result.getStatusLine().getStatusCode()) {
									case HttpStatus.SC_OK: {
										
										String matchedApiMethodFromResponse = matchApiFromRequest(apiRequest);
										responsesDataMap.put(matchedApiMethodFromResponse, result);
									}
									default: {
										//smth went wrong 
										System.out.println("***** Error...." + result.getStatusLine().getStatusCode());
									}
								}
								
								countLatch.countDown();
							}

							@Override
							public void cancelled() {
								System.out.println("!!!!!!!!!!!!  cancelled !!!!!!!!!");
								countLatch.countDown();
							}
						});
			}
			countLatch.await();
			System.out.println("--- httprequests execution finished ---- \n Result: "+responsesDataMap.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				asyncHttpClient.close();
				System.out.println("---------- CLOSING SYNC HTTP CLIENT ------");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private List<HttpGet> getMainPageDataRequestsList() {

		String url = Constants.NEWS_HUB_API_URL + Constants.COUNTRIES_API_NAME + "?" + Constants.NEWS_HUB_TOKEN;

		System.out.println("---------------- Create httpRequest for URL: " + url);

		List<HttpGet> list = new ArrayList<HttpGet>();
		list.add(new HttpGet(url));

		return list;
	}

	private String matchApiFromRequest(HttpGet request) {

		String uri = request.getRequestLine().getUri();
		String type = "not_specified";
		
		//TODO: delete it and do nice!
		if (uri.contains(Constants.COUNTRIES_API_NAME)) type = Constants.countriesAO;
		else if (uri.contains(Constants.MENU_ITEMS_API_NAME)) type = Constants.menuItemsAO;
		else if (uri.contains(Constants.SLIDE_NEWS_API_NAME)) type = Constants.sliderNewsAO;
		else if (uri.contains(Constants.REFERENCED_ITEMS_API_NAME)) type = Constants.referencedItemsNameAO;
		
		return type;
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
