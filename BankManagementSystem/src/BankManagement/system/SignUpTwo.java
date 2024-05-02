package BankManagement.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SignUpTwo extends JFrame implements ActionListener{
	
	JButton nextButton = new JButton("Next");
	public SignUpTwo() {
		setSize(600,479);
		getContentPane().setBackground(Color.white);
		setLocation(550,300);
		setVisible(true);
		setLayout(null);
		setTitle("Page 2");
		
		//Labels
		
		JLabel additionalDetails = new JLabel("Page 2: Additional Details ");
		additionalDetails.setFont(new Font ("Arial",Font.BOLD,15));
		additionalDetails.setBounds(200,50,600,20);
		add(additionalDetails);
		
		JLabel religion = new JLabel("Religion: ");
		religion.setFont(new Font ("Arial",Font.BOLD,15));
		religion.setBounds(100,80,600,20);
		add(religion);
		
		JLabel category = new JLabel("Category: ");
		category.setFont(new Font ("Arial",Font.BOLD,15));
		category.setBounds(100,110,600,20);
		add(category);
		
		JLabel income = new JLabel("Income: ");
		income.setFont(new Font ("Arial",Font.BOLD,15));
		income.setBounds(100,140,600,20);
		add(income);
		
		JLabel education = new JLabel("Educational ");
		education.setFont(new Font ("Arial",Font.BOLD,15));
		education.setBounds(100,170,600,20);
		add(education);
		
		JLabel qualification = new JLabel("Qualification: ");
		qualification.setFont(new Font ("Arial",Font.BOLD,15));
		qualification.setBounds(100,190,600,20);
		add(qualification);
		
		JLabel occupation = new JLabel("Occupation: ");
		occupation.setFont(new Font ("Arial",Font.BOLD,15));
		occupation.setBounds(100,220,600,20);
		add(occupation);
		
		JLabel tc = new JLabel("TC No: ");
		tc.setFont(new Font ("Arial",Font.BOLD,15));
		tc.setBounds(100,250,600,20);
		add(tc);
		
		JLabel serial = new JLabel("Serial number: ");
		serial.setFont(new Font ("Arial",Font.BOLD,15));
		serial.setBounds(100,280,600,20);
		add(serial);
		
		JLabel nationality = new JLabel("Nationality: ");
		nationality.setFont(new Font ("Arial",Font.BOLD,15));
		nationality.setBounds(100,310,600,20);
		add(nationality);
		
		JLabel existing = new JLabel("Existing Account: ");
		existing.setFont(new Font ("Arial",Font.BOLD,15));
		existing.setBounds(100,340,600,20);
		add(existing);
		
		//ComboBox
		String valReligion[] = {"Muslim","Christian","Jew","Hindu","Şinto","Other"};
		JComboBox religioncb = new JComboBox(valReligion);
		religioncb.setBounds(300,80,150,20);
		religioncb.setBackground(Color.white);
		add(religioncb);
		
		String valCategory[] = {"General","OBC","SC","ST","Other"};
		JComboBox categorycb = new JComboBox(valCategory);
		categorycb.setBounds(300,110,150,20);
		categorycb.setBackground(Color.white);
		add(categorycb);
		
		String incomeCategory[] = {"<20000","<50000","<100000","Other"};
		JComboBox incomecb = new JComboBox(incomeCategory);
		incomecb.setBounds(300,140,150,20);
		incomecb.setBackground(Color.white);
		add(incomecb);
		
		String educationValues[] = {"Non-Graduate","Bachelor","Masters","Phd"};
		JComboBox educationcb = new JComboBox(educationValues);
		educationcb.setBounds(300,180,150,20);
		educationcb.setBackground(Color.white);
		add(educationcb);
		
		String occupationValues[] = {"Salaried","Self-Employed","Student","Retired","Other"};
		JComboBox occupationcb = new JComboBox(occupationValues);
		occupationcb.setBounds(300,220,150,20);
		occupationcb.setBackground(Color.white);
		add(occupationcb);
		
		//Textfields
		
		JTextField tckimlik = new JTextField("");
		tckimlik.setFont(new Font ("Arial",Font.BOLD,15));
		tckimlik.setBounds(300,250,150,20);
		add(tckimlik);
		
		JTextField serialnumber = new JTextField("");
		serialnumber.setFont(new Font ("Arial",Font.BOLD,15));
		serialnumber.setBounds(300,280,150,20);
		add(serialnumber);
		
		JTextField nationalitytf = new JTextField("");
		nationalitytf.setFont(new Font ("Arial",Font.BOLD,15));
		nationalitytf.setBounds(300,310,150,20);
		add(nationalitytf);
		
		//radiobutton
		JRadioButton yesrb = new JRadioButton("Yes");
		JRadioButton norb = new JRadioButton("No");
		yesrb.setBounds(300,340,70,20);
		yesrb.setBackground(Color.white);
		norb.setBounds(370,340,70,20);
		norb.setBackground(Color.white);
		ButtonGroup yesnoGroup = new ButtonGroup();
		yesnoGroup.add(yesrb);
		yesnoGroup.add(norb);
		add(yesrb);
		add(norb);
		
		
		nextButton.setBackground(Color.white);
		nextButton.setForeground(Color.black);
		nextButton.setFont(new Font ("Arial",Font.BOLD,15));
		nextButton.setBounds(350,400,80,20);
		nextButton.addActionListener(this);
		add(nextButton);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,480);
	}
	
	public static void main(String[] args) {
		SignUpTwo signup = new SignUpTwo();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nextButton) {
			setVisible(false);
			new SignUpThree().setVisible(true);
		}
		
	}
}
