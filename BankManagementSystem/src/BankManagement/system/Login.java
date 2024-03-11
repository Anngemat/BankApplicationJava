package BankManagement.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

	JButton loginButton,clearButton,signUpButton;
	JPasswordField pinTextField;
	JTextField cardNoTextField ;
	public Login(){
		//General Qs
		setSize(800,479);
		getContentPane().setBackground(Color.white);
		setLayout(null);
		setLocation(550,300);
		setTitle("ATM Login");
		setVisible(true);
		
		
		//BankIcon
		ImageIcon bankIcon = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
		Image bankIconScaled = bankIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		ImageIcon bankIconResized = new ImageIcon(bankIconScaled);
		JLabel bankIconLabel = new JLabel(bankIconResized);
		bankIconLabel.setBounds(70,10,100,100);
		add(bankIconLabel);
		
		
		//Texts
		JLabel welcomeText = new JLabel("Welcome to General ATM");
		welcomeText.setFont(new Font ("Arial",Font.BOLD,38));
		welcomeText.setBounds(200,40,600,40);
		add(welcomeText);
	
		JLabel cardNoText = new JLabel("Card No:");
		cardNoText.setFont(new Font ("Arial",Font.BOLD,28));
		cardNoText.setBounds(120,150,400,40);
		add(cardNoText);

		JLabel pinText = new JLabel("PIN :");
		pinText.setFont(new Font ("Arial",Font.BOLD,28));
		pinText.setBounds(120,220,400,40);
		add(pinText);
		
		//TextFields
		cardNoTextField = new JTextField();
		cardNoTextField.setBounds(300,155,300,30);
		cardNoTextField.setFont(new Font ("Arial",Font.BOLD,14));
		add(cardNoTextField);
		
		pinTextField = new JPasswordField();
		pinTextField.setBounds(300,225,300,30);
		
		add(pinTextField);
		
		//Buttons
		loginButton = new JButton("Login");
		loginButton.setBounds(325,300,100,30);
		loginButton.setForeground(Color.black);
		loginButton.setBackground(Color.white);
		loginButton.addActionListener(this);
		add(loginButton);
		
		clearButton = new JButton("Clear");
		clearButton.setBounds(450,300,100,30);
		clearButton.setForeground(Color.black);
		clearButton.setBackground(Color.white);
		clearButton.addActionListener(this);
		add(clearButton); 		
		
		signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(325,350,225,30);
		signUpButton.setForeground(Color.black);
		signUpButton.setBackground(Color.white);
		signUpButton.addActionListener(this);
		add(signUpButton); 		
		
		setSize(800,480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	public static void main(String[] args) {
		Login Login1 = new Login();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clearButton) {
			cardNoTextField.setText("");
			pinTextField.setText("");
		}
		else if (e.getSource() == signUpButton) {
			
		}
		else if (e.getSource() == loginButton) {
			
		}
		
	}
}
