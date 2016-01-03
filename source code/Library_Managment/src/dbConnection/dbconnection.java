package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class dbconnection {
	public ResultSet select(String Query) throws SQLException{
		
		Connection connection = null;
		Statement statement =null;
		ResultSet rs=null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb","root",null);
			statement=(Statement) connection.createStatement();
			rs=statement.executeQuery(Query);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//connection.close();
		}
		return rs;
	   }

      public int update(String Query) throws SQLException{

    	int status=0;
  	 	Connection connection = null;
  		Statement statement =null;
	    try{
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb","root",null);
		statement=(Statement) connection.createStatement();
		status=statement.executeUpdate(Query);
				
	    }
	    catch(Exception e){
	    	System.out.println("Connection Failed");
			e.printStackTrace();
	    }
	    finally {
	    	//connection.close();
	    	}
	    return status;		
      }		
	
}
