package com.itmg.mobilekit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import com.itmg.mobilekit.api.response.APIResponseObject;
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
	public List<NewsContentAO> listMainNews(String countryCode, String pageID, String fullContent) {
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

		CloseableHttpAsyncClient asyncHttpClient = HttpAsyncClients.createDefault();

		try {
			asyncHttpClient.start();
			List<HttpGet> requestsList = getMainPageDataRequestsList();

			final CountDownLatch countLatch = new CountDownLatch(requestsList.size());

			for (final HttpGet apiRequest : requestsList) {
				asyncHttpClient.execute(apiRequest, new FutureCallback<HttpResponse>() {

							@Override
							public void failed(Exception ex) {
								ex.printStackTrace();
								// ------
								countLatch.countDown();
							}

							@Override
							public void completed(HttpResponse result) {
								
								String matchedApiMethodFromResponse = matchApiFromRequest(apiRequest);
								
								responsesDataMap.put(matchedApiMethodFromResponse, result);
								
								countLatch.countDown();
							}

							@Override
							public void cancelled() {
								countLatch.countDown();
							}
						});
			}
			countLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				asyncHttpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private List<HttpGet> getMainPageDataRequestsList() {
		
		
		String url = Constants.NEWS_HUB_API_URL+Constants.COUNTRIES_API_NAME+"?"+Constants.NEWS_HUB_TOKEN;
		
		System.out.println("---------------- calling URL = "+url);
		
		List<HttpGet> list = new ArrayList<HttpGet>();
		list.add(new HttpGet(url));
				
		return new ArrayList<HttpGet>();
	}
	
	private String matchApiFromRequest(HttpGet request) {
	
		String uri = request.getRequestLine().getUri();
		
		System.out.println("*************************************\n"+uri);
		
		String type = "not_specified";
		
		for (String apiName : Constants.API_METHODS) {
			
		}
		
		
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

				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
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
