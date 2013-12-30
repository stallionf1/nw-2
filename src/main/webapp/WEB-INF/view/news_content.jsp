<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NewsHub.org - News Content</title>

<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>	
<script type="text/javascript">

$(document).ready(function(){
	
    $("#show_next_button").click(function(){
      $.ajax({
        url: "load_next_news",
        type: "POST",
        cache: false,     
        dataType: "html",
        success: function(newsObject){   
         	 $("#news_item").html(newsObject);
        }
      });
    });
});

$(document).ready(function(){
    $("#show_previous_button").click(function(){
      $.ajax({
        url: "load_previous_news",
        type: "GET",
        cache: true,
        data: {currentNewsId: '${newsObject.news_id}'}, 
        dataType: "html",
        success: function(newsObject){   
       	 $("#news_item").html(newsObject);
        }
      });
    });
});   

</script>
</head>
<body>
	<div id="container">		
		<button onclick="window.history.go(-1)">Back</button>	
			
		<div id="news_item" class="news-item">
		<input type="hidden" name="newsId" value="${currentNewsId}"/>
		<br/>
		<img class="left" width="140" src="<c:out value="${newsObject.img_src}" />"
				alt="<c:out value="${newsObject.img_alt}" />" />
			
			<span class="date block"> <c:out value="${newsObject.date_updated}" /></span>
			 <p><b> 
			 	<c:out value="${newsObject.news_title}" />
			 	</b></p>
			<p>
				<span><c:out value="${newsObject.news_content}" /></span>
			</p>

			<c:if test="${!newsObject.parsed}">
				<a href="${newsObject.news_url}"> read more</a>
			</c:if>

		</div>
		
		<div id="controls">
			<%
				//int index = Integer.valueOf(request.getParameter("page_index"));
				if (1> 0) {
			%>
				<button id="show_previous_button">previous</button>
			<% } %>
			<button id="show_next_button">next</button>				
		</div>
	</div>
</body>
</html>