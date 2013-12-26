<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function yHandler(){

	var wrap = document.getElementById('wrap');
	var contentHeight = wrap.offsetHeight;
	var yOffset = window.pageYOffset; 
	var y = yOffset + window.innerHeight;
	if(y >= contentHeight){
		// Ajax call to get more dynamic data goes here
		wrap.innerHTML += '<div class="newData"></div>';
	}
	var status = document.getElementById('status');
	status.innerHTML = contentHeight+" | "+y;
}
window.onscroll = yHandler;
</script>

<style type="text/css">
div#status{position:fixed; font-size:24px;}
div#wrap{width:800px; margin:0px auto;}
div.newData{height:1000px; background:#09F; margin:10px 0px;}
</style>
</head>
<body>
<div id="status">0 | 0</div>
<div id="wrap"><img src="temp9.jpg"></div>
</body>
</html>