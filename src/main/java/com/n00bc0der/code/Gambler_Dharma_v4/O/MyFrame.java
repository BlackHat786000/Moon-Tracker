package com.n00bc0der.code.Gambler_Dharma_v4.O;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = -8748367649542579512L;

	Container c;
	JLabel label0, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12;
	JTextField name, salias, sname, slatitude, slongitude, stimezone, duration, moonss;
	JButton btn;
	JXDatePicker picker;
	JComboBox<String> hour, minute, second, stadium;

	String h[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23" };
	String m[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
			"35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59" };
	String s[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
			"35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59" };

	volatile boolean flag = true;

	MyFrame() {
		
		List<String> lstadiums = new ArrayList<String>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stadiums", "root", "root");
			String query = "select name from stadiums";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet set = pst.executeQuery();
			while (set.next()) {
				lstadiums.add(set.getString(1));
			}
			lstadiums.add("UPDATE EXISTING TIMEZONE");
			lstadiums.add("ADD NEW STADIUM");
			con.close();
		} catch (Exception e) {
			System.out.println("\nHuh huh  <(-_-)>  Something went wrong while retrieving your stadiums list from database....\n");
			System.out.println(e);
		}

		setTitle("MOON TRANSIT TRACKER v5.2");

		setSize(450, 500);
		setLocation(100, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		c = getContentPane();
		c.setLayout(null);

		label0 = new JLabel("ENTER SPORTS CONTEST DETAILS");
		label1 = new JLabel("Enter Name");
		label2 = new JLabel("Enter Date");
		label3 = new JLabel("Enter Time");
		label4 = new JLabel("Enter Stadium");
		label5 = new JLabel("ADD STADIUM ONCE IF NOT AVAILABLE IN ABOVE LIST :");
		label6 = new JLabel("Enter Alias");
		label7 = new JLabel("Enter Name");
		label8 = new JLabel("Enter Latitude");
		label9 = new JLabel("Enter Longitude");
		label10 = new JLabel("Enter Timezone");
		label11 = new JLabel("Enter Duration");
		label12 = new JLabel("Include Moon Sub Sub");

		label0.setBounds(10, 5, 230, 40);
		label1.setBounds(10, 50, 100, 20);
		label2.setBounds(10, 80, 100, 20);
		label3.setBounds(10, 110, 100, 20);
		label4.setBounds(10, 140, 100, 20);
		label11.setBounds(10, 170, 230, 20);
		label12.setBounds(10, 200, 200, 10);
		label5.setBounds(10, 215, 370, 20);
		label6.setBounds(10, 240, 230, 20);
		label7.setBounds(10, 270, 230, 20);
		label8.setBounds(10, 300, 230, 20);
		label9.setBounds(10, 330, 230, 20);
		label10.setBounds(10, 360, 230, 20);

		name = new JTextField();
		name.setBounds(120, 50, 280, 20);

		picker = new JXDatePicker();
		picker.setDate(Calendar.getInstance().getTime());
		picker.setFormats(new SimpleDateFormat("d-M-yyyy"));
		picker.setBounds(120, 80, 120, 20);

		hour = new JComboBox<String>(h);
		hour.setBounds(120, 110, 40, 20);
		minute = new JComboBox<String>(m);
		minute.setBounds(160, 110, 40, 20);
		second = new JComboBox<String>(s);
		second.setBounds(200, 110, 40, 20);

		stadium = new JComboBox<String>(lstadiums.toArray(new String[0]));
		stadium.setBounds(120, 140, 280, 20);

		duration = new JTextField();
		duration.setBounds(120, 170, 280, 20);
		duration.setText("4");
		moonss = new JTextField();
		moonss.setBounds(150, 195, 280, 20);
		moonss.setText("NO");

		salias = new JTextField();
		salias.setBounds(120, 240, 280, 20);
		sname = new JTextField();
		sname.setBounds(120, 270, 280, 20);
		slatitude = new JTextField();
		slatitude.setBounds(120, 300, 280, 20);
		slongitude = new JTextField();
		slongitude.setBounds(120, 330, 280, 20);
		stimezone = new JTextField();
		stimezone.setBounds(120, 360, 280, 20);
		salias.setEditable(false);
    	sname.setEditable(false);
    	slatitude.setEditable(false);
    	slongitude.setEditable(false);
    	stimezone.setEditable(false);

		btn = new JButton("PREDICT");
		btn.setBounds(170, 400, 90, 40);

		c.add(label0);
		c.add(label1);
		c.add(label2);
		c.add(label3);
		c.add(label4);
		c.add(label5);
		c.add(label6);
		c.add(label7);
		c.add(label8);
		c.add(label9);
		c.add(label10);
		c.add(label11);
		c.add(label12);
		c.add(name);
		c.add(picker);
		c.add(hour);
		c.add(minute);
		c.add(second);
		c.add(stadium);
		c.add(duration);
		c.add(moonss);
		c.add(label5);
		c.add(salias);
		c.add(sname);
		c.add(slatitude);
		c.add(slongitude);
		c.add(stimezone);
		c.add(btn);
		
		stadium.addActionListener(new ActionListener() {
            @SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent event) {
            	
                // Get the source of the component, which is our combo box.
                JComboBox MODE = (JComboBox) event.getSource();
                
                Object selected = MODE.getSelectedItem();
                if(!selected.toString().equals("ADD NEW STADIUM") && !selected.toString().equals("UPDATE EXISTING TIMEZONE") ) {
                	try {
            			Class.forName("com.mysql.cj.jdbc.Driver");
            			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stadiums", "root", "root");
            			String query = "select * from stadiums where name = ?";
            			PreparedStatement pst = con.prepareStatement(query);
            			pst.setString(1, selected.toString());
            			ResultSet set = pst.executeQuery();
            			while (set.next()) {
            				sname.setText(set.getString(2));
            				salias.setText(set.getString(1));
            				slatitude.setText(set.getString(3));
            				slongitude.setText(set.getString(4));
            				stimezone.setText(set.getString(5));
            			} con.close();
            		} catch (Exception e) {
            			System.out.println(
            					"\nHuh huh  <(-_-)>  Something went wrong while retrieving your stadium from database....\n");
            			System.out.println(e);
            		}
                	salias.setEditable(false);
                	sname.setEditable(false);
                	slatitude.setEditable(false);
                	slongitude.setEditable(false);
                	stimezone.setEditable(false);
                } else if(selected.toString().equals("ADD NEW STADIUM")) {
                	sname.setText("");
                	salias.setText("");
                	slatitude.setText("");
                	slongitude.setText("");
                	stimezone.setText("");
                	salias.setEditable(true);
                	sname.setEditable(true);
                	slatitude.setEditable(true);
                	slongitude.setEditable(true);
                	stimezone.setEditable(true);
                } else if(selected.toString().equals("UPDATE EXISTING TIMEZONE")) {
                	salias.setEditable(false);
                	sname.setEditable(false);
                	slatitude.setEditable(false);
                	slongitude.setEditable(false);
                	stimezone.setEditable(true);
                }
            }
        });

		final MyFrame frame = this;
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				synchronized (frame) {
					frame.notify();
				}

				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		setResizable(false);
		setVisible(true);

		synchronized (this)

		{
			try {
				this.wait();
			} catch (InterruptedException ex) {
			}
		}

	}
}