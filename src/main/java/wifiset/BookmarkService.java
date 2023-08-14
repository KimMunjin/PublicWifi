package wifiset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookmarkService {
	
	public List<Bookmark> getBookList(){
		List<Bookmark>bookList = new ArrayList<>();
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
                    " from WIFIBOOKMARK " +
                    " ORDER BY BOOK_ORDER " ;

            preparedStatement = connection.prepareStatement(sql);



            rs = preparedStatement.executeQuery();

            while(rs.next()){
                int bookId = rs.getInt("BOOK_ID");
                String bookName = rs.getString("BOOK_NAME");
                int bookOder = rs.getInt("BOOK_ORDER");
                String bookRegDate = rs.getString("BOOK_REGDATE");
                String bookModiDate = rs.getString("BOOK_MODIDATE");
                
                
                
                Bookmark bookmark = new Bookmark();
                bookmark.setBookId(bookId);
                bookmark.setBookName(bookName);
                bookmark.setBookOder(bookOder);
                bookmark.setBookRegDate(bookRegDate);
                bookmark.setBookModiDate(bookModiDate);
                
                
                bookList.add(bookmark);
                
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
		
		
		
		return bookList;
	}
	
	
	public void deleteBookGroup(int bookId) {
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
            

            String sql = " delete from WIFIBOOKMARK " + 
                    " where BOOK_ID = ? " ;

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bookId);

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
	
	
	
	
	
	public void updateBookGroup(int bookId, String bookName, int bookOrder) {
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
            

            String sql = " update WIFIBOOKMARK " + 
                    " set BOOK_NAME = ?, BOOK_ORDER = ?, BOOK_MODIDATE = date_format( now(),'%Y-%m-%dT%T') " +
                    " where BOOK_ID = ? " ;

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, bookName);
            preparedStatement.setInt(2, bookOrder);
            preparedStatement.setInt(3, bookId);

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
	
	
	
	public Bookmark getBookmark(int bookId) {
		Bookmark bookmark = null;
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
                    " from WIFIBOOKMARK " +
                    " where BOOK_ID = ? " ;

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bookId);

            rs = preparedStatement.executeQuery();

            
            
            if(rs.next()){
            bookmark = new Bookmark();
            
            String bookName = rs.getString("BOOK_NAME");
            int bookOder = rs.getInt("BOOK_ORDER");
            String bookRegDate = rs.getString("BOOK_REGDATE");
            String bookModiDate = rs.getString("BOOK_MODIDATE");
            
            bookmark.setBookId(bookId);
            bookmark.setBookName(bookName);
            bookmark.setBookOder(bookOder);
            bookmark.setBookRegDate(bookRegDate);
            bookmark.setBookModiDate(bookModiDate);
            
            
           
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
        return bookmark;
		
	}
	
	
	
	
	public void addBookGroup(String bookName, int bookOrder) {
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
            

            String sql = " Insert INTO WIFIBOOKMARK( BOOK_NAME, BOOK_ORDER,BOOK_REGDATE) VALUES(?, ?, date_format( now(),'%Y-%m-%dT%T')); ";
			preparedStatement = connection.prepareStatement(sql);
			
            preparedStatement.setString(1, bookName);
            preparedStatement.setInt(2, bookOrder);
            
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
