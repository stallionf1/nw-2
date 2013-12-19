package com.itmg.mobilekit.ui.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itmg.mobilekit.api.response.NewsContentAO;

@Deprecated
@Controller
public class SimpleController {

	@RequestMapping("/weather")
	public ModelAndView checkWeather() {
		System.out.println("--Calling simple controller ----");
		return new ModelAndView("WeatherView");
	}

	@RequestMapping("/something")
	public ResponseEntity<String> handle(
			@RequestParam("entered_ip") String entered_ip, ModelMap modelMap)
			throws UnsupportedEncodingException {

		String ip = entered_ip;
		System.out.println("------------- entered ip = " + entered_ip);

		return new ResponseEntity<String>("WeatherView", HttpStatus.OK);
	}

	@RequestMapping("/weather_header")
	public ResponseEntity<String> headerCheck(HttpServletRequest request)
			throws UnsupportedEncodingException {

		System.out.println("--- headers -- " + request.getHeaderNames());

		Enumeration e = request.getHeaderNames();

		while (e.hasMoreElements()) {
			String headers = (String) e.nextElement();
			if (headers != null) {
				System.out.println("--- header: " + headers);

			}
		}

		return new ResponseEntity<String>(request.getHeaderNames().toString(),
				HttpStatus.OK);
	}

	@RequestMapping("/render_html")
	public ResponseEntity<String> simpleHtmlResponse(HttpServletRequest req,
			HttpServletResponse response) {

		StringBuilder sb = new StringBuilder();
		sb.append("<h1>THIS IS P1 tag </h1>");
		sb.append("<h4>THIS IS P4 tag </h4>");

		// PrintWriter out;
		// try {
		// out = response.getWriter();
		// out.write("<h1>THIS IS P1 tag </h1>");
		// out.write("<h4>THIS IS P4 tag </h4>");
		// out.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
	}

	@RequestMapping("/check_converter")
	public ResponseEntity<String> checkClientsIp(HttpServletRequest req) {

		String link = "http://api.wunderground.com/api/855ee74161c29524/key/geolookup/conditions/lang:en/q/autoip.json?geo_ip=176.106.1.162";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = "failed....";
		try {
			HttpGet httpget = new HttpGet(link);

			System.out.println("executing request " + httpget.getURI());

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
			// System.out.println("----------------------------------------");
			// System.out.println(responseBody);
			// System.out.println("----------------------------------------");

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		SomeResponse resp = mapper.convertValue(responseBody,
				SomeResponse.class);

		System.out.println("----- trying to map from JSON = " + resp);

		// return new ResponseEntity<String>(xx.toString(), HttpStatus.OK);
		return new ResponseEntity<String>(responseBody, HttpStatus.OK);

	}

	class SomeResponse {
		Object response;

		public String toString() {
			return "some response string";
		}
	}

	// http://newshubtest.org/api/getMainPageNews?fullContent=no&pageID=2&countryCode=ua&accessToken=ec5e7622a39ba5a09e87fabcce102851

	@RequestMapping("/main_news")
	public ResponseEntity<String> mainNews(HttpServletRequest req) {

	//	String link = "http://newshubtest.org/api/getMainPageNews?fullContent=no&pageID=2&countryCode=ua&accessToken=ec5e7622a39ba5a09e87fabcce102851";
		String link = "http://newshubtest.org/api/getDetailedNewsContent?accessToken=ec5e7622a39ba5a09e87fabcce102851&newsID=1547";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//httpclient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		
		
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

			System.out.println("-------------------------- received response \n" + responseBody);
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		NewsContentAO resp = null;
		try {
			resp = mapper.readValue(responseBody, NewsContentAO.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("----- trying to map from JSON = " + resp);
		
		HttpHeaders h = new HttpHeaders();
		h.add("Content-type", "text/html;charset=UTF-8");
	//	return new ResponseEntity<String>("Привет мир",h ,HttpStatus.OK);
		
		return new ResponseEntity<String>(resp.toString(), h,  HttpStatus.OK);
	}

}