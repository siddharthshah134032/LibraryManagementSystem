package fines;

//import java.awt.EventQueue;

import home.homePage;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class bookLoan {

	private JFrame loanframe;
	private JTable table;
	String[][] result=new String[100][10];
	TableModel model=null;
	
	public bookLoan() {
		initialize();
	}

	private void initialize() {
		loanframe = new JFrame();
		loanframe.setTitle("Library Management System");
		loanframe.setBounds(100, 100, 450, 300);
		loanframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loanframe.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
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
				RowSpec.decode("default:grow"),}));
		
		JLabel lblFollowingsAreThe = new JLabel("Followings are the details of fines in system");
		lblFollowingsAreThe.setForeground(Color.BLUE);
		lblFollowingsAreThe.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 14));
		loanframe.getContentPane().add(lblFollowingsAreThe, "8, 2");
		loanframe.setVisible(true);
		
		JButton btnHome = new JButton("HOME");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homePage.main(null);
				loanframe.setVisible(false);
				
			}
		});
		loanframe.getContentPane().add(btnHome, "6, 4");
		
		JButton btnShowLoan = new JButton("SHOW LOAN");
		btnShowLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fineLogic fine=new fineLogic();
				result=fine.calculate_fine();
				 String [] columnNames = {"LOAN ID","BORROWERS FNAME","BORROWERS LNAME","CARD NO","FINE AMOUNT","PAID STATUS"};
				 model = new DefaultTableModel(result, columnNames);
				 table.setModel(model);
			}
		});
		loanframe.getContentPane().add(btnShowLoan, "6, 6");
		JScrollPane scrollPane = new JScrollPane();
		loanframe.getContentPane().add(scrollPane, "8, 8, fill, fill");
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

}
