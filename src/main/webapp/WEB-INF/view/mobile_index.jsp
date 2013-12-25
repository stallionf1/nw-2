<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NewsHub.org</title>
 
</head>
<body>

<%
       request.setCharacterEncoding("UTF-8");
       
     %>

	<div id="container">
	<div id="controls">
	<c:forEach items="${menuItemsList}" var="menuItem">
 		<form method="post"  action="">
   			<input type="hidden" name="menuItemParam" value="${menuItem.url}"/>
   				<input type="submit" name="${menuItem.url}" value="${menuItem.name}" />
  		</form>        
	</c:forEach>
		
		<br/>
		<form method="post" action="" name="countryForm">
			<select id="countryItem" name="countryItemParam" onchange="this.form.submit()">
				<c:forEach var="countryItem" items="${countryItemsList}">
					<option value="<c:out value="${countryItem.code}" />"><c:out
							value="${countryItem.name}" />
				</c:forEach>
			</select>
		</form>

   </div>
    <div class="search clearfix">
        <form action="search" method="GET" name="searchForm" accept-charset="UTF-8">
            <div class="search-inp left">
                <input name="searchParam" class="q" type="text" value="" placeholder="Шукати"/>
                <input type="hidden" name="country" value="ua">                
            </div>
            <button class="cur-p searchBtn" type="submit" value="">do search</button>
        </form>
    </div>
   
		<div class="news">
			<c:forEach var="newsObject" items="${mainNewsList}">
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