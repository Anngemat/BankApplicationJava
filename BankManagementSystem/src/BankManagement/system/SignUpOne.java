package BankManagement.system;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
public class SignUpOne extends JFrame {

	Random rand = new Random();
	int randomNumber = rand.nextInt(10000) + 1000;
	public SignUpOne(){
		//General Qs
		setSize(600,479);
		getContentPane().setBackground(Color.white);
		setLayout(null);
		setLocation(550,300);
		setTitle("ATM SignUp");
		setVisible(true);
		
		//Labels 
		JLabel formNo = new JLabel("FORM NO: " + randomNumber);
		formNo.setFont(new Font ("Arial",Font.BOLD,30));
		formNo.setBounds(175,20,600,30);
		add(formNo);
		
		JLabel personalDetails = new JLabel("Page 1: Personal Details ");
		personalDetails.setFont(new Font ("Arial",Font.BOLD,15));
		personalDetails.setBounds(200,50,600,20);
		add(personalDetails);
		
		JLabel name = new JLabel("Name: ");
		name.setFont(new Font ("Arial",Font.BOLD,15));
		name.setBounds(100,80,600,20);
		add(name);
		
		JLabel surName = new JLabel("Surname: ");
		surName.setFont(new Font ("Arial",Font.BOLD,15));
		surName.setBounds(100,110,600,20);
		add(surName);
		
		JLabel fathersName = new JLabel("Fathers Name: ");
		fathersName.setFont(new Font ("Arial",Font.BOLD,15));
		fathersName.setBounds(100,140,600,20);
		add(fathersName);
		
		JLabel dot = new JLabel("Date of Birth: ");
		dot.setFont(new Font ("Arial",Font.BOLD,15));
		dot.setBounds(100,170,600,20);
		add(dot);
		
		JLabel gender = new JLabel("Gender: ");
		gender.setFont(new Font ("Arial",Font.BOLD,15));
		gender.setBounds(100,200,600,20);
		add(gender);
		
		JLabel email = new JLabel("Email: ");
		email.setFont(new Font ("Arial",Font.BOLD,15));
		email.setBounds(100,230,600,20);
		add(email);
		
		JLabel marital = new JLabel("Marital Status: ");
		marital.setFont(new Font ("Arial",Font.BOLD,15));
		marital.setBounds(100,260,600,20);
		add(marital);
		
		JLabel city = new JLabel("City: ");
		city.setFont(new Font ("Arial",Font.BOLD,15));
		city.setBounds(100,290,600,20);
		add(city);
		
		JLabel state = new JLabel("State: ");
		state.setFont(new Font ("Arial",Font.BOLD,15));
		state.setBounds(100,320,600,20);
		add(state);
		
		JLabel pincode = new JLabel("Pincode: ");
		pincode.setFont(new Font ("Arial",Font.BOLD,15));
		pincode.setBounds(100,350,600,20);
		add(pincode);
		
		//Textfields
		JTextField nameText = new JTextField("");
		nameText.setFont(new Font ("Arial",Font.BOLD,15));
		nameText.setBounds(300,80,150,20);
		add(nameText);
		
		JTextField surNameText = new JTextField("");
		surNameText.setFont(new Font ("Arial",Font.BOLD,15));
		surNameText.setBounds(300,110,150,20);
		add(surNameText);
		
		JTextField fatherNameText = new JTextField("");
		fatherNameText.setFont(new Font ("Arial",Font.BOLD,15));
		fatherNameText.setBounds(300,140,150,20);
		add(fatherNameText);
		
		JTextField emailText = new JTextField("");
		emailText.setFont(new Font ("Arial",Font.BOLD,15));
		emailText.setBounds(300,230,150,20);
		add(emailText);
		
		JTextField cityText = new JTextField("");
		cityText.setFont(new Font ("Arial",Font.BOLD,15));
		cityText.setBounds(300,290,150,20);
		add(cityText);
		
		JTextField stateText = new JTextField("");
		stateText.setFont(new Font ("Arial",Font.BOLD,15));
		stateText.setBounds(300,320,150,20);
		add(stateText);
		
		JTextField pincodeText = new JTextField("");
		pincodeText.setFont(new Font ("Arial",Font.BOLD,15));
		pincodeText.setBounds(300,350,150,20);
		add(pincodeText);
		
		//Date Chooser
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(300,170,150,20);
		dateChooser.setForeground(new Color(105,105,105));
		add(dateChooser);
		
		//Radio Buttons
		JRadioButton malerb = new JRadioButton("Male");
		JRadioButton femalerb = new JRadioButton("Female");
		JRadioButton singlerb = new JRadioButton("Single");
		JRadioButton marriedrb = new JRadioButton("Married");
		malerb.setBounds(300,200,70,20);
		malerb.setBackground(Color.white);
		singlerb.setBackground(Color.white);
		marriedrb.setBackground(Color.white);
		femalerb.setBackground(Color.white);
		add(malerb);
		femalerb.setBounds(370,200,70,20);
		add(femalerb);
		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(femalerb);
		genderGroup.add(malerb);
		ButtonGroup maritalGroup = new ButtonGroup();
		maritalGroup.add(marriedrb);
		maritalGroup.add(singlerb);
		add(marriedrb);
		add(singlerb);
		marriedrb.setBounds(370,260,70,20);
		singlerb.setBounds(300,260,70,20);
		
		//button
		JButton nextButton = new JButton("Next");
		nextButton.setBackground(Color.white);
		nextButton.setForeground(Color.black);
		nextButton.setFont(new Font ("Arial",Font.BOLD,15));
		nextButton.setBounds(350,400,80,20);
		add(nextButton);
		
		setSize(600,480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		SignUpOne signup = new SignUpOne();
	}
}
