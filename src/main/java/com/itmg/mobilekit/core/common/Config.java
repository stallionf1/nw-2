package com.itmg.mobilekit.core.common;

import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

@ConfigureMe(name="newshub_config")
public class Config {

	private static final Logger logger = Logger.getLogger(Config.class);
	private static final Config instance = new Config();
	
	@Configure private String host;
	@Configure private String api_suffix;
	@Configure private String token;
	@Configure private String forwardHeader;
	@Configure private String weatherHost; 
	@Configure private String weatherAPI;
	@Configure private String usersDeviceLocale;

	
	private Config() {
		try {
			ConfigurationManager.INSTANCE.configure(this);
		} catch (IllegalArgumentException ex) {
			logger.fatal("Failed to read configuration file!", ex);
		}
	}
	
	public static Config getInstance() {
		return instance;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getApi_suffix() {
		return api_suffix;
	}
	public void setApi_suffix(String api_suffix) {
		this.api_suffix = api_suffix;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}	
	public String getForwardHeader() {
		return forwardHeader;
	}
	public void setForwardHeader(String forwardHeader) {
		this.forwardHeader = forwardHeader;
	}
	public String getWeatherHost() {
		return weatherHost;
	}
	public void setWeatherHost(String weatherHost) {
		this.weatherHost = weatherHost;
	}
	public String getWeatherAPI() {
		return weatherAPI;
	}
	public void setWeatherAPI(String weatherAPI) {
		this.weatherAPI = weatherAPI;
	}
	
	public String getUsersDeviceLocale() {
		return usersDeviceLocale;
	}

	public void setUsersDeviceLocale(String usersDeviceLocale) {
		this.usersDeviceLocale = usersDeviceLocale;
	}

	public static void main(String[] args) {
		System.out.println("host="+Config.getInstance().getHost());
		System.out.println("api_suffix="+Config.getInstance().getApi_suffix());
		System.out.println("token="+Config.getInstance().getToken());
		System.out.println("Header="+Config.getInstance().getForwardHeader());
	}
	
}
