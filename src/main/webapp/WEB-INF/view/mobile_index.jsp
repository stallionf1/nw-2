<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NewsHub.org</title>
</head>
<body>


 
		<form:select path="menuItem">
			<form:options items="${menuItemsList}" var="menuItem" itemValue="name" itemLabel="name" />
		</form:select>
		 
		 <form:select path="countryItem">
			<form:options items="${countryItemsList}" var="countryItem" itemValue="name" itemLabel="name" />
		</form:select>
		 
	 

</body>
</html>