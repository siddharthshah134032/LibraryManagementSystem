package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class Select {
	public ResultSet selectData(String Query) throws SQLException{
		
		Connection connection = null;
		Statement statement =null;
		ResultSet rs=null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/librarydb","root",null);
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

      public int updateQuery(String Query) throws SQLException{

    	int check=0;
  	 	Connection connection = null;
  		Statement statement =null;
	    try{
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/librarydb","root",null);
		statement=(Statement) connection.createStatement();
		check=statement.executeUpdate(Query);
				
	    }
	    catch(Exception e){
	    	System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
	    }
	    finally {
	    	//connection.close();
	    	}
	    return check;		
      }		
	
}
