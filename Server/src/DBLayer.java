import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//SINGLETON PATTERN
public class DBLayer {

	private static DBLayer dbl = new DBLayer();
	private static Connection con;
	private static Statement stmt;
	
	private DBLayer(){
		
		try{
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1?useSSL=false", "root", "gulsumg");
			stmt = con.createStatement();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
			
	}
	
	public static DBLayer getInstance(){
		return dbl;
	}
	
	public boolean insertToUsers(String username, String password, String email, String name, String surname, String type){
		if(username == null || password == null || email == null || type == null)
			return false;
		else{
			String query = "INSERT INTO Users VALUE ('"+ username + "','"+ password + "','"+ email + "','"+ name + "','"+ surname + "','"+ type + "', NULL);" ;  
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} //USER CREATION IN DATABASE
			return true;
		}
	}
	
	public boolean insertToMessages(int fromId, int toId, String emailMsg){
		if(fromId == -1 || toId == -1)
			return false;
		else{
			String query;
			//add the message to the database
			query = "INSERT INTO Messages VALUE (" + fromId + ", "+ toId + ", '"+ emailMsg + "', NOW());";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
			
		}
	}
	
	public String seeInbox(int toId){
		String buf = "";
		String query = "select * from Messages where to_id = " + toId;
		try{
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next()) buf = buf + "|"+rset.getString("from_id")+ "\t|" + rset.getString("msg") + "\t\t\t"+ "|"+ rset.getString("date_time")+"???";
		} catch (SQLException e){
			e.printStackTrace();
		}
		return buf;
	}
	
	public String seeOutbox(int fromId){
		String buf = "";
		String query = "select * from Messages where from_id = " + fromId;
		try{
		ResultSet rset = stmt.executeQuery(query);
		while(rset.next()) buf = buf + "|"+rset.getString("to_id")+ "\t|" + rset.getString("msg") + "\t\t\t"+ "|"+ rset.getString("date_time") +"???";
		} catch (SQLException e){
			e.printStackTrace();
		}
		return buf;
	}
	
	public String readUser(String username){
		String info = "";
		String query = "select * from Users where username = '" + username+"'";
		try{
		ResultSet rset = stmt.executeQuery(query);
		if(rset.next()) info = username + "\t\t| "+ rset.getString("password")+"\t\t| "+ rset.getString("email") + "\t| "+ rset.getString("name")+
							  "\t| "+ rset.getString("surname")  + "\t\t| "+ rset.getString("type") + "\t| "+ rset.getInt("user_id");
		} catch (SQLException e){
			e.printStackTrace();
		}
		return info;
		
	}
	
	public void updateUN(String un, String newUN){
		String query = "UPDATE Users SET username = + '"+newUN+ "' WHERE username = '" + un + "';";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updatePass(String un, String newPass){
		String query = "UPDATE Users SET password = + '"+newPass+ "' WHERE username = '" + un + "';";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateEmail(String un, String newEmail){
		String query = "UPDATE Users SET email = + '"+newEmail+ "' WHERE username = '" + un + "';";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateName(String un, String newName){
		String query = "UPDATE Users SET name = + '"+newName+ "' WHERE username = '" + un + "';";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateSurname(String un, String newSurname){
		String query = "UPDATE Users SET surname = + '"+newSurname+ "' WHERE username = '" + un + "';";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateType(String un, String newType){
		String query = "UPDATE Users SET type = + '"+newType+ "' WHERE username = '" + un + "';";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(String username){
		String query = "delete from Users where username = '" + username + "'";
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isPassValid(String username, String password){
		if(password == null || password.equals(""))
			return false;
		else{
			ResultSet rset = null;
			String query = "select * from Users where username = '"+username+"' and password = '"+password+"'";
			try {
				rset = stmt.executeQuery(query);
				if(rset.next()){					
					return true;
				}
				else 
					return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}	
		}
		
	}
	
	public String getTypeOfUser(String username){
		String type = "";
		String query = "select * from Users where username = '" + username + "'";
		ResultSet rset;
		try {
			rset = stmt.executeQuery(query);
			if(rset.next()) type = rset.getString("type");
				return type;
		} catch (SQLException e) {
			e.printStackTrace();
			return type;
		} 
		
	}
	
	public String getEmailOfUser(String username){
		String email = "";
		String query = "select * from Users where username = '" + username + "'";
		ResultSet rset;
		try {
			rset = stmt.executeQuery(query);
			if(rset.next()) email = rset.getString("email");
				return email;
		} catch (SQLException e) {
			e.printStackTrace();
			return email;
		} 
		
	}
	
	public int getUserIDWithUN(String username){
		int id = -1;
		String query = "select * from Users where username = '" + username + "'";
		ResultSet rset;
		try {
			rset = stmt.executeQuery(query);
			if(rset.next()) id = rset.getInt("user_id");
				return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		} 
	}
	public int getUserIDWithEmail(String email){
		int id = -1;
		String query = "select * from Users where email = '" + email + "'";
		ResultSet rset;
		try {
			rset = stmt.executeQuery(query);
			if(rset.next()) id = rset.getInt("user_id");
				return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		} 
	}
	
	public boolean doesEmailExist(String email){
		if(email == null || email.equals(""))
			return false;
		else{
			ResultSet rset = null;
			String query = "select * from Users where email = '" + email + "'";
			try {
				rset = stmt.executeQuery(query);
				if(rset.next()){					
					return true;
				}
				else 
					return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}			
		}
	}
	
	public boolean isUNValid(String username){
		if(username == null || username.equals(""))
			return false;
		else{
			ResultSet rset = null;
			String query = "select * from Users where username = '" + username + "'";//search in the database table whether if the specified username exists or not
			try {
				rset = stmt.executeQuery(query);
				if(!rset.next()){					
					return true;
				}
				else 
					return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}			
		}
	}
	
	public boolean doesUNExist(String username){
		if(username == null || username.equals(""))
			return false;
		else{
			ResultSet rset = null;
			String query = "select * from Users where username = '" + username + "'";//search in the database table whether if the specified username exists or not
			try {
				rset = stmt.executeQuery(query);
				if(rset.next()){					
					return true;
				}
				else 
					return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}			
		}
	}	
	
}
