<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NewsHub.org</title>

<style>
body {
	background-color: #eee;
	font: helvetica;
}

#container {
	width: 500px;
	background-color: #fff;
	margin: 30px auto;
	padding: 30px;
	border-radius: 5px;
	box-shadow: 5px;
}

.green {
	font-weight: bold;
	color: green;
}

.message {
	margin-bottom: 10px;
}

label {
	width: 70px;
	display: inline-block;
}

form {
	line-height: 160%;
}

.hide {
	display: none;
}
</style>

</head>
<body>

	<div id="container">
		<form method="get" action="index" name="menuForm">
			<select id="menuItem" name="menuItemParam" onchange="this.form.submit()">
				<c:forEach var="menuItem" items="${menuItemsList}">
					<option value="<c:out value="${menuItem.url}" />"><c:out
							value="${menuItem.name}" />
				</c:forEach>
			</select>
		</form>

		<form method="get" action="index" name="countryForm">
			<select id="countryItem" name="countryItemParam" onchange="this.form.submit()">
				<c:forEach var="countryItem" items="${countryItemsList}">
					<option value="<c:out value="${countryItem.code}" />"><c:out
							value="${countryItem.name}" />
				</c:forEach>
			</select>
		</form>

		<div class="news">
			<c:forEach var="newsObject" items="${mainNewsList}">
				<div class="news-item">
					<a href="<c:out value="${newsObject.news_url}" />"
						class="block left"> <img class="left" width="140"
						src="<c:out value="${newsObject.img_src}" />"
						alt="<c:out value="${newsObject.img_alt}" />" />
					</a> <span class="date block"> <c:out
							value="${newsObject.date_updated}" /></span> <a
						href="<c:out value="${newsObject.news_url}" />"
						class="news-title block"> <c:out
							value="${newsObject.news_title}" />
					</a>
					<p>
						<span><c:out value="${newsObject.news_content}" /></span>
					</p>

				</div>
			</c:forEach>

		</div>
	</div>
</body>
</html>