<%@page import="wifiset.WifiService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.load-page{
		text-align: center;
	}
</style>
</head>
<body>
	<% 	
		WifiService wifiService = new WifiService();
		int totalCount = wifiService.loadWifi();
	%>
	<div class="load-page">
		<h1><%= totalCount%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
		<a href = main.jsp>홈으로 가기</a>
	</div>
</body>
</html>