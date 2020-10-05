package javaapplication1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utility {
	public Connection con=null;
	private static Utility instance=null;
	private Utility() {}
	public static Utility getInstance() {
		if(instance==null) {
			instance=new Utility();
		}
		return instance;
	}
	public void openConnection() {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		con= DriverManager.getConnection("jdbc:mysql://localhost:3307/CV19?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
                        e.getMessage();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void closeConnection() {
		try {
		con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
