package checkOut;

//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import dbConnection.dbconnection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

//import libMang.homePage;

public class checkOUT {

	private JFrame checkoutframe;
	private JTextField textbookId;
	private JTextField textbranchId;
	private JTextField textcardNo;

	public checkOUT() {
		initialize();
	}

	private void initialize() {
		checkoutframe = new JFrame();
		checkoutframe.setTitle("Library Managment System ");
		checkoutframe.setBounds(100, 100, 450, 300);
		checkoutframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		checkoutframe.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
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
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblEnterTheDetails = new JLabel("Enter The Details Below");
		checkoutframe.getContentPane().add(lblEnterTheDetails, "6, 2");
		
		JLabel lblBookId = new JLabel("Book ID :");
		checkoutframe.getContentPane().add(lblBookId, "4, 4, right, default");
		
		textbookId = new JTextField();
		checkoutframe.getContentPane().add(textbookId, "6, 4, left, default");
		textbookId.setColumns(10);
		
		JLabel lblBranchId = new JLabel("Branch Id :");
		checkoutframe.getContentPane().add(lblBranchId, "4, 6, right, default");
		
		textbranchId = new JTextField();
		checkoutframe.getContentPane().add(textbranchId, "6, 6, left, default");
		textbranchId.setColumns(10);
		
		JLabel lblCardNo = new JLabel("Card No :");
		checkoutframe.getContentPane().add(lblCardNo, "4, 8, right, default");
		
		textcardNo = new JTextField();
		checkoutframe.getContentPane().add(textcardNo, "6, 8, left, default");
		textcardNo.setColumns(10);
		
		JButton btnNewButton = new JButton("HOME");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				home.homePage.main(null);
				checkoutframe.setVisible(false);
			}
		});
		checkoutframe.getContentPane().add(btnNewButton, "4, 10");
		
		JButton btncheckOut = new JButton("CHECK OUT");
		checkoutframe.getContentPane().add(btncheckOut, "6, 10, left, default");
		checkoutframe.setVisible(true);
		
		btncheckOut.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String bookId=textbookId.getText();
				String branchId=textbranchId.getText();
				String cardno=textcardNo.getText();
				String status=null;
				if(bookId.equals("") || branchId.equals("") || cardno.equals(""))
				{
					JOptionPane.showMessageDialog(checkoutframe, "Please Enter All Details....!!!");
				}
				else{
					if(cardno.length()<4){
						JOptionPane.showMessageDialog(checkoutframe, "Enter valid 4 digit card number");
						textcardNo.setText("");
					}
				   if(validate_bookID(bookId)){
						if(validate_branch(branchId)){
							if(validate_cardno(cardno)){
								checkOutLogic cLogic=new checkOutLogic();
								try {
									status=cLogic.checkOut(bookId,branchId,cardno);
									JOptionPane.showMessageDialog(checkoutframe,status);
									textbookId.disable();
									textbranchId.disable();
									textcardNo.disable();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						else{
							JOptionPane.showMessageDialog(checkoutframe, "This is not the existing card number.Enter valid card number");
							textcardNo.setText("");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(checkoutframe, "Enter valid Branch ID");
						textbranchId.setText("");
					}
				   }
				   else{
					   JOptionPane.showMessageDialog(checkoutframe, "This is not the existing Book Id.Enter valid Book ID");
						textbookId.setText("");
				   }
			}//end of else-if
			}//end of action performed
		});//end of action listner
		
	}//end of initialization
	
	
	boolean validate_bookID(String bookid){
		boolean check1=false;
		String bookId=bookid;
		String query="select bookId from book;";
		ResultSet rs=null;
		dbconnection db=new dbconnection();
		//Select db=new Select();
		try {
			//rs=db.selectData(query);
			rs=db.select(query);
			while(rs.next()){
				String val=rs.getString(1);
				if(val.equalsIgnoreCase(bookId)){
					check1=true;
					break;
				}
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return check1;
	}
	
	boolean validate_cardno(String cardno){
		boolean check2=false;
		String query="select cardno from borrower;";	
		ResultSet rs=null;
		//Select db=new Select();
		dbconnection db=new dbconnection();
		try {
			//rs=db.selectData(query);
			rs=db.select(query);
			while(rs.next()){
				String val=rs.getString(1);
				if(val.equalsIgnoreCase(cardno)){
					check2=true;
					break;
				}
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return check2;
	}
	boolean validate_branch(String branchid){
		boolean check3=false;
		String branchId=branchid;
		String one=Integer.toString(1);
		String two=Integer.toString(2);
		String three=Integer.toString(3);
		String four=Integer.toString(4);
		String five=Integer.toString(5);
		if(branchId.equalsIgnoreCase(one)|| branchId.equalsIgnoreCase(two)||branchId.equalsIgnoreCase(three)||branchId.equalsIgnoreCase(four)||branchId.equalsIgnoreCase(five))
			check3=true;
		return check3;
	}
}
