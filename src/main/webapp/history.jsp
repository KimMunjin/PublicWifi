<%@page import="wifiset.WifiHistory"%>
<%@page import="java.util.List"%>
<%@page import="wifiset.WifiService"%>
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
		function delHistory(historyId) {
            if (confirm("정말로 삭제하시겠습니까?")) {
				window.location.href = "history.jsp?historyId=" + historyId;
			}
        }
    </script>
	<%	
		String historyIdStr = request.getParameter("historyId");
		WifiService wifiService = new WifiService();
		if(historyIdStr!=null){
			int historyId = Integer.parseInt(historyIdStr);
			wifiService.deleteHistory(historyId);
		}

		List<WifiHistory>HistoryList = wifiService.getHistory();
	%>
	<h1>위치 히스토리 목록</h1>
	<a href = "main.jsp">홈</a> | <a href = "history.jsp">위치 히스토리 목록</a> | 
	<a href = "load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href = "bookmark-list.jsp">북마크 보기</a> | <a href = "bookmark-group.jsp">북마크 그룹 관리</a>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			<%		
			     for(WifiHistory wifiHistory :HistoryList){
			 %>
			    	 <tr>
			    		<td><%=wifiHistory.getHistoryId()%></td>
			    	 	<td><%=wifiHistory.getMyCoordX()%></td>
			    	 	<td><%=wifiHistory.getMyCoordY()%></td>
			    	 	<td><%=wifiHistory.getInquiryDate()%></td>
			    	 	<td><div style="text-align: center"><input type="button" style="display :inline-block;"  value="삭제" onclick="delHistory(<%=wifiHistory.getHistoryId()%>)"></div></td>
			    	 </tr>
			  <%
			     }    
			%>
			</tr>
		</tbody>
	</table>
</body>
</html>