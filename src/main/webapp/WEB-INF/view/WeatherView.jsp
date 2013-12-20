<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
	<p>Weather for ip: ${entered_ip}</p>
		<div class="weather-img-block left">
       		 <img class="weather-img" width="40" height="35" src="${imageUrl}" alt="cloudy_icon"/>
   		 </div>
	<div class="weather-info left">
    	<div class="weather-title clearfix">
        	<span class="left">${location}</span>
    	</div>
    
        <div class="degree">${degree}</div>
    </div>
</body>
</html>
