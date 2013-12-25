<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search</title>
 
</head>
<body>

<%
	request.setCharacterEncoding("UTF-8");
%>

<div id="container">
<button onclick="window.history.go(-1)">Back</button>
<br/>	
    <div class="search clearfix">
        <form action="search" method="GET" name="searchForm" accept-charset="UTF-8">
            <div class="search-inp left">
                <input name="searchParam" class="q" type="text" value="" placeholder="Search"/>
                <input type="hidden" name="country" value="ua">                
            </div>
            <button class="cur-p searchBtn" type="submit" value="">Search</button>
        </form>
    </div>
   <br/>
		<div class="news">
			<c:forEach var="newsObject" items="${searchResultNewsList}">
				<div class="news-item">
					<a href="<c:out value="${newsObject.short_url}" />"
						class="block left"> <img class="left" width="140"
						src="<c:out value="${newsObject.img_src}" />"
						alt="<c:out value="${newsObject.img_alt}" />" />
					</a> 
					<span class="date block"> <c:out value="${newsObject.date_updated}" /></span>
					 <a	href="<c:out value="${newsObject.short_url}" />" class="news-title block">
					 	 <c:out value="${newsObject.news_title}" />
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