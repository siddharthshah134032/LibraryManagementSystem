package fines;

import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;
import java.util.GregorianCalendar;

//import javax.xml.datatype.Duration;

import dbConnection.Select;


public class fineLogic {
	
	//@SuppressWarnings("deprecation")
	public String[][] calculate_fine(){
	ResultSet rs1=null,rs2=null,rs3=null;
	int loanid=0;
	java.sql.Date duedate;
	int datediff=0;
	int flag=0;
	Select db=new Select();
	String[][] result=new  String[100][10];
	Calendar calendar = new GregorianCalendar(/* remember about timezone! */);
    java.util.Date todaysDate = calendar.getTime();
    System.out.println(todaysDate);
   // String date = formatter.format(todaysDate);
	try {
		String query="select BL.loanID,BL.duedate from book_loan BL where datein IS NULL;";
		rs1=db.selectData(query);
		while(rs1.next()){
			loanid=Integer.parseInt(rs1.getString(1));
			duedate=rs1.getDate(2);
			if(duedate.before(todaysDate)){
				datediff = (int) ((todaysDate.getTime() - duedate.getTime()) / (1000 * 60 * 60 * 24));
				String fine="0.25";
				Double fine1=Double.parseDouble(fine);
				fine1=datediff*0.25;
				System.out.println("------"+loanid);
				String query1="select count(*) from fines where loanId='"+loanid+"';";
				System.out.println(query1);
				rs2=db.selectData(query1);
				while(rs2.next()){
					flag=Integer.parseInt(rs2.getString(1));
				}
				if(flag==0){
					System.out.println("no records");
					System.out.println(fine1);
					String updatefine1="Insert into fines values("+loanid+",'"+fine1+"',"+false+");";
					db.updateQuery(updatefine1);
				}
				else{
					System.out.println("record exist");
					String updatefine2="UPDATE fines SET fine_amt="+"'"+fine1+"'"+" WHERE loanId="+"'"+loanid+"';";
					System.out.println("update"+updatefine2);
					db.updateQuery(updatefine2);
					flag=0;
				}
			}
			String query2="select F.loanId,B.b_fname,B.b_lname,B.cardno,F.fine_amt,F.paid from fines F, book_loan BL,borrower B where B.cardno=BL.cardno AND BL.loanId=F.loanID AND F.paid=0;";
			rs3=db.selectData(query2);
			int i=0,j=0;
			while(rs3.next()){
				j=0;
				String loanId=rs3.getString(1);
				String bfname=rs3.getString(2);
				String blname=rs3.getString(3);
				String cardno=rs3.getString(4);
				String fine=rs3.getString(5);
				String paid=rs3.getString(6);
				
				result[i][j]=loanId;j++;
				result[i][j]=bfname;j++;
				result[i][j]=blname;j++;
				result[i][j]=cardno;j++;
				result[i][j]=fine;j++;
				result[i][j]=paid;j++;
				i++;
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return result;
	}
}
