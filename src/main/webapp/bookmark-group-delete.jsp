<%@page import="wifiset.Bookmark"%>
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
		function deleteGroupMark(){
			var bookId = document.getElementById("bookId").value;
			
			
			var url = 'bookmark-group-delete-submit.jsp?bookId='+bookId;
			var newWindow = window.open(url, '_blank');
			newWindow.onload = function() {
	            newWindow.focus();
	        };
			
		}
		
	
	</script>
	<%	
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		BookmarkService bookmarkService = new BookmarkService();
		Bookmark bookmark = bookmarkService.getBookmark(bookId); 
	%>
	<h1> 북마크 그룹 삭제 </h1>
	<a href = "main.jsp">홈</a> | <a href = "history.jsp">위치 히스토리 목록</a> | 
	<a href = "load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href = "bookmark-list.jsp">북마크 보기</a> | <a href = "bookmark-group.jsp">북마크 그룹 관리</a>
		<table>
	<colgroup>
		<col style = "width: 20%;"/>
		<col style = "width: 80%"/>
	</colgroup>
		<tbody>
			<tr>
				<th>북마크 이름</th>
				<td><input type="text" id ="bookName" value="<%=bookmark.getBookName()%>"></td>
			</tr>
			<tr>
				<th>순서</th>
				<td><input type="text" id="bookOrder" value="<%=bookmark.getBookOder()%>"></td>
			</tr>
			<tr>
				<input type="hidden" id="bookId" value="<%=bookmark.getBookId()%>">
				<td colspan="2" style="text-align: center;">
					<a href="bookmark-group.jsp">돌아가기</a> | <input type="button" value="삭제" onclick="deleteGroupMark()">
				</td>
			</tr>
		</tbody>
	</table>

</body>
</html>