<%@page import="wifiset.Bookmark"%>
<%@page import="wifiset.BookmarkService"%>
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
		String bookName = request.getParameter("bookName");
		int bookOrder = Integer.parseInt(request.getParameter("bookOrder"));
		BookmarkService bookmarkService = new BookmarkService();
		bookmarkService.addBookGroup(bookName, bookOrder);
	%>
	<script>
        alert("북마크 그룹 정보를 추가하였습니다.");
        window.opener.location.href = "bookmark-group.jsp";
        window.close();
        
    </script>
</body>
</html>