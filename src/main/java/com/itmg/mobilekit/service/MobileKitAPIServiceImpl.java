package com.itmg.mobilekit.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MobileKitAPIServiceImpl implements MobileKitAPIService {
	
	@Override
	public void listAllCountries() {
		// TODO Auto-generated method stub
		System.out.println("--- asked to get list of countries ----- ");
	}

	@Override
	public void listMainNews() {
		// TODO Auto-generated method stub
		System.out.println("--- asked to get list of all main news ----- ");
		
		
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
