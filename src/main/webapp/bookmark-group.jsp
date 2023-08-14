<%@page import="wifiset.Bookmark"%>
<%@page import="java.util.List"%>
<%@page import="wifiset.BookmarkService"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
		width: 100%;
	}
	th, td{
		border:solid 1px #000;
	}
</style>
</head>
<body>
	<script>
		function goAddGroupPage(){
			window.location.href = "bookmark-group-add.jsp"
		}
	</script>
	<%
		BookmarkService bookmarkService = new BookmarkService();
		List<Bookmark>bookList = bookmarkService.getBookList(); 
	%>
	<h1> 북마크 그룹 </h1>
	<div style="width:100%">
		<a href = "main.jsp">홈</a> | <a href = "history.jsp">위치 히스토리 목록</a> | 
		<a href = "load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href = "bookmark-list.jsp">북마크 보기</a> | <a href = "bookmark-group.jsp">북마크 그룹 관리</a>
	</div>
	<br>
	<input type="button" value = "북마크 그룹 이름 추가" onclick="goAddGroupPage()">
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>북마크 이름</th>
				<th>순서</th>
				<th>등록일자</th>
				<th>수정일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			<%
			if(bookList!=null){	
				for(Bookmark bookmark: bookList){
			%>		
			    	 <tr>
			    		<td><%=bookmark.getBookId()%></td>
			    		<td><%=bookmark.getBookName()%></td>
			    		<td><%=bookmark.getBookOder()%></td>
    		
			    		<td><%=bookmark.getBookRegDate()%></td>
			    		<td>
				    		<%
								String bookModiDate = bookmark.getBookModiDate();
								if (bookModiDate == null) {
	    							bookModiDate = "";
								}
							%>
							<%= bookModiDate %>
			    		</td>
			    		<td colspan="2" style="text-align: center;">
			    			<a href = "bookmark-group-edit.jsp?bookId=<%=bookmark.getBookId()%>">수정</a> <a href = "bookmark-group-delete.jsp?bookId=<%=bookmark.getBookId()%>">삭제</a>
			    		</td>
			    	 </tr>
			  <%
			    }
			}	
			  %>
			</tr>
		</tbody>
	</table>
</body>
</html>