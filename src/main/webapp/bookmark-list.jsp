<%@page import="wifiset.BookAddWifi"%>
<%@page import="java.util.List"%>
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
		function goAddGroupPage(){
			window.location.href = "bookmark-group-add.jsp"
		}
	</script>
	<%
		AddService addService = new  AddService();
		List<BookAddWifi>list = addService.getList(); 
	%>
	<h1> 북마크 목록 </h1>
	<div style="width:100%">
		<a href = "main.jsp">홈</a> | <a href = "history.jsp">위치 히스토리 목록</a> | 
		<a href = "load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href = "bookmark-list.jsp">북마크 보기</a> | <a href = "bookmark-group.jsp">북마크 그룹 관리</a>
	</div>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>북마크 이름</th>
				<th>와이파이명</th>
				<th>등록일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			<%
			if(list!=null){	
				for(BookAddWifi bookAddWifi: list){
			%>		
			    	 <tr>
			    		<td><%=bookAddWifi.getBookAddNum()%></td>
			    		<td><%=bookAddWifi.getBookName()%></td>
			    		<td><%=bookAddWifi.getWifiName()%></td>
			    		<td><%=bookAddWifi.getBkwiRegDate()%></td>
			    		<td colspan="2" style="text-align: center;">
			    			 <a href = "bookmark-delete.jsp?bookAddNum=<%=bookAddWifi.getBookAddNum()%>">삭제</a>
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