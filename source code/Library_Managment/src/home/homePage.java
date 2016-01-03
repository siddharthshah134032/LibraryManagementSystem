package home;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;

import bookSearch.bookSearchLogic;
import borrower.borrowerUI;
import checkOut.checkOUT;
import checkOut.checkOutUI;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import dbConnection.Select;
import fines.bookLoan;

import javax.swing.JOptionPane;
//import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Font;

public class homePage {

	private JFrame homeframe;
	private JTextField textbookid;
	private JTextField texttitle;
	private JTextField textauthor;
	private JTextField textfname;
	private JTextField textmi;
	private JTextField textlname;
	private JScrollPane scrollPane;
	private JTable bookSearchtable;
	TableModel model=null;
	private JButton btncheckIN;
	private JButton btnCheckOut;
	private JButton btnRegisterBorrower;
	private JLabel lblEnterTheDetails;
	String bookid=null,branchid=null;
	String copies=null;
	private JButton btnNewButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					homePage window = new homePage();
					window.initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public homePage() {
		//initialize();
	}

	private void initialize() {
		homeframe = new JFrame();
		homeframe.setTitle("Library Management System");
		homeframe.setBounds(100, 100, 500, 500);
		homeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeframe.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		lblEnterTheDetails = new JLabel("Please enter the details below to search the book.");
		lblEnterTheDetails.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 14));
		lblEnterTheDetails.setBackground(new Color(240, 240, 240));
		lblEnterTheDetails.setForeground(Color.BLUE);
		homeframe.getContentPane().add(lblEnterTheDetails, "6, 2");
		
		JLabel lblNewLabel = new JLabel("Book Id");
		homeframe.getContentPane().add(lblNewLabel, "4, 4, right, default");
		
		textbookid = new JTextField();
		homeframe.getContentPane().add(textbookid, "6, 4, left, default");
		textbookid.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Title");
		homeframe.getContentPane().add(lblNewLabel_1, "4, 6, right, default");
		
		texttitle = new JTextField();
		homeframe.getContentPane().add(texttitle, "6, 6, left, default");
		texttitle.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Author");
		homeframe.getContentPane().add(lblNewLabel_3, "4, 8, right, default");
		
		textauthor = new JTextField();
		homeframe.getContentPane().add(textauthor, "6, 8, left, default");
		textauthor.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Authors First Name");
		homeframe.getContentPane().add(lblNewLabel_2, "4, 10, right, default");
		
		textfname = new JTextField();
		homeframe.getContentPane().add(textfname, "6, 10, left, default");
		textfname.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Authors MI");
		homeframe.getContentPane().add(lblNewLabel_4, "4, 12, right, default");
		
		textmi = new JTextField();
		homeframe.getContentPane().add(textmi, "6, 12, left, default");
		textmi.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Authors Last Name");
		homeframe.getContentPane().add(lblNewLabel_5, "4, 14, right, default");
		
		textlname = new JTextField();
		homeframe.getContentPane().add(textlname, "6, 14, left, default");
		textlname.setColumns(10);
		homeframe.setVisible(true);
		
		JButton btnSearch = new JButton("SEARCH");
		homeframe.getContentPane().add(btnSearch, "2, 16");
		
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String bookid=textbookid.getText();
			    String title=texttitle.getText();
			    String author=textauthor.getText();
			    String fName=textfname.getText();
			    String lName=textlname.getText();
			    String mi=textmi.getText();
			    ResultSet rs=null;
			    boolean check=false;
			    Select db=new Select();
			    if(bookid.equals("") && title.equals("") && author.equals("") && fName.equals("") && lName.equals("") && mi.equals(""))
				{
					JOptionPane.showMessageDialog(homeframe, "Please Enter All The Details");
				}
			    else{
			    System.out.println("bookId"+bookid);
			    if(!bookid.isEmpty()){
			    	if(bookid.length() <10 ||bookid.length()>10){
			    		JOptionPane.showMessageDialog(homeframe, "Please Enter Valid 10 Digit Book Id");
			    		textbookid.setText("");
			    	}
			    	else{
			    		String query="select bookId from book;";
				    try {
				    	System.out.println(query);
				    	System.out.println("in try.....");
						rs=db.selectData(query);
						while(rs.next()){
							String val=rs.getString(1);
							if(val.equalsIgnoreCase(bookid)){
								check=true;
								break;
							}
						}
						if(!check){
							JOptionPane.showMessageDialog(homeframe, "This Book Id is not valid. Enter Valid Book Id.....!!!");
							textbookid.setText("");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
			    }
			    String[][] result=new String[100][8];
			    title=validate_string(title);
			    author=validate_string(author);
			    bookSearchLogic bs=new bookSearchLogic(bookid, title, author, fName, mi,lName);
			    result=bs.searchbook();
			   // result1=bs.removedup(res, n)
			    String [] columnNames = {"BOOKID","TITLE","AUTHOR","BRANCH ID","NO OF COPIES","AVAILABLE COPIES"};
			    
			    model = new DefaultTableModel(result, columnNames);
			    bookSearchtable.setModel(model);
			}
			}
		});
		
		btnNewButton = new JButton("SHOW FINES");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookLoan bLoan=new bookLoan();
				homeframe.setVisible(false);
			}
		});
		homeframe.getContentPane().add(btnNewButton, "4, 16");
		
		btnCheckOut = new JButton("CHECK OUT");
		homeframe.getContentPane().add(btnCheckOut, "2, 18");
		btnCheckOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				checkOUT cO=new checkOUT();
				homeframe.setVisible(false);
			}
		});
		
		btncheckIN = new JButton("CHECK IN");
		btncheckIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkIN cIN=new checkIN();
				homeframe.setVisible(false);
			}
		});
		homeframe.getContentPane().add(btncheckIN, "4, 18, fill, default");
		
		btnRegisterBorrower = new JButton("REGISTER BORROWER");
		homeframe.getContentPane().add(btnRegisterBorrower, "6, 18, left, default");
		btnRegisterBorrower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrowerUI bUI=new borrowerUI();
				homeframe.setVisible(false);	
			}
		});
		scrollPane = new JScrollPane();
		homeframe.getContentPane().add(scrollPane, "6, 20, fill, fill");
		
		bookSearchtable = new JTable();
		scrollPane.setViewportView(bookSearchtable);
		bookSearchtable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int row = bookSearchtable.rowAtPoint(new Point(e.getX(), e.getY()));    
				bookid=(bookSearchtable.getModel().getValueAt(row, 0)).toString();
				branchid=(bookSearchtable.getModel().getValueAt(row, 3)).toString();
				//copies=(int)(bookSearchtable.getModel().getValueAt(row, 8));
				copies=(bookSearchtable.getModel().getValueAt(row, 5)).toString();
				int b=0;
				String val=null;
				val=Integer.toString(b);
				if(copies.equalsIgnoreCase(val)){
					JOptionPane.showMessageDialog(homeframe, "Required Book Is Not Available in this branch Please Select Other Branch....!!!");
				}
				else
				{
 				checkOutUI cOUT=new checkOutUI(bookid,branchid,copies);
				homeframe.setVisible(false);
				}
				
			}
		});
	}
	
	public String validate_string(String svalue)
	{		
		int i=0;
		char val;
		String finalString="";
		if(!svalue.isEmpty()){
			while(i<svalue.length()){
				val=svalue.charAt(i);
				if((char) val != '\''){
					finalString+=val;					
				}
				else{
					finalString+='\\';
					finalString+='\'';
					}
				i++;
			}
		}
		return finalString;		
	}

}
