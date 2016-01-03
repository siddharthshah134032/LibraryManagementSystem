package home;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


//import libMang.dbConnection;
import dbConnection.Select;
import dbConnection.dbconnection;

public class checkBook {
	String loanId=null;
	public checkBook(String loan_id) {
		// TODO Auto-generated constructor stub
		this.loanId=loan_id;
	}

	public int checkInbook()
	   {
		   int checkSuccess=0;
		   SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
		   Calendar calendar = new GregorianCalendar(/* remember about timezone! */);
           java.util.Date todaysDate = calendar.getTime();
           java.util.Date todaysDate1 = calendar.getTime();
           System.out.println(todaysDate);
           System.out.println(todaysDate1);
           String date = formatter.format(todaysDate);
           //todaysDate.equals(obj)
		   String query="UPDATE BOOK_LOAN SET DATEIN="+"'"+date+"'" +"WHERE loanId="+"'"+loanId+"';";
		   System.out.println(query);
		   //dbconnection uppdateop=new dbconnection();
		   Select uppdateop=new Select();
		   try {
			checkSuccess=uppdateop.updateQuery(query);
			String query1="update fines set paid=1,fine_amt=0 where loanId='"+loanId+"';";
			uppdateop.selectData(query1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return checkSuccess;
	   }
	
	
}
