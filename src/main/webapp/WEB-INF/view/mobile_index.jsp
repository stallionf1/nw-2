<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<TABLE BORDER="0">
				<TR>
					<c:forEach items="${menuItemsList}" var="menuItem">
						<TD>
							<form method="post" action="">
								<input type="hidden" name="menuItemParam"
									value="${menuItem.url}" /> <input type="submit"
									name="${menuItem.url}" value="${menuItem.name}" />
							</form>
						</TD>
					</c:forEach>
				</TR>
			</TABLE>
			<br />
			<form method="post" action="" name="countryForm">
				<select id="countryItem" name="countryItemParam"
					onchange="this.form.submit()">
					<c:forEach var="countryItem" items="${countryItemsList}">
						<option value="<c:out value="${countryItem.code}" />">
							<c:out	value="${countryItem.name}" />
							</option>
					</c:forEach>
				</select>
			</form>

		</div>
		<div class="search clearfix">
			<form action="search" method="GET" name="searchForm" accept-charset="UTF-8">
				<div class="search-inp left">
					<input name="searchParam" class="q" type="text" value="" placeholder="Search" /> 
					<input type="hidden" name="country"	value="ua">
				</div>
				<button class="cur-p searchBtn" type="submit" value="">Search</button>
			</form>
		</div>
		<br />
		
		<div class="top_news"> 
		<p>TOP NEWS</p>
		<!-- Move it to separate jsp page for showing NewsContent -->
			<c:forEach var="newsObject" items="${topNews}">
		<a href="			
	<c:choose>
      <c:when test="${newsObject.parsed == true}">
      	<c:out value="${newsObject.short_url}" />
      </c:when>
      <c:otherwise>
      	<c:out value="${newsObject.news_url}" />      
      </c:otherwise>
	</c:choose>"
						class="block left"> <img class="left" width="140"
						src="<c:out value="${newsObject.img_src}" />"
						alt="<c:out value="${newsObject.img_alt}" />" />
					</a> <span class="date block"> <c:out
							value="${newsObject.date_updated}" /></span>
							
<a href="			
	<c:choose>
      <c:when test="${newsObject.parsed == true}">
      	<c:out value="${newsObject.short_url}" />
      </c:when>
      <c:otherwise>
      	<c:out value="${newsObject.news_url}" />      
      </c:otherwise>
	</c:choose>"
						class="news-title block"> <c:out
							value="${newsObject.news_title}" />
					</a>
					<p>
						<span><c:out value="${newsObject.news_content}" /></span>
					</p>
		</c:forEach>
			<!-- Move it to separate jsp page for showing NewsContent  END -->
		</div>
		
		<div class="news">
			<p>NEWS</p>
			<c:forEach var="newsObject" items="${mainNewsList}">
				<div class="news-item">

<a href="			
	<c:choose>
      <c:when test="${newsObject.parsed == true}">
      	<c:out value="${newsObject.short_url}" />
      </c:when>
      <c:otherwise>
      	<c:out value="${newsObject.news_url}" />      
      </c:otherwise>
	</c:choose>"
						class="block left"> <img class="left" width="140"
						src="<c:out value="${newsObject.img_src}" />"
						alt="<c:out value="${newsObject.img_alt}" />" />
					</a> <span class="date block"> <c:out
							value="${newsObject.date_updated}" /></span>
							
<a href="			
	<c:choose>
      <c:when test="${newsObject.parsed == true}">
      	<c:out value="${newsObject.short_url}" />
      </c:when>
      <c:otherwise>
      	<c:out value="${newsObject.news_url}" />      
      </c:otherwise>
	</c:choose>"
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