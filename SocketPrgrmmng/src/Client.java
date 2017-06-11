import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client {

	public static void main(String[] args) throws Exception {
		Client clnt = new Client();
		clnt.run();
	}
	public void run() throws Exception{
		Socket sock = new Socket("localhost", 1024);
		PrintStream ps = new PrintStream(sock.getOutputStream());
		
		InputStreamReader ir = new InputStreamReader(sock.getInputStream());
		BufferedReader br = new BufferedReader(ir); //gets from server
		
		//menu
		Scanner scan = new Scanner(System.in);
		int menuNo;
		String username, password, name, surname, type, email;
		
		do{
			System.out.println("\nPlease select the desired menu item by entering "
					+ "the number next to it. Press 3 for exit. \n"
					+ "(1) Create User \n"
					+ "(2) Login to an Existing User Account \n"
					+ "(3) Exit"); 
			
			menuNo = scan.nextInt();
			scan.nextLine();
			if(menuNo == 1 || menuNo == 2 || menuNo == 3){
				ps.println(menuNo); //sends the selected menu number to the server
				
				if(menuNo == 1){
					System.out.println("Enter the desired username:"); 
					int validUN = 0;
					String cont = null;
					while(validUN == 0){ // ask for the username till it is valid
						username = scan.nextLine();
						ps.println(username);
						
						validUN = br.read() - 48;

						if(validUN == 1){ // ask for the other attributes after the username is valid
							System.out.println("The username is valid. Please enter the desired password:");
							password = scan.nextLine();
							
							System.out.println("Please enter your email:");
							email = scan.nextLine();
							
							System.out.println("Please enter your name:");
							name = scan.nextLine();
							
							System.out.println("Please enter your surname:");
							surname = scan.nextLine();
							
							System.out.println("Please enter the type of your account:");
							type = scan.nextLine();
							
							cont = "'" + password + "','"+ email + "','" + name + "','" + surname + "','" + type + "', NULL);";
							ps.println(cont);
						}
						else{
							System.out.println("The username is not valid, please try again!");
							ps.println(" ");
						}
						
					}
				}
				else if(menuNo == 2){
					
					System.out.println("Enter your username:"); 
					int validUN = 0;
					
					//username = scan.nextLine();
					//ps.println(username);
					
					while(validUN == 0){ // ask for the username till it is valid
						username = scan.nextLine();
						ps.println(username);
						//br.readLine();
						validUN = br.read()-48;
						if(validUN == 1){ // ask for the other attributes after the username is valid
							System.out.println("The username is valid. Please enter your password:");
							int validPass = 0;

							
							while(validPass == 0){ //ask for the password till it is valid
								
								password = scan.nextLine();
								ps.println(password);
								
								validPass = br.read() - 48;
								if(validPass == 1){//pass correct get the user type from server and display the correct user menu
									ps.println(password);
									String userType = br.readLine();
									if(userType.equals("R")){
										int noR = -1;
										//REGULAR MENU
										do{
											System.out.println("\nPlease select the desired menu item by entering "
													+ "the number next to it. Press 4 for going back to the main menu. \n"
													+ "(1) Send Message \n"
													+ "(2) Inbox \n"
													+ "(3) Outbox \n"
													+ "(4) Exit"); 
											noR = scan.nextInt();
											scan.nextLine();
											
											if(noR == 1 || noR == 2 || noR == 3 || noR == 4){
												ps.print(noR);
												if(noR == 1){ // send message
													System.out.println("Please enter the person's email you want to send mail to:");
													String emailTo = null;
													int validEmailTo = 0;
													while(validEmailTo == 0){
														emailTo = scan.nextLine();
														ps.println(emailTo);
														validEmailTo = br.read() - 48;
														if(validEmailTo == 1){
															String emailMsg;
															System.out.println("Please enter your message.");
															emailMsg = scan.nextLine();
															ps.println(emailMsg);
														}
														else
															System.out.println("The email is not valid, please try again.");
														
													}
												}
												else if(noR == 2){ // inbox
													String buf = "";
													System.out.println("The inbox of " + username);
													buf = br.readLine();
													if(buf == null || buf.equals(""))
														System.out.println("No messages received!");
													else
														System.out.println(buf);
												
												}
												else if(noR == 3){ // outbox
													String buf = "";
													System.out.println("The outbox of " + username);
													buf = br.readLine();
													if(buf == null || buf.equals(""))
														System.out.println("No messages sent!");
													else
														System.out.println(buf);
												}
											}
											else
												System.out.println("Invalid Number, please try again!");
											
										}while(noR != 4);
										System.out.println("You have gone back to the main menu!");
										
									}
									else{ // Admin menu
										
										int noA = -1;
										//ADMIN MENU
										do{
											System.out.println("\nPlease select the desired menu item by entering "
													+ "the number next to it. Press 5 for going back to the main menu. \n"
													+ "(1) Create User \n"
													+ "(2) Read User \n"
													+ "(3) Update User \n"
													+ "(4) Delete User \n"
													+ "(5) Exit"); 
											noA = scan.nextInt();
											scan.nextLine();
											
											
											if(noA == 1 || noA == 2 || noA == 3 || noA == 4 || noA == 5 ){
												ps.print(noA);
												if(noA == 1){ // create
													String usernameCreate, passwordCreate, emailCreate, nameCreate, surnameCreate, typeCreate;
													System.out.println("Enter the desired username:"); 
													int validUNCreate = 0;
													String cont = null;
													while(validUNCreate == 0){ // ask for the username till it is valid
														usernameCreate = scan.nextLine();
														ps.println(usernameCreate);
														
														validUNCreate = br.read()-48;
														if(validUNCreate == 1){ // ask for the other attributes after the username is valid
															System.out.println("The username is valid. Please enter the desired password:");
															passwordCreate = scan.nextLine();
															
															System.out.println("Please enter the email:");
															emailCreate = scan.nextLine();
															
															System.out.println("Please enter the name:");
															nameCreate = scan.nextLine();
															
															System.out.println("Please enter the surname:");
															surnameCreate = scan.nextLine();
															
															System.out.println("Please enter the type of the account:");
															typeCreate = scan.nextLine();
															
															cont = "'" + passwordCreate + "','"+ emailCreate + "','" + nameCreate + "','" + surnameCreate + "','" + typeCreate + "', NULL);";
															ps.println(cont);
														}
														else{
															System.out.println("The username is not valid, please try again!");
															ps.println(" ");
														}
														
													}
												}
												else if(noA == 2){ // read
													String usernameRead;
													System.out.println("Enter the username of the user to be read:"); 
													int validUNRead = 0;
													while(validUNRead == 0){ // ask for the username till it is valid
														usernameRead = scan.nextLine();
														ps.println(usernameRead);
														
														validUNRead = br.read()-48;
														if(validUNRead == 1){ 
															System.out.println("The username is valid. The information for "+usernameRead+":");
															System.out.println("Username\t| Password\t| Email\t| Name\t| Surname\t| Type\t| User ID");
															System.out.println(br.readLine());
														}
														else{
															System.out.println("The username is not valid, please try again!");
														}														
													}
												}
												else if(noA == 3){ // update
													String usernameUpdate;
													System.out.println("Enter the username of the user to be updated:"); 
													int validUNUpdate = 0;
													while(validUNUpdate == 0){ // ask for the username till it is valid
														usernameUpdate = scan.nextLine();
														ps.println(usernameUpdate);
														
														validUNUpdate = br.read() - 48;
														if(validUNUpdate == 1){ 
															System.out.println("The username is valid. " + usernameUpdate + " is to be updated.");
															int noU = -1;
															do{
																System.out.println("\nPlease select the desired menu item by entering "
																		+ "the number next to it. Press 6 for going back to the admin menu. \n"
																		+ "(1) Change Username \n"
																		+ "(2) Change Password \n"
																		+ "(3) Change Name \n"
																		+ "(4) Change Surname \n"
																		+ "(5) Change Type \n"
																		+ "(6) Exit"); 
																
																noU = scan.nextInt();
																scan.nextLine();
													
																if(noU == 1 || noU == 2 || noU == 3 || noU == 4 || noU == 5 || noU == 6){
																	ps.print(noU);
																	if(noU == 1){ //change username 
																		System.out.println("Please enter the new username of " + usernameUpdate);
																		String newUN = scan.nextLine();
																		ps.println(newUN);
																		
																	}
																	else if(noU == 2){ //change password
																		System.out.println("Please enter the new password of " + usernameUpdate);
																		String newPass = scan.nextLine();
																		ps.println(newPass);
																		System.out.println("new pass "+ newPass);
																	}
																	else if(noU == 3){ //change name
																		System.out.println("Please enter the new name of " + usernameUpdate);
																		String newName = scan.nextLine();
																		ps.println(newName);
																	}	
																	else if(noU == 4){ //change surname
																		System.out.println("Please enter the new surname of " + usernameUpdate);
																		String newSurname = scan.nextLine();
																		ps.println(newSurname);
																	}
																	else if(noU == 5){ //change type
																		System.out.println("Please enter the new type of " + usernameUpdate);
																		String newType = scan.nextLine();
																		ps.println(newType);
																	}		
																}
																else
																	System.out.println("Invalid Number, please try again!");
																
															}while(noU != 6);
															System.out.println("You have gone back to the admin menu!");
														}
														else{
															System.out.println("The username cannot be found, please try again!");
														}	
													}												
												}
												else if(noA == 4){ // delete
													String usernameDelete;
													System.out.println("Enter the username of the user to be deleted:"); 
													int validUNDelete = 0;
													while(validUNDelete == 0){ // ask for the username till it is valid
														usernameDelete = scan.nextLine();
														ps.println(usernameDelete);
														
														validUNDelete = br.read() - 48;
														if(validUNDelete == 1){ 
															System.out.println("The username is valid. " + usernameDelete + " is deleted.");
																
														}
														else{
															System.out.println("The username cannot be found, please try again!");
														}
														
													}
												}
											}
											else
												System.out.println("Invalid Number, please try again!");
											
										}while(noA != 5);
										System.out.println("You have gone back to the main menu!");
									}
								}
								else
									System.out.println("The password is not valid, please try again!");
							}			
						}
						else // USERNAME NOT VALID
							System.out.println("The username is not valid, please try again!");
						
					}	
					br.readLine();
				}
			}
			else
				System.out.println("You entered an invalid number, please try again.");
			
		}
		while(menuNo != 3);
		System.out.println("You exit the program, bye!");
		
		scan.close();
		sock.close();
		
	}
}
