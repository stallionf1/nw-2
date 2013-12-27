<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NewsHub.org - News Content</title>
</head>
<body>

	<div id="container">		
		<button onclick="window.history.go(-1)">Back</button>		
		<div class="news-item">
		<br/>
			<%-- <a href="<c:out value="${newsObject.news_url}" />" class="block left">
				<img class="left" width="140"
				src="<c:out value="${newsObject.img_src}" />"
				alt="<c:out value="${newsObject.img_alt}" />" />
			</a> 
			
			<span class="date block"> <c:out value="${newsObject.date_updated}" /></span>
			
			 <a href="<c:out value="${newsObject.news_url}" />" class="news-title block"> 
			 	<c:out value="${newsObject.news_title}" />
			</a>
			<p>
				<span><c:out value="${newsObject.news_content}" /></span>
			</p> --%>
			
			 
			<img class="left" width="140" src="<c:out value="${newsObject.img_src}" />"
				alt="<c:out value="${newsObject.img_alt}" />" />
			
			<span class="date block"> <c:out value="${newsObject.date_updated}" /></span>
			 <p><b> 
			 	<c:out value="${newsObject.news_title}" />
			 	</b>
			</p>
			<p>
				<span><c:out value="${newsObject.news_content}" /></span>
			</p>
		</div>
	</div>
</body>
</html>