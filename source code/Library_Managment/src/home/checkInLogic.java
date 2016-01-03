package home;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbConnection.Select;

public class checkInLogic {
	
	String bookid=null,cardno=null,fname=null,lname=null,query=null,datein=null,loanid=null;
	Boolean check=false,check1=false,check2=false;
   
	checkInLogic(String bookid,String cardno,String fname,String lname)
    {
	   this.bookid=bookid;
	   this.cardno=cardno;
	   this.fname=fname;
	   this.lname=lname;
   }
	 public String[][] buildQuery()
	   {
	     query="SELECT BL.loanid,BL.bookId,BL.branchId,BL.cardno,dateout,duedate,b_fname,b_lname from BOOK_LOAN BL,BORROWER B WHERE "
	     		+ "BL.cardno=B.cardno AND" ;
	     
	     if(!cardno.isEmpty()){
	    	 query+=" BL.cardno="+cardno;
	    	  check=true;
	     }
	     
	     if(!bookid.isEmpty()){
	    	 if(check)
	    		 query+=" AND";
	    	 query+=" BL.bookId="+bookid;
	    	 check1=true;
	     }
	     if(!check){
	     if(!fname.isEmpty()){
	    	 if(check1)
	    		 query+=" AND ";
	    	 query+=" B.b_fname="+"'"+fname+"'"; 
	    	 check2=true;
	     }
	     if(!lname.isEmpty()){
	    	 if(check2)
	    		 query+=" AND ";
	    	 query+=" B.b_lname="+"'"+lname+"'";   
	     }
	 
	     } 
	     query+=" AND BL.datein is null;";
	     
	    Select sel=new Select();
	    System.out.println(query);
	 	ResultSet rs=null;
	 	try {
			rs=sel.selectData(query);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String loan_id=null,bookId=null,branchId=null,card_no=null,date_out=null,date_due=null,fname=null,lname=null;
	    String[][] res=new String[100][8];
	    int i=0,j=0;
	 	try {
			while(rs.next()){
				j=0;
				loan_id=rs.getString(1);
				bookId=rs.getString(2);
				branchId=rs.getString(3);
				card_no=rs.getString(4);
				date_out=rs.getString(5);
				date_due=rs.getString(6);
				fname=rs.getString(7);
				lname=rs.getString(8);
				
				res[i][j]=loan_id;j++;
				res[i][j]=bookId;j++;
				res[i][j]=branchId;j++;
				res[i][j]=card_no;j++;
				res[i][j]=date_out;j++;
				res[i][j]=date_due;j++;		
				res[i][j]=fname;j++;
				res[i][j]=lname;j++;
				i++;				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	return res;	
	   }
}
