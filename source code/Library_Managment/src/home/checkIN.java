package home;
//import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.text.SimpleDateFormat;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Font;


public class checkIN {

	private JFrame checkInframe;
	private JTextField txtbookId;
	private JTextField textcardNo;
	private JTextField textFname;
	private JTextField textLname;
	private JLabel lblNewLabel_4;
	private JButton btnLocate;
	private JButton btnCheckIn;
	private JScrollPane loanPane;
	private JTable loanTable;
	TableModel model=null;
	String[][] res=new String[100][8];
	String loan_id=null;
	private JButton btnHome;

	public checkIN() {
		initialize();
	}
		
	private void initialize() {
		checkInframe = new JFrame();
		checkInframe.setTitle("Library Management System");
		checkInframe.setBounds(100, 100, 450, 300);
		checkInframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		checkInframe.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
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
				RowSpec.decode("default:grow"),}));
		
		lblNewLabel_4 = new JLabel("Please Enter The Following Details");
		lblNewLabel_4.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 14));
		lblNewLabel_4.setForeground(Color.BLUE);
		checkInframe.getContentPane().add(lblNewLabel_4, "6, 2");
		
		JLabel lblNewLabel = new JLabel("Book ID :");
		checkInframe.getContentPane().add(lblNewLabel, "4, 4, right, default");
		
		txtbookId = new JTextField();
		checkInframe.getContentPane().add(txtbookId, "6, 4, left, default");
		txtbookId.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Card Number:");
		checkInframe.getContentPane().add(lblNewLabel_1, "4, 6, right, default");
		
		textcardNo = new JTextField();
		textcardNo.setColumns(10);
		checkInframe.getContentPane().add(textcardNo, "6, 6, left, default");
		
		JLabel lblNewLabel_2 = new JLabel("Borrowers First Name:");
		checkInframe.getContentPane().add(lblNewLabel_2, "4, 8, right, default");
		
		textFname = new JTextField();
		textFname.setColumns(10);
		checkInframe.getContentPane().add(textFname, "6, 8, left, default");
		
		JLabel lblNewLabel_3 = new JLabel("Borrowers Last Name:");
		checkInframe.getContentPane().add(lblNewLabel_3, "4, 10, right, default");
		
		textLname= new JTextField();
		checkInframe.getContentPane().add(textLname, "6, 10, left, default");
		textLname.setColumns(10);
		
		btnHome = new JButton("HOME");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homePage.main(null);
				checkInframe.setVisible(false);
			}
		});
		checkInframe.getContentPane().add(btnHome, "2, 12");
		
		loanPane = new JScrollPane();
		checkInframe.getContentPane().add(loanPane, "6, 14, fill, fill");
		
		loanTable = new JTable();
		loanPane.setRowHeaderView(loanTable);
		
		btnLocate = new JButton("LOCATE BOOK");
		checkInframe.getContentPane().add(btnLocate, "6, 12, left, default");
		
		checkInframe.setVisible(true);
		btnLocate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String bookid=txtbookId.getText();
				String cardno=textcardNo.getText();
				String fname=textFname.getText();
				String lname=textLname.getText();
				if(bookid.isEmpty() && cardno.isEmpty() && fname.isEmpty() && lname.isEmpty()){
					JOptionPane.showMessageDialog(checkInframe, "Please Enter All The Details");
				}
				else
				{
					checkInLogic cb=new checkInLogic(bookid,cardno,fname,lname);
					res=cb.buildQuery();
					String [] columnNames = {"LOAN ID","BOOK ID","BRANCH ID","CARD NUMBER","DATE OUT","DATE DUE","FIRST NAME","LAST NAME"};
					model = new DefaultTableModel(res, columnNames);
					loanTable.setModel(model);
				}
				
			}
		});
		loanTable.addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseClicked(MouseEvent e) {
				int row = loanTable.rowAtPoint(new Point(e.getX(), e.getY()));    
				loan_id=(loanTable.getModel().getValueAt(row, 0)).toString();
			}
		});
		
		btnCheckIn = new JButton("CHECK IN");
		checkInframe.getContentPane().add(btnCheckIn, "4, 12");
		btnCheckIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int success;
				checkBook cB=new checkBook(loan_id);
				success=cB.checkInbook();
				if(success==1)
					JOptionPane.showMessageDialog(checkInframe, "Book Checked In Successfully");
				else
					JOptionPane.showMessageDialog(checkInframe, "Failed to checked in book");
			}
		});
	}
}