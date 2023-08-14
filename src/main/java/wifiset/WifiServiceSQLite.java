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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class WifiServiceSQLite {
	public int loadWifi() {
		String dburl = "jdbc:sqlite:wifi.db";
		
		try {
			Class.forName("org.sqlite.JDBC");
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
			connection = DriverManager.getConnection(dburl);
			
			String createTableSQL = "CREATE TABLE IF NOT EXISTS WIFIINFO ("
			        + "MGR_No TEXT PRIMARY KEY,"
			        + "BOROUGH TEXT,"
			        + "WIFI_NAME TEXT,"
			        + "RO_ADDRE TEXT,"
			        + "DE_ADDRE TEXT,"
			        + "INST_LOCATE TEXT,"
			        + "INST_TYPE TEXT,"
			        + "INST_INSTITU TEXT,"
			        + "SERVICE_TYPE TEXT,"
			        + "NETWORK_TYPE TEXT,"
			        + "INST_YEAR INTEGER,"
			        + "INANDOUT TEXT,"
			        + "CONN_ENVIRON TEXT,"
			        + "COORD_X REAL,"
			        + "COORD_Y REAL,"
			        + "WORK_DATE TEXT);";

			// 테이블 생성 실행
			try {
			    Statement statement = connection.createStatement();
			    statement.execute(createTableSQL);
			} catch (SQLException e) {
			    e.printStackTrace();
			}
			
			
			
			
			String sql = " insert into WIFIINFO (MGR_No, BOROUGH, WIFI_NAME, RO_ADDRE, DE_ADDRE, INST_LOCATE, INST_TYPE, INST_INSTITU, SERVICE_TYPE, NETWORK_TYPE, INST_YEAR, INANDOUT, CONN_ENVIRON, COORD_X, COORD_Y, WORK_DATE )" +
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
						
						
						preparedStatement.setString(1, tmp.get("X_SWIFI_MGR_NO").toString());
						preparedStatement.setString(2, tmp.get("X_SWIFI_WRDOFC").toString());
						preparedStatement.setString(3, tmp.get("X_SWIFI_MAIN_NM").toString());
						preparedStatement.setString(4, tmp.get("X_SWIFI_ADRES1").toString());
						preparedStatement.setString(5, tmp.get("X_SWIFI_ADRES2").toString());
						preparedStatement.setString(6, tmp.get("X_SWIFI_INSTL_FLOOR").toString());
						preparedStatement.setString(7, tmp.get("X_SWIFI_INSTL_TY").toString());
						preparedStatement.setString(8, tmp.get("X_SWIFI_INSTL_MBY").toString());
						preparedStatement.setString(9, tmp.get("X_SWIFI_SVC_SE").toString());
						preparedStatement.setString(10, tmp.get("X_SWIFI_CMCWR").toString());
						preparedStatement.setInt(11, Integer.parseInt(tmp.get("X_SWIFI_CNSTC_YEAR").toString().replace("\"","")));
						preparedStatement.setString(12, tmp.get("X_SWIFI_INOUT_DOOR").toString());
						preparedStatement.setString(13, tmp.get("X_SWIFI_REMARS3").toString());
						preparedStatement.setDouble(14,Double.parseDouble(tmp.get("LAT").toString().replace("\"","")));
						preparedStatement.setDouble(15,Double.parseDouble(tmp.get("LNT").toString().replace("\"","")));
						preparedStatement.setString(16, tmp.get("WORK_DTTM").toString());
							
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
	
	
	
	
	
	/*
	public List<Wifi> getNearWifi(){
		String dburl = "jdbc:sqlite:wifi.db";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet rs = null;
        
        try {
            connection = DriverManager.getConnection(dburl);
            

            String sql = " select member_type, user_id, password, name "+
                    " from member " +
                    " where member_type = ? ";
            //" where member_type = ? and user_id = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, );
            //preparedStatement.setString(2, );


            rs = preparedStatement.executeQuery();

            while(rs.next()){
                String memberType = rs.getString("member_type");
                String userId = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");
                
                Member member = new Member();
                member.setMemberType(memberTypeValue);
                member.setUserId(userId);
                member.setPassword(password);
                member.setName(name);
                
                memberList.add(member);
                
                System.out.println(memberType+", "+userId+", "+password+", "+name);
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
        
        
        
	}
	*/
    
}
