package com.itmg.mobilekit.api.response;

import java.util.Date;

public class NewsContentAO extends APIResponseObject {

	private String news_id;
	private String news_url;
	private String img_src;
	private String img_alt;
	private String date_updated;
	private String news_title;
	private String news_content;	
	private boolean parsed;
	private String short_url;
	
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	public String getNews_url() {
		return news_url;
	}
	public void setNews_url(String news_url) {
		this.news_url = news_url;
	}
	public String getImg_src() {
		return img_src;
	}
	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}
	public String getImg_alt() {
		return img_alt;
	}
	public void setImg_alt(String img_alt) {
		this.img_alt = img_alt;
	}
	public String getDate_updated() {
		return date_updated;
	}
	public void setDate_updated(String date_updated) {
		this.date_updated = date_updated;
	}
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	public boolean isParsed() {
		return parsed;
	}
	public void setParsed(boolean parsed) {
		this.parsed = parsed;
	}	
	public String getShort_url() {
		return short_url;
	}
	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}
	@Override
	public String toString() {
		return "NewsContent [news_id=" + news_id + ", news_url=" + news_url
				+ ", img_src=" + img_src + ", img_alt=" + img_alt
				+ ", date_updated=" + date_updated + ", news_title="
				+ news_title + ", news_content=" + news_content + ", parsed="
				+ parsed + "]";
	}
	
	@Override
	public Date deteReceived() {
		// TODO Auto-generated method stub
		return null;
	}

}
