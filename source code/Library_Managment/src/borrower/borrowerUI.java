package borrower;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import dbConnection.Select;
import dbConnection.dbconnection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import libMang.homePage;

import java.awt.Font;

@SuppressWarnings("unused")
public class borrowerUI {

	private JFrame browframe;
	private JTextField textFname;
	private JTextField textLname;
	private JTextField textAdd;
	private JTextField textcity;
	private JTextField textstate;
	private JTextField textcntNo;

	public borrowerUI() {
		initialize();
	}

	private void initialize() {
		browframe = new JFrame();
		browframe.setTitle("Library Management System");
		browframe.setBounds(100, 100, 450, 300);
		browframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		browframe.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblPleaseEnterThe = new JLabel("Please Enter The Following Details");
		lblPleaseEnterThe.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 14));
		lblPleaseEnterThe.setForeground(Color.BLUE);
		browframe.getContentPane().add(lblPleaseEnterThe, "6, 2");
		
		JLabel lblNewLabel = new JLabel("First Name");
		browframe.getContentPane().add(lblNewLabel, "4, 4, right, default");
		
		textFname = new JTextField();
		browframe.getContentPane().add(textFname, "6, 4, left, default");
		textFname.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Last Name");
		browframe.getContentPane().add(lblNewLabel_1, "4, 6, right, default");
		
		textLname = new JTextField();
		browframe.getContentPane().add(textLname, "6, 6, left, default");
		textLname.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Address");
		browframe.getContentPane().add(lblNewLabel_2, "4, 8, right, default");
		
		textAdd= new JTextField();
		browframe.getContentPane().add(textAdd, "6, 8, left, default");
		textAdd.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("City");
		browframe.getContentPane().add(lblNewLabel_3, "4, 10, right, default");
		
		textcity = new JTextField();
		browframe.getContentPane().add(textcity, "6, 10, left, default");
		textcity.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("State");
		browframe.getContentPane().add(lblNewLabel_4, "4, 12, right, default");
		
		textstate = new JTextField();
		browframe.getContentPane().add(textstate, "6, 12, left, default");
		textstate.setColumns(10);
		
		JLabel lblEnterTheContact = new JLabel("Enter the Contact number in the format (XXX) XXX-XXXX");
		lblEnterTheContact.setFont(new Font("Arial Black", Font.ITALIC, 11));
		lblEnterTheContact.setForeground(Color.RED);
		browframe.getContentPane().add(lblEnterTheContact, "6, 14");
		
		JLabel lblNewLabel_5 = new JLabel("Contact No");
		browframe.getContentPane().add(lblNewLabel_5, "4, 16, right, default");
		
		textcntNo = new JTextField();
		browframe.getContentPane().add(textcntNo, "6, 16, left, default");
		textcntNo.setColumns(10);
		browframe.setVisible(true);
		
		JButton btnRegister = new JButton("REGISTER");
		browframe.getContentPane().add(btnRegister, "4, 18");
		btnRegister.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String fname=textFname.getText();
				String lname=textLname.getText();
				String address=textAdd.getText();
				String city=textstate.getText();
				String state=textstate.getText();
				String contact=textcntNo.getText();
				dbconnection selObj=new dbconnection();
				//Select selObj=new Select();
				ResultSet rs=null;
				ResultSet rs1=null;
				int cardNo=0;
				String query=null,query2=null,query1=null;
				if(fname.equals("") || lname.equals("") || address.equals("") || city.equals("") || state.equals("") || contact.equals(""))
				{
					JOptionPane.showMessageDialog(browframe, "Please Enter All The Details");
				}
				else{
					if(state.length()>2){
						JOptionPane.showMessageDialog(browframe, "Invalid State Value. Please Enter 2 character value for state");
						textstate.setText("");
					}
					else if(validate(contact)==false){
						JOptionPane.showMessageDialog(browframe, "Invalid Contact Number Value. Please Enter Value in specified format");
						textcntNo.setText("");
					}
					else{
						query="select cardno from borrower where b_fname like '%"+fname+"%' and b_lname like '%"+lname+"%' and address like '%"+address+"%' and city like '%"+city+"%' and state like '%"+state+"%';";
			         	System.out.println(query);
			         	try {
							 rs=selObj.select(query);
			         		//rs=selObj.selectData(query);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
			         	try {
							if(rs.next())
							{
								JOptionPane.showMessageDialog(browframe, "Registration Failed As Borrower With Same Details Already exists");
								textFname.setText("");
								textLname.setText("");
								textAdd.setText("");
								textcity.setText("");
								textstate.setText("");
								textcntNo.setText("");
								rs.close();
							}
							else
							{
								query1="select MAX(cardno) from borrower;";
								//rs1=selObj.selectData(query1);
								rs1=selObj.select(query1);
								if(rs1.next())
								cardNo = rs1.getInt(1);
								rs1.close();
								System.out.println(cardNo);
								cardNo+=1;
								System.out.println(cardNo);
								query2="insert into borrower values('"+cardNo+" ','"+fname+" ','"+lname+" ','"+address+" ','"+city+" ','"+state+" ','"+contact+" ')";
								selObj.update(query2);
								//selObj.updateQuery(query2);
								textFname.disable();
								textLname.disable();
								textAdd.disable();
								textcity.disable();
								textstate.disable();
								textcntNo.disable();
								JOptionPane.showMessageDialog(browframe, "Registration successful: Your Card No is"+cardNo);
								//frame.setVisible(false);
								//bookSearch bs=new bookSearch();
							}
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		JButton btnHome = new JButton("HOME");
		browframe.getContentPane().add(btnHome, "6, 18, left, default");
		btnHome.addActionListener(new ActionListener() {
		//	@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
			home.homePage.main(null);
			browframe.setVisible(false);
			}
		});
		
	}
	public boolean validate(String phoneNo){
		String cellNo=phoneNo;
		if(phoneNo.length()>14 || ((cellNo.charAt(0)=='(') && (cellNo.charAt(4)==')') && (cellNo.charAt(5)==' ') && (cellNo.charAt(9)=='-'))){
			return true;
		}
		else
			return false;
	}
}
