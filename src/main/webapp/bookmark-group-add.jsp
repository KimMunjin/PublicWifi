
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
		function addGroupMark(){

			var bookName = document.getElementById("bookName").value;
			var bookOrder =document.getElementById("bookOrder").value;
			
			var url = 'bookmark-group-add-submit.jsp?bookName=' + bookName + '&bookOrder=' + bookOrder;
			var newWindow = window.open(url, '_blank');
			newWindow.onload = function() {
	            newWindow.focus();
	        };
			
		}
		
	
	</script>
	
	<h1> 북마크 그룹 추가 </h1>
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
				<td><input type="text" id ="bookName" value=""></td>
			</tr>
			<tr>
				<th>순서</th>
				<td><input type="text" id="bookOrder" value=""></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="button" value="추가" onclick="addGroupMark()">
				</td>
			</tr>
		</tbody>
	</table>

</body>
</html>