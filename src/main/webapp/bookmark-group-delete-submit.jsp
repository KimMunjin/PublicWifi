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
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		BookmarkService bookmarkService = new BookmarkService();
		bookmarkService.deleteBookGroup(bookId);
	%>
	<script>
        alert("북마크 그룹 정보를 삭제하였습니다.");
        window.opener.location.href = "bookmark-group.jsp";
        window.close();
        
    </script>
</body>
</html>