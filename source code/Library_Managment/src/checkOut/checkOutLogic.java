package checkOut;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dbConnection.Select;
import libMang.dbConnection;

public class checkOutLogic {
	
	String bookid=null;
	String branchid=null;
	String cardno=null;
	ResultSet rs = null,rs1=null,rs2=null,rs3=null;
	public checkOutLogic() {
		// TODO Auto-generated constructor stub
	}
	
	public String checkOut(String bookId, String branchId, String cardNo) throws SQLException
	{
		String bookid=bookId;
		String branchid=branchId;
		String cardno=cardNo;
		String status=null;
		int maxLoan=0,up=0;
		Select db=new Select();
		if(!maxloan(cardno)){
			System.out.println("hi");
			status="You already have 3 book loans. Please do check in first...!!!";			
		}
		else{
			if(!countCopies(bookid,branchid))
			{
				status="Requested book is not available in this branch. Please check in some other branch...!!!";
			}
			else
			{
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			Calendar cal = new GregorianCalendar(/* remember about timezone! */);
            java.util.Date todaysdate = cal.getTime();
			String todayDate = format.format(todaysdate);
			cal.add(Calendar.DATE, 14);
			java.util.Date dueDate = cal.getTime();
			String dueDate1 = format.format(dueDate);
			String loan_id="Select max(loanId) from book_loan;";
			rs=db.selectData(loan_id);
			while(rs.next())
				maxLoan=rs.getInt(1);
			maxLoan+=1;
			String updateLoan="Insert into book_loan values("+maxLoan+",'"+bookid+"','"+branchid+"','"+cardno+"','"+todayDate+"','"+dueDate1+"',"+null+");";
			up=db.updateQuery(updateLoan);
			if(up==1)
			status="successful";
			else
			status="Fail";
		 }
		}
		return status;
	}
	public Boolean maxloan(String cardno) throws SQLException{
        String cardno1=cardno;
		Boolean loanCount=false;
        int count=0;
        rs=null;
        //build a query 
        String query1="Select count(bookId) from book_loan where cardno="+cardno1+ " AND datein IS NULL;";
        System.out.println(query1);
        dbConnection qSelect=new dbConnection();
        rs=qSelect.selectData(query1);
        while(rs.next()){
        count=rs.getInt(1);
        }
        System.out.println(count);
        if(count<3)
        	loanCount=true;
        System.out.println("total loans"+loanCount);
        rs=null;
    	return loanCount;    	
    }
	public Boolean countCopies(String bookid,String branchid) throws SQLException{
       	boolean isAvailable=false;
    	int copies=0,loan=0;
    	String bookId=bookid;
    	String branchId=branchid;
    	String originalCopies="Select noofcopies from book_copies where bookID='"+bookId+"' AND branchID="+branchId+";";
    	String lendcopies="Select count(bookID) from book_loan where bookID='"+bookId+"' AND branchID="+branchId+";";
    	dbConnection qSelect=new dbConnection();
    	System.out.println(originalCopies);
    	System.out.println(lendcopies);
    	rs=qSelect.selectData(originalCopies);
    	while(rs.next()){
        copies=rs.getInt(1);
    	}        
        rs1=qSelect.selectData(lendcopies);
        while(rs1.next()){
        loan=rs1.getInt(1);
        }   
        if(copies>loan)
        	isAvailable=true;
        rs=null;
        rs1=null;
    	return isAvailable;
    }
    
}
