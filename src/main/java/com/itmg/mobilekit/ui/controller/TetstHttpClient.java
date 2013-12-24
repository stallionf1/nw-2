package com.itmg.mobilekit.ui.controller;

import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;

@Deprecated
public class TetstHttpClient {

	public static void main(final String[] args) throws Exception {
		
		String test = "http://uaddd.newshub.org";
		
		String code = test.substring(test.indexOf("//") +2, test.indexOf("."));
		
		System.out.println("--code = "+code);
		
		
		String menuItem = "http://ua.newshub.org/business";
		
		int lenght = menuItem.length();
		int x = menuItem.lastIndexOf("/");
		
		String res = menuItem.substring(x+1, lenght);
		System.out.println("--- menu item = " + res);
		
		
//		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
//		CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();
//		
// 		
//		try {
//			httpclient.start();
////			final HttpGet[] requests = new HttpGet[] {
////					new HttpGet("http://google.com"),
////					new HttpGet("http://docs.oracle.com"),
////					new HttpGet("http://newshub.org") };
//		
//			MyGet[] customGetList = new MyGet[] {
//					new MyGet(new HttpGet("http://google.com"), "Type_1"),
//					new MyGet(new HttpGet("http://ya.com"), "Type_2"),
//					new MyGet(new HttpGet("http://habrahabr.com"), "Type_3"),
//					new MyGet(new HttpGet("http://newshub.org"), "Type_4"),
//			};
//			
//			final CountDownLatch latch = new CountDownLatch(customGetList.length);
//			
//		
////			for (final HttpGet request : requests) {
////				httpclient.execute(request, new FutureCallback<HttpResponse>() {
////
////					public void completed(final HttpResponse response) {
////					
////						
////						latch.countDown();
////						System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
////						System.out.println("--response:"+ response.getEntity().toString());
////					}
////
////					public void failed(final Exception ex) {
////						latch.countDown();
////						System.out.println(request.getRequestLine() + "->" + ex);
////					}
////
////					public void cancelled() {
////						latch.countDown();
////						System.out.println(request.getRequestLine()	+ " cancelled");
////					}
////
////				});
////			}
////			
//			
//			for (MyGet _get : customGetList) {
//				MyFutureResponse myResponse = new MyFutureResponse(latch, _get.getGetRequest());
//				
//				Future<HttpResponse> answer = httpclient.execute(_get.getGetRequest(), myResponse);
//				
//			}
//			
//			latch.await();
//			System.out.println("Shutting down");
//		} finally {
//			httpclient.close();
//		}
//		System.out.println("Done");
	}
	
}

class MyGet {
	private HttpGet getRequest;
	private String type;
	
	public MyGet(HttpGet get, String type) {
		this.getRequest = get;
		this.type = type;
	}

	public HttpGet getGetRequest() {
		return getRequest;
	}

	public void setGetRequest(HttpGet getRequest) {
		this.getRequest = getRequest;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}


class MyFutureResponse implements FutureCallback<HttpResponse> {

	private CountDownLatch latch;
	private HttpGet request;
	
	public MyFutureResponse(CountDownLatch latchToCount, HttpGet getMethod) {
		this.latch = latchToCount;
		this.request = getMethod;
	}
	
	@Override
	public void cancelled() {
		latch.countDown();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void completed(HttpResponse response) {
		// TODO Auto-generated method stub
		latch.countDown();
		
		System.out.println("URI="+request.getURI());
		
		System.out.println("--------------- response ------------- ");
		
	}

	@Override
	public void failed(Exception arg0) {
		// TODO Auto-generated method stub
		latch.countDown();
	}
	
}
