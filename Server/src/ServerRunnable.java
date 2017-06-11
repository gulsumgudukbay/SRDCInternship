import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.StringTokenizer;

public class ServerRunnable implements Runnable{
	
	protected Socket clientSock = null;
	protected DBLayer dbl = DBLayer.getInstance();
	
	public ServerRunnable(Socket clientSock){
		this.clientSock = clientSock;
	}
	
	public void run(){
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(clientSock.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			PrintWriter ps = new PrintWriter(clientSock.getOutputStream(), true);
			
			//start of user menu
			int number = Integer.parseInt(br.readLine());
			while(number == 1|| number == 2){
				if(number == 1){ // Create user
					int validUN = 0;
					String username;
					while(validUN == 0){
						username = br.readLine();
						if(dbl.isUNValid(username)) 
							validUN = 1;
						ps.println(validUN);
						String cont = br.readLine();
						if(!cont.equals(" ")){ //valid
							StringTokenizer st = new StringTokenizer(cont, "===");
							String password = st.nextToken();
							String email = st.nextToken();
							String name = st.nextToken();
							String surname = st.nextToken();
							String type = st.nextToken();
							dbl.insertToUsers(username, password, email, name, surname, type);
						}
					}
				}
				else if(number == 2){ // Login
					String username = null;
					String password = null;
					int validUN = 0;
					while(validUN == 0){
						username = br.readLine();
						if(dbl.doesUNExist(username))
							validUN = 1;

						ps.println(validUN);
						if(validUN == 1){
							int validPass = 0;
							while(validPass == 0){
								password = br.readLine();
								if(dbl.isPassValid(username, password))
									validPass = 1;
								ps.println(validPass);
							}
							if(validPass == 1){
								br.readLine();
								String userType = dbl.getTypeOfUser(username);
								ps.println(userType); //send user type to the client
								int toId = -1;
								int fromId = -1;
								if(userType.equals("R")){ //regular user
									int noR = 1;
	
									while(noR == 1 || noR == 2 || noR == 3){
										noR = Integer.parseInt(br.readLine());
										if(noR == 1){ //send message
											int validEmailTo = 0;
											String emailTo = null;
										
											while(validEmailTo == 0){
												emailTo = br.readLine();
												if(dbl.doesEmailExist(emailTo))
													validEmailTo = 1;
												ps.println(validEmailTo);
											}
											String emailMsg = br.readLine();
											toId = dbl.getUserIDWithEmail(emailTo);
												
											fromId = dbl.getUserIDWithUN(username);
											
											//add the message to the database
											dbl.insertToMessages(fromId, toId, emailMsg);
										}
										else if(noR == 2){ // inbox
											int userId = dbl.getUserIDWithUN(username);
											String buf = dbl.seeInbox(userId);
											ps.println(buf);
										}
										else if(noR == 3){ // outbox
											int userId = dbl.getUserIDWithUN(username);
											String buf = dbl.seeOutbox(userId);
											ps.println(buf);
										}
									}
									
								}
								else{ //admin user
									int noA = 1;
									while(noA == 1 || noA == 2 || noA == 3 || noA == 4){
										noA = Integer.parseInt(br.readLine());

										if(noA == 1){ //create user
											String usernameCreate = "";
											int validUNCreate = 0;
											while(validUNCreate == 0){
												usernameCreate = br.readLine();
								
												if(dbl.isUNValid(usernameCreate)) 
													validUNCreate = 1;
												
												ps.println(validUNCreate);
												String cont = br.readLine();
												System.out.println(cont);
												if(!cont.equals(" ")){ //valid
													StringTokenizer st = new StringTokenizer(cont, "===");
													String password2 = st.nextToken();
													String email2 = st.nextToken();
													String name2 = st.nextToken();
													String surname2 = st.nextToken();
													String type2 = st.nextToken();
													dbl.insertToUsers(usernameCreate, password2, email2, name2, surname2, type2);
												}
											}
											
										}
										
										else if(noA == 2){ // read user
											String usernameRead = "";
											int validUNRead = 0;
											while(validUNRead == 0){
												usernameRead = br.readLine();
												if(dbl.doesUNExist(usernameRead))
													validUNRead = 1;
												
												ps.println(validUNRead);
												
											}
											ps.println(dbl.readUser(usernameRead));
										}
										else if(noA == 3){ // update user
											
											String usernameUpdate = "";
											int validUNUpdate = 0;
											
											while(validUNUpdate == 0){
												usernameUpdate = br.readLine();
											
												if(dbl.doesUNExist(usernameUpdate)) 
													validUNUpdate = 1;
												
												ps.println(validUNUpdate);
											}
											//update after checking whether if the specified username is valid
											int noU = 1;
											while(noU == 1 || noU == 2 || noU == 3 || noU == 4 || noU == 5){
												noU = Integer.parseInt(br.readLine());
												System.out.println(noU + " NO U");
												if(noU == 1){ //change username
													String newUN = br.readLine();
													dbl.updateUN(usernameUpdate, newUN);
												}
												else if(noU == 2){ //change password 
													String newPass = br.readLine();
													dbl.updatePass(usernameUpdate, newPass);
												}
												else if(noU == 3){ //change name
													String newName = br.readLine();
													dbl.updateName(usernameUpdate, newName);
												}
												else if(noU == 4){ //change surname
													String newSurname = br.readLine();
													dbl.updateSurname(usernameUpdate, newSurname);
												}
												else if(noU == 5){ //change type
													String newType = br.readLine();
													dbl.updateType(usernameUpdate, newType);
												}	
											}	
										}
								
										else if(noA == 4){ // delete user
											String usernameDelete = "";
											int validUNDelete = 0;
											while(validUNDelete == 0){
												usernameDelete = br.readLine();
												if(dbl.doesUNExist(usernameDelete)) 
													validUNDelete = 1;
												
												ps.println(validUNDelete);
											}
											dbl.deleteUser(usernameDelete);
										}	
									}	
									
								}
							}	
						}
					}
				}
				
				number = Integer.parseInt(br.readLine());
				
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
