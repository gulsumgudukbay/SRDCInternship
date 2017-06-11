package user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.codehaus.jettison.json.JSONException;


//SINGLETON PATTERN
public class UserDBL {

	private static UserDBL dbl = new UserDBL();
	private static Connection con;
	private static Statement stmt;
	
	private UserDBL() {
		
		try{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hw2?useSSL=false", "root", "gulsumg");
			stmt = con.createStatement();
		}catch(SQLException ex){
			ex.printStackTrace();
		}	
	}
	public static UserDBL getInstance(){
		return dbl;
	}
	
	public User getUser(int id) throws SQLException {
    	String sql = "SELECT * FROM Users WHERE user_id = ?";
        User user = new User();
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("ID IS "+id);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            user = processResultSet(rs);
        else
        	user = null;
        System.out.println(user.username+ " "+ user.password);
        return user;
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
	
	public int getUserIDFromEmail(String email) throws SQLException{
		String sql = "SELECT * FROM Users WHERE email =  '"+email+"'";
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next())
			return rs.getInt("user_id");
		else return -1;
	}
	
	
	public int getUserID(String username) throws SQLException{
		String sql = "SELECT * FROM Users WHERE username = '"+username+"'";
		System.out.println(username);
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next())
			return rs.getInt("user_id");
		else return -1;
	}
	protected User processResultSet(ResultSet rs) throws SQLException{
		User user = new User();
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setName(rs.getString("name"));
		user.setSurname(rs.getString("surname"));
		user.setEmail(rs.getString("email"));
		user.setType(rs.getString("type"));
		user.setUser_id(rs.getInt("user_id"));
		return user;
	}
	
	public User create(User user) throws SQLException {
		if(isUNValid(user.getUsername())){
	        String query = "INSERT INTO Users VALUE ('"+ user.getUsername() + "','"+ user.getPassword() + "','"+ user.getEmail() + "','"+ user.getName() + "','"+ user.getSurname() + "','"+ user.getType() + "',NULL);";
	        stmt.executeUpdate(query);
	        query = "SELECT * from Users where username = '"+ user.getUsername()+"'";
	        ResultSet rs = stmt.executeQuery(query);
	        rs.next();
	        int id = rs.getInt("user_id");
	        user.setUser_id(id);
	        return user;
		}
		else return null;
    }



	public boolean isPassValid(User user){
		if(user != null){
			String username = user.getUsername();
			String password = user.getPassword();
			if(password == null || password.equals(""))
				return false;
			else{
				ResultSet rset = null;
				String query = "select * from Users where username = '"+username+"' and password = '"+password+"'";
				try {
					rset = stmt.executeQuery(query);
					if(rset.next())
						return true;
					else 
						return false;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}	
			}
		}
		else return false;
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
	
	public List<Message> seeInbox(int toId) throws JSONException{
		Messages out = new Messages();
		String query = "select * from Messages where toId = " + toId;
		try{
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()){
				Message cur = new Message();
				cur.setFromId(rset.getInt("fromId"));
				cur.setMessage(rset.getString("message"));
				cur.setTimestamp(rset.getString("date_time"));
				out.add(cur);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		/*JSONObject ja = out.toJSONArr();
		return ja;*/
		return out.getArr();
	}
	
	public List<Message> seeOutbox(int fromId) throws JSONException{
		Messages out = new Messages();
		String query = "select * from Messages where fromId = " + fromId;
		try{
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()){
				Message cur = new Message();
				cur.setToId(rset.getInt("toId"));
				cur.setMessage(rset.getString("message"));
				cur.setTimestamp(rset.getString("date_time"));
				out.add(cur);	
			} 
		}catch (SQLException e){
			e.printStackTrace();
		}
		/*JSONObject ja = out.toJSONArr();
		return ja;*/
		return out.getArr();
	}
	
	
	public void update(User user, String un) throws SQLException{
		String query = "";
		String newUN = user.getUsername();
		String newPass = user.getPassword();
		String newEmail = user.getEmail();
		String newName = user.getName();
		String newSurname = user.getSurname();
		String newType = user.getType();
		
		int id = dbl.getUserID(un);
		if(newUN!=null && !newUN.equals("")){
			query = "UPDATE Users SET username ='"+newUN+ "' WHERE user_id = '" + id + "';";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(newPass!=null && !newPass.equals("")){
			query = "UPDATE Users SET password ='"+newPass+ "' WHERE user_id = '" + id + "';";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(newEmail!=null && !newEmail.equals("")){
			query = "UPDATE Users SET email ='"+newEmail+ "' WHERE user_id = '" + id + "';";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(newName!=null && !newName.equals("")){
			query = "UPDATE Users SET name ='"+newName+ "' WHERE user_id = '" + id + "';";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(newSurname!=null && !newSurname.equals("")){
			query = "UPDATE Users SET surname ='"+newSurname+ "' WHERE user_id = '" + id + "';";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(newType!=null && !newType.equals("")){
			query = "UPDATE Users SET type ='"+newType+ "' WHERE user_id = '" + id + "';";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

	public User readUser(String username){
		User user = new User();
		String query = "select * from Users where username = '" + username+"'";
		try{
			ResultSet rset = stmt.executeQuery(query);
			if(rset.next()){
				user.setUsername(username);
				user.setPassword(rset.getString("password"));
				user.setEmail(rset.getString("email"));
				user.setName(rset.getString("name"));
				user.setSurname(rset.getString("surname"));
				user.setType(rset.getString("type"));
				user.setUser_id(rset.getInt("user_id"));
			}	   
		} catch (SQLException e){
			e.printStackTrace();
		}
		return user;
		
	}
	
}
