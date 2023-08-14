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
		String mgrNo = request.getParameter("mgrNo");
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		AddService addService = new AddService();
		addService.addBook(bookId, mgrNo);
	%>
	<script>
        alert("북마크를 추가하였습니다.");
        window.opener.location.href = "bookmark-list.jsp";
        window.close();
        
    </script>
</body>
</html>