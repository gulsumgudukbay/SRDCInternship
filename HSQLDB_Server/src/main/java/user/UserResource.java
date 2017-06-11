/**
 * @author Gulsum Gudukbay
 *
 */

package user;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
@Path("/UserResource")
public class UserResource extends HttpServlet{

	//private static final long serialVersionUID = 1L;
	private static final long serialVersionUID = 6078310513185681918L;
	private static String loggedInUsername = "";
	private static String loggedInPassword = "";
	static Logger Logger = LoggerFactory.getLogger(UserResource.class);
	public ArrayList<User> users = new ArrayList<User>();
	public User user = new User();
	User testUser = new User((long) 1, "gulsumg", "sub", "gulsumg", "gulsum","gulsum", "gudukbay","", "","profile", "profilepics/cat.png",
			"no website", "gudukbay@gmail.com",true, "female","test", "test","0531-------", true,"Bilkent University Main Campus", "test","14 January 1996", "organizer","Mystic", "test");
	public UserResource(){
		users.add(testUser);
	}
	
	 private Response.ResponseBuilder getResponseBuilder( Response.Status status ) {
	        CacheControl cc = new CacheControl();
	        cc.setNoCache( true );
	        cc.setMaxAge( -1 );
	        cc.setMustRevalidate( true );
	 
	        return Response.status( status ).cacheControl( cc );
	}

	 private User auth(String username, String password){
		 for(int i = 0; i < users.size(); i++){
			 if(users.get(i).getPreferredUsername().equals(username) && users.get(i).getPassword().equals(password)){
				 loggedInUsername = users.get(i).getPreferredUsername();
				 loggedInPassword = users.get(i).getPassword();
				 return users.get(i);
			 }
		 }
		 return null;
	 }
	 
	 private User search(String username, String email){
		 
		 for(int i = 0; i < users.size(); i++)
			 if(users.get(i).getPreferredUsername().equals(username) || users.get(i).getEmail().equals(email))
				 return users.get(i);
		 return null;
	 }
	 
	@POST
	@Path("/users")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createUser(@Context HttpServletRequest request, @Context HttpServletResponse response) throws SQLException, ServletException, IOException{	
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        ObjectMapper mapper = new ObjectMapper();
        user = mapper.readValue(json, User.class);		
		if(user.getPreferredUsername() == null || user.getPreferredUsername().equals("")||user.getEmail() == null || user.getEmail().equals("") || user.getPassword() == null || user.getPassword().equals(""))
			return getResponseBuilder(Response.Status.UNAUTHORIZED).entity("Please fill out the required fields properly!").build();
		else if(search(user.getPreferredUsername(), user.getEmail()) == null){
			users.add(user);
			return getResponseBuilder( Response.Status.OK ).entity(json ).build();
		}
		else
			return getResponseBuilder(Response.Status.UNAUTHORIZED).entity("The username or email already exists!").build();
		
	}
	
	
	@GET
    @Path( "/users/info" )
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getUser() throws ServletException, IOException, JSONException{
		User jsonObj = new User();
		jsonObj = auth(loggedInUsername, loggedInPassword);
        if(jsonObj != null)
        	return getResponseBuilder( Response.Status.OK ).entity( jsonObj ).build();
        else
        	return getResponseBuilder( Response.Status.UNAUTHORIZED).entity("No info exists!").build();
        
	}
	

	
	@POST
	@Path("/users/read")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response readUser(@Context HttpServletRequest request, @Context HttpServletResponse response) throws SQLException, ServletException, IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String buf = "";
        if(br != null){
            buf = br.readLine();
        }
        String unToRead = buf.substring(13, buf.lastIndexOf('"'));
        User u = null;
        boolean b = false;
        
        for(int i = 0; i < users.size(); i++)
        	if(users.get(i).getName().equals(unToRead)){
        		b = true;
        		u = users.get(i);
        	}     
		if(b){
			return getResponseBuilder( Response.Status.OK).entity(u).build();
		}
		else
			return getResponseBuilder( Response.Status.UNAUTHORIZED ).entity("The username does not exist! Try again." ).build();
	}
	
	
	@POST
	@Path("/users/login")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response loginUser(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException, SQLException{
		
		User user;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        ObjectMapper mapper = new ObjectMapper();
        user = mapper.readValue(json, User.class);
        boolean b = false;
        if(auth(user.getPreferredUsername(), user.getPassword())!=null)
        	b = true;

        if(b){
            loggedInUsername = user.getPreferredUsername();
        	return getResponseBuilder( Response.Status.OK ).entity("http://localhost:8080/HSQLDB_Client/userInformation.html").build();
        }
        else
			return getResponseBuilder( Response.Status.UNAUTHORIZED ).entity("Wrong username and password combination!" ).build();
        
	}
	
}
