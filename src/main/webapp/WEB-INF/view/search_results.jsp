<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search</title>

  <!--JQUERY-->
  <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script type="text/javascript">
  var pageId = 2;
  $(document).ready(function(){
     $("#loadMoreButton").click(function(){
       $.ajax({
         url: "load_more_search_results",
         type: "GET",
         cache:true,
         data: {data:pageId}, 
         dataType:"html",
         success: function(newsObject){   
          	 $("#searched_news").append(newsObject);
        	 pageId++;
         }
       });
     });
    });
  </script>
 
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
                <input name="searchParam" class="q" type="text" value="${searchParam}" placeholder="Search"/>
                <input type="hidden" name="country" value="ua">                
            </div>
            <button class="cur-p searchBtn" type="submit" value="">Search</button>
        </form>
    	<TABLE BORDER="0">
				<TR>
					<c:forEach items="${menuItemsList}" var="menuItem">
						<TD>
							<form method="post" action="search"> 
								<input type="hidden" name="menuItemParam"   value="${menuItem.url}" />
								<input type="submit" name="${menuItem.url}" value="${menuItem.name}" />
							</form>
						</TD>
					</c:forEach>
				</TR>
			</TABLE>
   <br/>
   </div>
   <div id="searched_news" class="news">
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
		<c:choose>
			<c:when test="${mainNewsList.size() > 5}">
				<button id="loadMoreButton">Show more results</button>
					<div id="moreSearchResults">
				</div>
			</c:when>
		</c:choose>		
	</div>
</body>
</html>