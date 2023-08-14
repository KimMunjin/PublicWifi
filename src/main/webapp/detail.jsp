<%@page import="wifiset.Wifi"%>
<%@page import="wifiset.WifiService"%>
<%@page import="wifiset.Bookmark"%>
<%@page import="wifiset.BookmarkService"%>
<%@page import="java.util.List"%>
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
	
	<%
		String mgrNo = request.getParameter("mgrNo");
	
		WifiService wifiService = new WifiService();
		Wifi wifi = wifiService.getDetail(mgrNo);
		BookmarkService bookmarkService = new BookmarkService();
		List<Bookmark>bookList = bookmarkService.getBookList(); 
		
	%>
	<h1>와이파이 정보 구하기</h1>
	<a href = "main.jsp">홈</a> | <a href = "history.jsp">위치 히스토리 목록</a> | 
	<a href = "load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href = "bookmark-list.jsp">북마크 보기</a> | <a href = "bookmark-group.jsp">북마크 그룹 관리</a>
	<div>
		<select id="bookmarkSelect">
			<option value="">북마크 그룹 이름 선택</option>
			<% for (Bookmark bookmark : bookList) { %>
            	<option value="<%= bookmark.getBookId()%>"><%= bookmark.getBookName()%></option>
        	<% } %>
		</select>
		<button id="submitButton">북마크 추가하기</button>
		<input type="hidden" id="mgrNo" value="<%=wifi.getMgrNo()%>">
	</div>
	<table>
	<colgroup>
		<col style = "width: 20%;"/>
		<col style = "width: 80%"/>
	</colgroup>
		<tbody>
			<tr>
				<th>관리번호</th>
				<td><%=wifi.getMgrNo()%></td>
			</tr>
			<tr>
				<th>자치구</th>
				<td><%=wifi.getBorough()%></td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td><%=wifi.getWifiName()%></td>
			</tr>
			<tr>
				<th>도로명주소</th>
				<td><%=wifi.getRoAddress()%></td>
			</tr>
			<tr>
				<th>상세주소</th>
				<td><%=wifi.getDeAddress()%></td>
			</tr>
			<tr>
				<th>설치위치(층)</th>
				<td><%=wifi.getInstLocate()%></td>
			</tr>
			<tr>
				<th>설치유형</th>
				<td><%=wifi.getInstType()%></td>
			</tr>
			<tr>
				<th>설치기관</th>
				<td><%=wifi.getInstInstitute()%></td>
			</tr>
			<tr>
				<th>서비스구분</th>
				<td><%=wifi.getServiceType()%></td>
			</tr>
			<tr>
				<th>망종류</th>
				<td><%=wifi.getNetworkType()%></td>
			</tr>
			<tr>
				<th>설치년도</th>
				<td><%=wifi.getInstYear()%></td>
			</tr>
			<tr>
				<th>실내외구분</th>
				<td><%=wifi.getInOut()%></td>
			</tr>
			<tr>
				<th>WIFI접속환경</th>
				<td><%=wifi.getConnEnvironment()%></td>
			</tr>
			<tr>
				<th>X좌표</th>
				<td><%=wifi.getCoordiX()%></td>
			</tr>
			<tr>
				<th>Y좌표</th>
				<td><%=wifi.getCoordiY()%></td>
			</tr>
			<tr>
				<th>작업일자</th>
				<td><%=wifi.getWorkDate()%></td>
			</tr>	
		</tbody>
	</table>

</body>
<script>
        document.getElementById("submitButton").addEventListener("click", function() {
        	var mgrNo = document.getElementById("mgrNo").value;
            var selectedValue = document.getElementById("bookmarkSelect").value;
            if (selectedValue) {
				var url = "bookmark-add-submit.jsp?bookId="+selectedValue+"&mgrNo="+mgrNo;
				var newWindow = window.open(url, '_blank');
				newWindow.onload = function() {
		            newWindow.focus();
		        };
            } else {
                alert("값을 선택하세요.");
            }
        });
    </script>
</html>