package wifiset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddService {
	public void addBook(int bookId, String mgrNo) {
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
            

            String sql = " Insert INTO BOOKADDWIFI( BOOK_ID, MGR_NO, BKWI_REGDATE) VALUES(?, ?, date_format( now(),'%Y-%m-%dT%T')); ";
			preparedStatement = connection.prepareStatement(sql);
			
            preparedStatement.setInt(1, bookId);
            preparedStatement.setString(2, mgrNo);
            
            int affected = preparedStatement.executeUpdate();

            
            

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
	
	public List<BookAddWifi> getList(){
		List<BookAddWifi> list = new ArrayList<>();
		
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
            

            String sql = " SELECT bookaddnum, b. book_id, book_name, wifi_name, i.mgr_no, bkwi_regdate "
            		+ " FROM bookaddwifi b "
            		+ " JOIN wifibookmark w ON b.book_id = w.book_id "
            		+ " JOIN WIFIINFO i ON b.mgr_no = i.mgr_no"
            		+ " ORDER BY bookaddnum ; " ;

            preparedStatement = connection.prepareStatement(sql);



            rs = preparedStatement.executeQuery();

            while(rs.next()){
            	int bookAndNum = rs.getInt("bookaddnum");
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                String wifiName = rs.getString("wifi_name");
                String bkwiRegDate = rs.getString("bkwi_regdate");
                
                
                
                
                BookAddWifi bookAddWifi = new BookAddWifi();
                bookAddWifi.setBookAddNum(bookAndNum);
                bookAddWifi.setBookId(bookId);
                bookAddWifi.setBookName(bookName);
                bookAddWifi.setWifiName(wifiName);
                bookAddWifi.setBkwiRegDate(bkwiRegDate);
                
                
                
                list.add(bookAddWifi);
                
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
        
        return list;
		
	}
	
	public BookAddWifi getOne(int bookAddNum){
		BookAddWifi bookAddWifi = null;
		
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
            

            String sql = " SELECT bookaddnum, b. book_id, book_name, wifi_name, i.mgr_no, bkwi_regdate "
            		+ " FROM bookaddwifi b "
            		+ " JOIN wifibookmark w ON b.book_id = w.book_id "
            		+ " JOIN WIFIINFO i ON b.mgr_no = i.mgr_no "
            		+ " WHERE bookaddnum = ? ";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bookAddNum);

            rs = preparedStatement.executeQuery();

            if(rs.next()){
            	bookAddWifi =  new BookAddWifi(); 
            	int bookAndNum = rs.getInt("bookaddnum");
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                String wifiName = rs.getString("wifi_name");
                String bkwiRegDate = rs.getString("bkwi_regdate");
                
                
                
                
                bookAddWifi.setBookAddNum(bookAndNum);
                bookAddWifi.setBookId(bookId);
                bookAddWifi.setBookName(bookName);
                bookAddWifi.setWifiName(wifiName);
                bookAddWifi.setBkwiRegDate(bkwiRegDate);
                
                
                
                
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
        
        return bookAddWifi;
		
	}
	
	
	public void deleteBook(int bookAddNum) {
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
            

            String sql = " delete from bookaddwifi where BOOKADDNUM = ? " ;

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bookAddNum);

            rs = preparedStatement.executeQuery();

            
            
            int affected = preparedStatement.executeUpdate();

            
            
            
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
}
