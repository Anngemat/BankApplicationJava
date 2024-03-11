package BankManagement.system;
import java.sql.*;
public class Conn {
	Connection c;
	Statement s;
	public Conn() {
		
		try {
			
			c = DriverManager.getConnection("jdbc:mysql:///bankmanagementsystem","root","1350005");
			s=c.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
