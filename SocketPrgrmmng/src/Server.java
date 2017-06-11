import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {

	public static void main(String[] args) throws Exception {
		Server srvr = new Server();
		srvr.run();
		
	}
	
	public void run() throws Exception{
		System.out.println("111");
		ServerSocket srvSock = new ServerSocket(1024);
		Socket sock = srvSock.accept();
		System.out.println("222");
		
		//gets input from client
		InputStreamReader isr = new InputStreamReader(sock.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		PrintStream ps = new PrintStream(sock.getOutputStream());
		int number = br.read()-48;
		//if selected number is valid, sends message to the client that the no is valid
		while(true){	
			try(
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1?useSSL=false", "root", "gulsumg");
					Statement stmt = con.createStatement();
				){
					String query;
					int validUN = 0;
					
					if(number == 1){ // Create user
						ResultSet rset;
						String username;
						br.readLine();
						while(validUN == 0){
							username = br.readLine();
							query = "select * from user where username = '" + username + "'";//search in the database table whether if the specified username exists or not
							rset = stmt.executeQuery(query);
	
							if(!rset.next() && username != null) 
								validUN = 1;
							
							ps.print(validUN);
							String cont = br.readLine();
							if(!cont.equals(" ")){ //valid
								query = "INSERT INTO user VALUE ('"+ username + "',"+ cont ;  
								stmt.executeUpdate(query); //USER CREATION IN DATABASE
							}
						}
					}
					else if(number == 2){ // Login
						ResultSet rset;
						String username = null;
						String password = null;
						br.readLine();
						
						while(validUN == 0){
							username = br.readLine();
							query = "select * from user where username = '" + username + "'";//search in the database table whether if the specified username exists or not
							rset = stmt.executeQuery(query);
	
							if(rset.next() && username != null && !username.equals("")) //user with specified username exists
								validUN = 1;
							////////////////////
							ps.print(validUN);
							if(validUN == 1){

								int validPass = 0;
								while(validPass == 0){
									password = br.readLine();
									query = "select * from user where username = '"+ username + "' and password = '"+ password + "'" ;  
									rset = stmt.executeQuery(query); // user found
									if(rset.next() && password != null) //pass and username matches
										validPass = 1;
									ps.print(validPass);
								}
							
								
								String userType = "";
								//logged in, sending the username to the client
								query = "select * from user where username = '"+ username + "' and password = '"+ password + "'" ;  
								rset = stmt.executeQuery(query); // user found
								while(rset.next()) userType = rset.getString("type");
								ps.println(userType); //send user type to the client
								int toId = -1;
								int fromId = -1;
								if(userType.equals("R")){ //regular user
									int noR = 1;
									br.readLine();

									while(noR == 1 || noR == 2 || noR == 3){
										noR = br.read()-48;

										if(noR == 1){ //send message
											int validEmailTo = 0;
											String emailTo = null;
											String emailFrom = null;
											
											query = "select * from user where username = '"+ username + "' and password = '"+ password + "'";
											rset = stmt.executeQuery(query);
											
											while(rset.next()) emailFrom = rset.getString("email");	
											while(validEmailTo == 0){
												emailTo = br.readLine();
												System.out.println(emailTo + " email to");
												query = "select * from user where email = '" + emailTo + "'";
												rset = stmt.executeQuery(query);
												
												if(rset.next() && !emailTo.equals(emailFrom) && emailTo != null && !emailTo.equals(""))
													validEmailTo = 1;
												ps.print(validEmailTo);
											}
											String emailMsg = br.readLine();
											query = "select * from user where email = '" + emailTo + "'";
											rset = stmt.executeQuery(query); //get the id with the specified email
											if(rset.next()) toId = rset.getInt("user_id");
												
											query = "select * from user where username = '" + username + "'";
											rset = stmt.executeQuery(query); //get the id of the user
											if(rset.next()) fromId = rset.getInt("user_id");
		
											//add the message to the database
											query = "INSERT INTO Messages VALUE (" + fromId + ", "+ toId + ", '"+ emailMsg + "', NOW());";
											stmt.executeUpdate(query);
										}
										else if(noR == 2){ // inbox
											query = "select * from user where username = '" + username + "'";
											rset = stmt.executeQuery(query); //get the id of the user
											if(rset.next()) toId = rset.getInt("user_id");
											
											String buf = "";
											query = "select * from Messages where to_id = " + toId;
											rset = stmt.executeQuery(query);
											while(rset.next()) buf = buf + rset.getString("from_id")+ "\t" + rset.getString("msg") + "\t"+ rset.getString("date_time") + "\t\t";
											ps.println(buf);
										}
										else{ // outbox
											query = "select * from user where username = '" + username + "'";
											rset = stmt.executeQuery(query); //get the id of the user
											if(rset.next()) fromId = rset.getInt("user_id");
											
											String buf = "";
											query = "select * from Messages where from_id = " + fromId;
											rset = stmt.executeQuery(query);
											while(rset.next()) buf = buf + rset.getString("to_id")+ "\t" + rset.getString("msg") + "\t"+ rset.getString("date_time") + "\t\t";
											ps.println(buf);
										}
										
										
									}
									
								}
								else{ //admin user
									int noA = 1;
									br.readLine();
									while(noA == 1 || noA == 2 || noA == 3 || noA == 4){
										noA = br.read()-48;
										if(noA == 1){ //create user
											String usernameCreate;
											//br.readLine();
											int validUNCreate = 0;
											while(validUNCreate == 0){
												usernameCreate = br.readLine();
												query = "select * from user where username = '" + usernameCreate + "'";//search in the database table whether if the specified username exists or not
												rset = stmt.executeQuery(query);
						
												if(!rset.next() && usernameCreate != null) 
													validUNCreate = 1;
												
												ps.print(validUNCreate);
												String cont = br.readLine();
												if(!cont.equals(" ")){ //valid
													query = "INSERT INTO user VALUE ('"+ usernameCreate + "',"+ cont ;  
													stmt.executeUpdate(query); //USER CREATION IN DATABASE
												}
											}
										}
										else if(noA == 2){ // read user
											String usernameRead;
											int validUNRead = 0;
											while(validUNRead == 0){
												usernameRead = br.readLine();
												query = "select * from user where username = '" + usernameRead + "'";//search in the database table whether if the specified username exists or not
												rset = stmt.executeQuery(query);
												if(rset.next() && usernameRead != null) 
													validUNRead = 1;
												
												ps.print(validUNRead);
												query = "select * from user where username = '" + usernameRead + "'";
												rset = stmt.executeQuery(query);
												if(rset.next()) 
													ps.println(usernameRead + "\t\t| "+ rset.getString("password")+"\t\t| "+ rset.getString("email")  + "\t| "+ rset.getString("name")+"\t| "+ rset.getString("surname")  + "\t\t| "+ rset.getString("type") + "\t| "+ rset.getInt("user_id"));
												
											}
										}
										else if(noA == 3){ // update user
											
											String usernameUpdate;
											int validUNUpdate = 0;
											
											while(validUNUpdate == 0){
												usernameUpdate = br.readLine();
												query = "select * from user where username = '" + usernameUpdate + "'";//search in the database table whether if the specified username exists or not
												rset = stmt.executeQuery(query);
						
												if(rset.next() && usernameUpdate != null) 
													validUNUpdate = 1;
												
												ps.print(validUNUpdate);
												
												//update after checking whether if the specified username is valid
												int noU = 1;
												br.readLine();
												while(noU == 1 || noU == 2 || noU == 3 || noU == 4 || noU == 5){
													noU = br.read() - 48;
													System.out.println(noU + " NO U");
													if(noU == 1){ //change username
														String newUN = br.readLine();
														query = "UPDATE user SET username = + '"+newUN+ "' WHERE username = '" + usernameUpdate + "';";
														stmt.executeUpdate(query);
													}
													else if(noU == 2){ //change password 
														String newPass = br.readLine();
														query = "UPDATE user SET password = + '"+newPass+ "' WHERE username = '" + usernameUpdate + "';";
														stmt.executeUpdate(query);
													}
													else if(noU == 3){ //change name
														String newName = br.readLine();
														query = "UPDATE user SET name = + '"+newName+ "' WHERE username = '" + usernameUpdate + "';";
														stmt.executeUpdate(query);
													}
													else if(noU == 4){ //change surname
														String newSurname = br.readLine();
														query = "UPDATE user SET surname = + '"+newSurname+ "' WHERE username = '" + usernameUpdate + "';";
														stmt.executeUpdate(query);
													}
													else{ //change type
														String newType = br.readLine();
														query = "UPDATE user SET type = + '"+newType+ "' WHERE username = '" + usernameUpdate + "';";
														stmt.executeUpdate(query);
													}	
												}		
											}	
										}
								
										else{ // delete user
											String usernameDelete;
											int validUNRead = 0;
											while(validUNRead == 0){
												usernameDelete = br.readLine();
												query = "select * from user where username = '" + usernameDelete + "'";//search in the database table whether if the specified username exists or not
												rset = stmt.executeQuery(query);
												if(rset.next() && usernameDelete != null) 
													validUNRead = 1;
												
												ps.print(validUNRead);
												query = "delete from user where username = '" + usernameDelete + "'";
												stmt.executeUpdate(query);
											}
										}
										
									}	
								}
							}	
							
						}
					}
				}
				catch(SQLException ex){
						ex.printStackTrace();
				}
			
			number = br.read()-48;
		}
		
	
		
	}

}
