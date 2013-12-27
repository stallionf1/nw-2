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
    $("#showPreviousNewsButton").click(function(){
      $.ajax({
        url: "load_previous_news",
        type: "POST",
        cache: true,
        data: {currentNewsId: '${newsObject.news_id}'}, 
        dataType: "html",
        success: function(newsObject){   
         	 $("#news_item").append("<b>Response</b>");         	
       	 pageId++;
        }
      });
    });
});

$(document).ready(function(){
    $("#showNextNewsButton").click(function(){
      $.ajax({
        url: "load_next_news",
        type: "POST",
        cache: true,
        data: {currentNewsId: '${newsObject.news_id}'}, 
        dataType: "html",
        success: function(newsObject){   
       	 $("#news-item").html("<b>content changed! by NEXT</b>");
       	 pageId++;
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
		<br/>
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
		<div id="controls">
			<%
				//int index = Integer.valueOf(request.getParameter("page_index"));
				if (1> 0) {
			%>
				<button id="showPreviousNewsButton">previous</button>
			<% } %>
			<button id="showNextNewsButton">next</button>				
		</div>
	</div>
</body>
</html>