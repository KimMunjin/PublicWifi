<%@page import="wifiset.Wifi"%>
<%@page import="java.util.List"%>
<%@page import="wifiset.WifiService"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
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
		function showPosition(position) {
	        var latitude = position.coords.latitude;
	        var longitude = position.coords.longitude;

	        document.getElementById("LAT").value = latitude;
	        document.getElementById("LNT").value = longitude;
	    }
		function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition);
            } else {
                alert("Geolocation is not supported by this browser.");
            }
        }
		function getNearWifi() {
            var latitude = document.getElementById("LAT").value;
            var longitude = document.getElementById("LNT").value;
            var url = 'main.jsp?LAT=' + latitude + '&LNT=' + longitude;
            location.href = url;
        }
    </script>
    <%
    	String latParam = request.getParameter("LAT");
    	String lntParam = request.getParameter("LNT");
    	List<Wifi>wifiList = null;
    	
    	if (latParam != null && lntParam != null) {
            Double lat = Double.parseDouble(latParam);
            Double lnt = Double.parseDouble(lntParam);
            
            WifiService wifiService = new WifiService();
            wifiList = wifiService.getNearWifi(lat, lnt);      
        } 
    %>
	<h1>와이파이 정보 구하기</h1>
	<a href = "main.jsp">홈</a> | <a href = "history.jsp">위치 히스토리 목록</a> | 
	<a href = "load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href = "bookmark-list.jsp">북마크 보기</a> | <a href = "bookmark-group.jsp">북마크 그룹 관리</a>
	<div>
		<form>
			<label for="LAT">LAT:</label>
			<input type="text" id="LAT" name="LAT" value="0.0" required> ,
			<label for="LNT">LNT:</label>
			<input type="text" id="LNT" name="LNT" value="0.0" required>
			<input type="button" value="내 위치 가져오기" onclick="getLocation()">
			<input type="button" value="근처 WIFI 정보 보기" onclick="getNearWifi()"> 
		</form>
	</div>
	<table>
		<thead>
			<tr>
				<th>거리</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			<%
			if(wifiList!=null){	
				for(Wifi wifi: wifiList){
			%>		
			    	 <tr>
			    		<td><%=wifi.getDistance()%></td>
			    		<td><%=wifi.getMgrNo()%></td>
			    		<td><%=wifi.getBorough()%></td>
			    		
			    		
			    		<td>
			    			<a href="detail.jsp?mgrNo=<%=wifi.getMgrNo()%>">
			    			<%=wifi.getWifiName()%>
			    			</a>
			    		</td>
			    		<td><%=wifi.getRoAddress()%></td>
			    		<td><%=wifi.getDeAddress()%></td>
			    		<td><%=wifi.getInstLocate()%></td>
			    		<td><%=wifi.getInstType()%></td>
			    		<td><%=wifi.getInstInstitute()%></td>
			    		<td><%=wifi.getServiceType()%></td>
			    		<td><%=wifi.getNetworkType()%></td>
			    		<td><%=wifi.getInstYear()%></td>
			    		<td><%=wifi.getInOut()%></td>
			    		
			    	 	<td><%=wifi.getConnEnvironment()%></td>
			    	 	<td><%=wifi.getCoordiX()%></td>
			    	 	<td><%=wifi.getCoordiY()%></td>
			    	 	<td><%=wifi.getWorkDate()%></td>
			    	 </tr>
			  <%
			    }
			}else{
				%>
				<td colspan="17" style="text-align: center;">위치 정보를 입력한 후에 조회해 주세요.</td>
				<%
			}
			  %>
			</tr>
		</tbody>
	</table>
	
</body>
</html>