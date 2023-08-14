<%@page import="wifiset.BookAddWifi"%>
<%@page import="wifiset.AddService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%	
		int bookAddNum = Integer.parseInt(request.getParameter("bookAddNum"));
		AddService addService = new AddService();
		addService.deleteBook(bookAddNum);
	%>
	<script>
        alert("북마크 정보를 삭제하였습니다.");
        window.opener.location.href = "bookmark-list.jsp";
        window.close();
        
    </script>
</body>
</html>