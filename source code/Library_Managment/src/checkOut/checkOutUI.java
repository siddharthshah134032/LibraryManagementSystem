package checkOut;



//import java.awt.EventQueue;
import home.checkIN;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import dbConnection.Select;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Color;

public class checkOutUI {

	private JFrame cOUTframe;
	private JTextField textbookid;
	private JTextField textbranchid;
	private JTextField textcardno;
	String bookID=null,branchID=null,copies=null;

	public checkOutUI(String bookid, String branchid, String copies) {
		this.bookID=bookid;
		this.branchID=branchid;
		this.copies=copies;
		initialize();
	}

	@SuppressWarnings("deprecation")
	private void initialize() {
		cOUTframe = new JFrame();
		cOUTframe.setTitle("Library Management System");
		cOUTframe.setBounds(100, 100, 450, 300);
		cOUTframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cOUTframe.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblPleaseEnterThe = new JLabel("Please Enter the card number");
		lblPleaseEnterThe.setForeground(Color.BLUE);
		lblPleaseEnterThe.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 14));
		cOUTframe.getContentPane().add(lblPleaseEnterThe, "8, 4");
		
		JLabel lblBookId = new JLabel("Book Id :");
		cOUTframe.getContentPane().add(lblBookId, "6, 6, right, default");
		
		textbookid = new JTextField();
		cOUTframe.getContentPane().add(textbookid, "8, 6, left, default");
		textbookid.setColumns(10);
		textbookid.setText(bookID);
		textbookid.disable();
		
		JLabel lblBranchId = new JLabel("Branch Id :");
		cOUTframe.getContentPane().add(lblBranchId, "6, 8, right, default");
		
		textbranchid = new JTextField();
		cOUTframe.getContentPane().add(textbranchid, "8, 8, left, default");
		textbranchid.setColumns(10);
		textbranchid.setText(branchID);
		textbranchid.disable();
		
		JLabel lblCardNo = new JLabel("Card No :");
		cOUTframe.getContentPane().add(lblCardNo, "6, 10, right, default");
		
		textcardno = new JTextField();
		cOUTframe.getContentPane().add(textcardno, "8, 10, left, default");
		textcardno.setColumns(10);
		
		JButton btnHome = new JButton("HOME");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				home.homePage.main(null);
				cOUTframe.setVisible(false);
			}
		});
		cOUTframe.getContentPane().add(btnHome, "4, 12");
		
		JButton btnCheckIn = new JButton("CHECK IN");
		cOUTframe.getContentPane().add(btnCheckIn, "6, 12");
		btnCheckIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				checkIN cin=new checkIN();
				cOUTframe.setVisible(false);
			}
		});
		
		JButton btncheckout= new JButton("CHECK OUT");
		cOUTframe.getContentPane().add(btncheckout, "8, 12, left, default");
		cOUTframe.setVisible(true);
		btncheckout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String cardno=textcardno.getText();
				if(cardno.isEmpty()){
					JOptionPane.showMessageDialog(cOUTframe, "Please Enter Card Number");
				}//end card empty
				if(!cardno.isEmpty())
					if(cardno.length()<4){
					JOptionPane.showMessageDialog(cOUTframe, "Enter valid 5 digit card number");
					textcardno.setText("");
					}//end of length less than 4
					else if(validate(cardno)){
						SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
						Calendar cal = new GregorianCalendar(/* remember about timezone! */);
			            java.util.Date todaysdate = cal.getTime();
						String todayDate = format.format(todaysdate);
						cal.add(Calendar.DATE, 14);
						java.util.Date dueDate = cal.getTime();
						String dueDate1 = format.format(dueDate);
						String bookid=textbookid.getText();
						String branchid=textbranchid.getText();
						String cardno1=textcardno.getText();
						Select db=new Select();
						ResultSet rs=null;
						int rs2=0;
						int bookLoan=0;
						String query1="Select MAX(loanId) from book_loan";
						try {
							rs=db.selectData(query1);
							while(rs.next()){
								bookLoan=rs.getInt(1);
							}//end while
							bookLoan+=1;
							String updateLoan="Insert into book_loan values("+bookLoan+",'"+bookid+"','"+branchid+"','"+cardno1+"','"+todayDate+"','"+dueDate1+"',"+null+");";
							System.out.println(updateLoan);
							System.out.println(bookLoan);
							rs2=db.updateQuery(updateLoan);
							if(rs2==1){
								JOptionPane.showMessageDialog(cOUTframe,"You borrowed book successfuly....!!!");
							}
							else{
								textcardno.disable();
								JOptionPane.showMessageDialog(cOUTframe,"Process of borrowing fail...!!!");
							}
						} //end of try
						catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
						}//end of catch
					}//end of if
					else
					{
						JOptionPane.showMessageDialog(cOUTframe, "This is not the valid card number. Please enter the valid card no...!!");
						textcardno.setText("");
					}
				}//action performed
		});//action listner
		
	}//initialized
	boolean validate(String carNO){
		String cardno=carNO;
		boolean check=false;
		ResultSet rs=null;
		Select db=new Select();
		String query="select cardno from borrower;";
		try {
			rs=db.selectData(query);
			while(rs.next()){
				String val=rs.getString(1);
				if(val.equalsIgnoreCase(cardno)){
					check=true;
					break;
				}//end if
			}//end while
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return check;
	}

}
