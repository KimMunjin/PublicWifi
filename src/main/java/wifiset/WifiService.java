package wifiset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class WifiService {
	public int loadWifi() {
		String dburl = "jdbc:mariadb://localhost:3306/wifi_db";
		String dbUserId = "wifi_user";
        String dbPassword = "1234";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet rs = null;
         
        
        
		int totalCount = 0;
		boolean check = false;
		int start = 1;
		int end = 1000;
		String key = "596a4d676b72656d32314f6e516268";
		try {
			connection = DriverManager.getConnection(dburl,dbUserId, dbPassword);
			
			
			String sql = " insert into WIFIINFO (MGR_No, BOROUGH, WIFI_NAME, RO_ADDRE, DE_ADDRE, INST_LOCATE, INST_TYPE, INST_INSTITU, SERVICE_TYPE, NETWORK_TYPE, INST_YEAR, INANDOUT, CONN_ENVIRON, COORD_Y, COORD_X, WORK_DATE )" +
					" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ); ";
			preparedStatement = connection.prepareStatement(sql);

			while(!check) {
				StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
				try {
					urlBuilder.append("/" +  URLEncoder.encode(key,"UTF-8") );
					urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") );
					urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
					urlBuilder.append("/" + URLEncoder.encode(Integer.toString(start),"UTF-8"));
					urlBuilder.append("/" + URLEncoder.encode(Integer.toString(end),"UTF-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		
				URL url;
				StringBuilder sb = new StringBuilder();
				BufferedReader br = null;
				HttpURLConnection conn = null;
				try {
					url = new URL(urlBuilder.toString());
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Content-type", "application/json");
					
					if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
						br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					} else {
						br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
					}
					String line;
					while ((line = br.readLine()) != null) {
							sb.append(line);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					conn.disconnect();
				}
				
				JsonObject result = (JsonObject) new JsonParser().parse(sb.toString());
				JsonObject data = (JsonObject)result.get("TbPublicWifiInfo");
				if(totalCount ==0 ) {
					totalCount = Integer.parseInt(data.get("list_total_count").toString());
				}
				if(data==null) {
					check = true;
				}else {
					JsonArray items = (JsonArray)data.get("row");
					JsonObject tmp;
					for(int i = 0;i<items.size();i++ ) {
						tmp = (JsonObject) items.get(i);
						
						
						preparedStatement.setString(1, tmp.get("X_SWIFI_MGR_NO").toString().replace("\"",""));
						preparedStatement.setString(2, tmp.get("X_SWIFI_WRDOFC").toString().replace("\"",""));
						preparedStatement.setString(3, tmp.get("X_SWIFI_MAIN_NM").toString().replace("\"",""));
						preparedStatement.setString(4, tmp.get("X_SWIFI_ADRES1").toString().replace("\"",""));
						preparedStatement.setString(5, tmp.get("X_SWIFI_ADRES2").toString().replace("\"",""));
						preparedStatement.setString(6, tmp.get("X_SWIFI_INSTL_FLOOR").toString().replace("\"",""));
						preparedStatement.setString(7, tmp.get("X_SWIFI_INSTL_TY").toString().replace("\"",""));
						preparedStatement.setString(8, tmp.get("X_SWIFI_INSTL_MBY").toString().replace("\"",""));
						preparedStatement.setString(9, tmp.get("X_SWIFI_SVC_SE").toString().replace("\"",""));
						preparedStatement.setString(10, tmp.get("X_SWIFI_CMCWR").toString().replace("\"",""));
						preparedStatement.setInt(11, Integer.parseInt(tmp.get("X_SWIFI_CNSTC_YEAR").toString().replace("\"","")));
						preparedStatement.setString(12, tmp.get("X_SWIFI_INOUT_DOOR").toString().replace("\"",""));
						preparedStatement.setString(13, tmp.get("X_SWIFI_REMARS3").toString().replace("\"",""));
						preparedStatement.setDouble(14,Double.parseDouble(tmp.get("LAT").toString().replace("\"","")));
						preparedStatement.setDouble(15,Double.parseDouble(tmp.get("LNT").toString().replace("\"","")));
						preparedStatement.setString(16, tmp.get("WORK_DTTM").toString().replace("\"",""));
							
						int affected = preparedStatement.executeUpdate();
						
					}
				}
				start = end+1;
				end += 1000;		
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
                if(rs!=null&&!rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
            try {
                if(preparedStatement!=null&&!preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
            try {
                if(connection!=null&&!connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}

		return totalCount;	
	}
	
	public List<WifiHistory> getHistory(){
		
		List<WifiHistory>historyList = new ArrayList<>();
		String dburl = "jdbc:mariadb://localhost:3306/wifi_db";
		String dbUserId = "wifi_user";
        String dbPassword = "1234";
        
        try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet rs = null;
        
        try {
            connection = DriverManager.getConnection(dburl,dbUserId,dbPassword);
            

            String sql = " select * " + 
                    " from WIFIHISTORY w " +
                    " ORDER BY HISTORY_ID " ;

            preparedStatement = connection.prepareStatement(sql);



            rs = preparedStatement.executeQuery();

            while(rs.next()){
                int historyId = rs.getInt("HISTORY_ID");
                double myCoordX = rs.getDouble("MY_COORDX");
                double myCoordY = rs.getDouble("MY_COORDY");
                String inquiryDate = rs.getString("INQUIRY_DATE");
                
                
                
                WifiHistory wifiHistory = new WifiHistory();
                wifiHistory.setHistoryId(historyId);
                wifiHistory.setMyCoordY(myCoordY);
                wifiHistory.setMyCoordX(myCoordX);
                wifiHistory.setInquiryDate(inquiryDate);
                
                
                
                historyList.add(wifiHistory);
                
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }finally {
            try {
                if(rs!=null&&!rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
            try {
                if(preparedStatement!=null&&!preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
            try {
                if(connection!=null&&!connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
		return historyList;
	}

	public void deleteHistory(int historyId) {
		String dburl = "jdbc:mariadb://localhost:3306/wifi_db";
		String dbUserId = "wifi_user";
        String dbPassword = "1234";
        
        try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet rs = null;
        
        try {
            connection = DriverManager.getConnection(dburl, dbUserId, dbPassword);

            String sql = " delete from `WIFIHISTORY` " +
                    " where HISTORY_ID =? ";


            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,historyId);


            int affected = preparedStatement.executeUpdate();

            if(affected>0){
                System.out.println("삭제 성공");
            }else{
                System.out.println("삭제 실패");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs!=null&&!rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(preparedStatement!=null&&!preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if(connection!=null&&!connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
	}
	
	
	
	public Wifi getDetail(String mgrNo) {
		
		Wifi wifi = null;
		String dburl = "jdbc:mariadb://localhost:3306/wifi_db";
		String dbUserId = "wifi_user";
        String dbPassword = "1234";
        
        try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(dburl,dbUserId,dbPassword);
            

            String sql = " select * " + 
                    " from WIFIINFO w " +
                    " WHERE MGR_No = ? " ;

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mgrNo);



            rs = preparedStatement.executeQuery();

            while(rs.next()){
            	wifi = new Wifi();
            	wifi.setMgrNo(rs.getString("MGR_No"));
            	wifi.setBorough(rs.getString("BOROUGH"));
            	wifi.setWifiName(rs.getString("WIFI_NAME"));
            	wifi.setRoAddress(rs.getString("RO_ADDRE"));
            	wifi.setDeAddress(rs.getString("DE_ADDRE"));
            	wifi.setInstLocate(rs.getString("INST_LOCATE"));
            	wifi.setInstType(rs.getString("INST_TYPE"));
            	wifi.setInstInstitute(rs.getString("INST_INSTITU"));
            	wifi.setServiceType(rs.getString("SERVICE_TYPE"));
            	wifi.setNetworkType(rs.getString("NETWORK_TYPE"));
            	wifi.setInstYear(rs.getInt("INST_YEAR"));
            	wifi.setInOut(rs.getString("INANDOUT"));
            	wifi.setConnEnvironment(rs.getString("CONN_ENVIRON"));
            	wifi.setCoordiX(rs.getDouble("COORD_X"));
            	wifi.setCoordiY(rs.getDouble("COORD_Y"));
            	wifi.setWorkDate(rs.getString("WORK_DATE"));
              
                
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }finally {
            try {
                if(rs!=null&&!rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
            try {
                if(preparedStatement!=null&&!preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
            try {
                if(connection!=null&&!connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
		return wifi;
		
		
	}
	
	
	
	public List<Wifi> getNearWifi(double myLat,double myLnt){
		List<Wifi> nearWifiList = new ArrayList<>();
		
		String dburl = "jdbc:mariadb://localhost:3306/wifi_db";
		String dbUserId = "wifi_user";
        String dbPassword = "1234";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet rs = null;
        
        try {
            connection = DriverManager.getConnection(dburl,dbUserId,dbPassword);
            

            String sql = " select ROUND(6371 * acos(cos(radians("+myLat+"))*cos(radians(COORD_X))*cos(radians(COORD_Y)-radians("+myLnt+"))+sin(radians("+myLat+")) * sin(radians(COORD_X))),4) AS distance, " + 
            		" MGR_No, BOROUGH, WIFI_NAME, RO_ADDRE, DE_ADDRE, INST_LOCATE, INST_TYPE, INST_INSTITU, SERVICE_TYPE, NETWORK_TYPE, "+
            		" INST_YEAR, INANDOUT, CONN_ENVIRON, COORD_X, COORD_Y, WORK_DATE"+
                    " from WIFIINFO w " +
                    " ORDER BY distance " +
                    " LIMIT 20" ;

            preparedStatement = connection.prepareStatement(sql);



            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Double distance = rs.getDouble("distance");
                String mgrNo = rs.getString("MGR_No");
                String borough = rs.getString("BOROUGH");
                String wifiName = rs.getString("WIFI_NAME");
                String roAddress = rs.getString("RO_ADDRE");
                String deAddress = rs.getString("DE_ADDRE");
                String instLocate = rs.getString("INST_LOCATE");
                String instType = rs.getString("INST_TYPE");
                String instInstitute = rs.getString("INST_INSTITU");
                String serviceType = rs.getString("SERVICE_TYPE");
                String networkType = rs.getString("NETWORK_TYPE");
                int instYear = rs.getInt("INST_YEAR");
                String inOut = rs.getString("INANDOUT");
                String connEnvironment = rs.getString("CONN_ENVIRON");
                double coordiX = rs.getDouble("COORD_X");
                double coordiY = rs.getDouble("COORD_Y");
                String workDate = rs.getString("WORK_DATE");
                
                
                
                Wifi wifi = new Wifi();
                wifi.setDistance(distance);
                wifi.setMgrNo(mgrNo);
                wifi.setBorough(borough);
                wifi.setWifiName(wifiName);
                wifi.setRoAddress(roAddress);
                wifi.setDeAddress(deAddress);
                wifi.setInstLocate(instLocate);
                wifi.setInstType(instType);
                wifi.setInstInstitute(instInstitute);
                wifi.setServiceType(serviceType);
                wifi.setNetworkType(networkType);
                wifi.setInstYear(instYear);
                wifi.setInOut(inOut);
                wifi.setConnEnvironment(connEnvironment);
                wifi.setCoordiX(coordiX);
                wifi.setCoordiY(coordiY);
                wifi.setWorkDate(workDate);
                
                nearWifiList.add(wifi);
                
            }
            preparedStatement = null;
            String sql2 = " Insert INTO WIFIHISTORY(MY_COORDX, MY_COORDY, INQUIRY_DATE) VALUES(?, ?, date_format( now(),'%Y-%m-%dT%T')); ";
			preparedStatement = connection.prepareStatement(sql2);
			
            preparedStatement.setDouble(1, myLat);
            preparedStatement.setDouble(2, myLnt);
            
            int affected = preparedStatement.executeUpdate();

            if(affected>0){
                System.out.println("저장 성공");
            }else{
                System.out.println("저장 실패");
            }
            

        } catch (SQLException e) {
        	e.printStackTrace();
        }finally {
            try {
                if(rs!=null&&!rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
            try {
                if(preparedStatement!=null&&!preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
            try {
                if(connection!=null&&!connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
        return nearWifiList;
	}
	
    
}
