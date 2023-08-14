<%@page import="wifiset.BookAddWifi"%>
<%@page import="wifiset.AddService"%>
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
		function deleteMark(){
			var bookAddNum = document.getElementById("bookAddNum").value;
			
			
			var url = 'bookmark-delete-submit.jsp?bookAddNum='+bookAddNum;
			var newWindow = window.open(url, '_blank');
			newWindow.onload = function() {
	            newWindow.focus();
	        };
			
		}
		
	
	</script>
	<%	
		int bookAddNum = Integer.parseInt(request.getParameter("bookAddNum"));
		AddService addService = new  AddService();
		BookAddWifi bookAddWifi = addService.getOne(bookAddNum); 
	%>
	<h1> 북마크 삭제 </h1>
	<a href = "main.jsp">홈</a> | <a href = "history.jsp">위치 히스토리 목록</a> | 
	<a href = "load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href = "bookmark-list.jsp">북마크 보기</a> | <a href = "bookmark-group.jsp">북마크 그룹 관리</a>
	<br>
	<p>북마크를 삭제하시겠습니까?</p>
		<table>
	<colgroup>
		<col style = "width: 20%;"/>
		<col style = "width: 80%"/>
	</colgroup>
		<tbody>
			<tr>
				<th>북마크 이름</th>
				<td><input type="text" id ="bookName" value="<%=bookAddWifi.getBookName()%>"></td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td><input type="text" id="wifiName" value="<%=bookAddWifi.getWifiName()%>"></td>
			</tr>
			<tr>
				<th>등록일자</th>
				<td><input type="text" id="bkwiRegDate" value="<%=bookAddWifi.getBkwiRegDate()%>"></td>
			</tr>
			<tr>
				<input type="hidden" id="bookAddNum" value="<%=bookAddWifi.getBookAddNum()%>">
				<td colspan="2" style="text-align: center;">
					<a href="bookmark-list.jsp">돌아가기</a> | <input type="button" value="삭제" onclick="deleteMark()">
				</td>
			</tr>
		</tbody>
	</table>

</body>
</html>