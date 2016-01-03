package bookSearch;

import java.sql.ResultSet;
import java.sql.SQLException;

import dbConnection.dbconnection;

public class bookSearchLogic {

	public String bookId,title,author_name,fname1,lname1,minit1; 
	ResultSet rs=null,rs3=null;
	String query=null,query3=null;
	String iselected=null;
	Boolean check=false,check1=false,check2=false,check3=false;
	public bookSearchLogic(String book_id,String title,String author,String fname,String minit,String lname)
	{
		this.bookId=book_id;
		this.title=title;
		this.author_name=author;
		this.fname1=fname;
		this.lname1=lname;
		this.minit1=minit;
	}
	public String[][] searchbook()
	{
		System.out.println(bookId);
		System.out.println(title);
		System.out.println(author_name);
		query="SELECT b.bookId,b.title,ba.author,ba.fname,ba.mi,ba.lname,bc.branchid,bc.noofcopies FROM BOOK b,BOOK_AUTHORS "
				+ "ba,BOOK_COPIES bc WHERE b.bookId=ba.bookId AND bc.noofcopies >0 AND b.bookId=bc.bookId AND b.bookId IN (SELECT b.bookId from Book WHERE ";
	
		if(!bookId.isEmpty()){
			System.out.println("in id not null");
			query+="b.bookID='"+bookId+"'";
		}		
		else
		{			
			if(!title.isEmpty()){	
				check=true;
				query+="b.title LIKE '%"+title+"%'";				
			}
			if(!author_name.isEmpty()){
				if(check)
					query+=" AND ";
				query+="ba.author LIKE '%"+author_name+"%'";
				check1=true;
			}
			if(!fname1.isEmpty()){
				if(check || check1)
					query+=" AND ";
				query+="ba.fname LIKE '%"+fname1+"%'";
				check2=true;
			}
			if(!minit1.isEmpty()){
				if(check || check1 || check2)
					query+=" AND ";
				query+="ba.mi LIKE '"+minit1+"'";
				check3=true;
			}
			if(!lname1.isEmpty()){
				if(check ||check1 || check2 || check3)
					query+=" AND ";
			    query+="ba.lname LIKE '%"+lname1+"%'";
			    
			}
		}
		query+=");";
		//dbConnection sel=new dbConnection();
		dbconnection sel=new dbconnection();
		ResultSet rs=null,rs1=null,rs2=null;
		
		//rs=sel.selectData(query);
		String query1=null,query2=null,book_id=null,title=null,author=null,branchid=null,noofcopies=null;
		String[][] result=new String[100][9];
		String[][] result1=new String[100][9];
		int i=0,j=0,k=0,l=0,m=0,n=0;
		try {
			rs=sel.select(query);
			while(rs.next()){
				j=0;
				book_id=rs.getString(1);
				title=rs.getString(2);
				author=rs.getString(3);
				branchid=rs.getString(7);
				noofcopies=rs.getString(8);
				
				result[i][j]=book_id;j++;
				result[i][j]=title;j++;
				result[i][j]=author;j++;	
				result[i][j]=branchid;j++;
				result[i][j]=noofcopies;j++;
				
				int a1=0,a2=0,a3=0;
				query1="select noofcopies from book_copies BC where BC.bookId="+"'"+book_id+"'"+ "and BC.branchId="+"'"+branchid+"'"+ ";";
				//rs1=sel.selectData(query1);
				rs1=sel.select(query1);
				while(rs1.next()){
					a1=rs1.getInt(1);
				}
				query2="select count(loanId) from book_loan BL where BL.bookId="+"'"+book_id+"'"+ "and BL.branchId="+"'"+branchid+"'"+ "and BL.datein IS NULL;"; 
				//rs2=sel.selectData(query2);
				rs2=sel.select(query2);
				while(rs2.next()){
					a2=rs2.getInt(1);
				}
				a3=a1-a2;
				result[i][j]=Integer.toString(a3);
				i++;
			}
			for(k=0;k<i;k++)
			{
				if(!result[k][0].equalsIgnoreCase("null"))
				{
					for(l=k+1;l<i;l++)
					{
						if(result[k][0].equals(result[l][0]))
						{
							if(result[k][3].equals(result[l][3]))
							{
							result[k][2]+=','+result[l][2];
							result[l][0]="null";
							}
						}
					}
					
				}
			}//end of for
			for(m=0;m<i;m++)
			{
				if(!result[m][0].equalsIgnoreCase("null"))
				{
					for(int x=0;x<=5;x++)
					{
						result1[n][x]=result[m][x];
					}
					n++;
				}
			};
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//res=removedup(res,i);
		return result1;
	}	
}
