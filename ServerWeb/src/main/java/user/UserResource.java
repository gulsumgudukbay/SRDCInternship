package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

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

	private static final long serialVersionUID = 1L;
	private static String loggedInUsername = "";
	static Logger Logger = LoggerFactory.getLogger(UserResource.class);

	UserDBL dbl = UserDBL.getInstance();
	
	
	 private Response.ResponseBuilder getResponseBuilder( Response.Status status ) {
	        CacheControl cc = new CacheControl();
	        cc.setNoCache( true );
	        cc.setMaxAge( -1 );
	        cc.setMustRevalidate( true );
	 
	        return Response.status( status ).cacheControl( cc );
	}
	
	@POST
	@Path("/users")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createUser(@Context HttpServletRequest request, @Context HttpServletResponse response) throws SQLException, ServletException, IOException{
		User user;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        ObjectMapper mapper = new ObjectMapper();
        user = mapper.readValue(json, User.class);		

		if(dbl.isUNValid (user.getUsername())){
			dbl.create(user);
			return getResponseBuilder( Response.Status.OK ).entity(json ).build();
		}
		else
			return getResponseBuilder( Response.Status.UNAUTHORIZED ).entity(json ).build();
	}
	
	
	@GET
    @Path( "/users/inbox" )
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response seeInbox() throws SQLException, ServletException, IOException, JSONException{

		int toId = dbl.getUserID(loggedInUsername);
		List<Message> jsonObj;
		if(dbl.seeInbox(toId)==null)
			jsonObj = null;
		else
			jsonObj = dbl.seeInbox(toId);
        if(jsonObj!=null && !jsonObj.isEmpty())
        	return getResponseBuilder( Response.Status.OK ).entity( jsonObj ).build();
        else
        	return getResponseBuilder( Response.Status.UNAUTHORIZED).entity("No messages exist!").build();
        
	}
	
	@GET
    @Path( "/users/outbox" )
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response seeOutbox() throws SQLException, ServletException, IOException, JSONException{

		int fromId = dbl.getUserID(loggedInUsername);
		List<Message> jsonObj;
		if(dbl.seeInbox(fromId)==null)
			jsonObj = null;
		else
			jsonObj = dbl.seeOutbox(fromId);

		if(jsonObj != null && !jsonObj.isEmpty()){
        	return getResponseBuilder( Response.Status.OK ).entity( jsonObj ).build();
        	}
        else
        	return getResponseBuilder( Response.Status.UNAUTHORIZED).entity("No messages exist!").build();
	}
		
	@POST
	@Path("/users/sendMessage")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response sendMessage(@Context HttpServletRequest request, @Context HttpServletResponse response) throws SQLException, ServletException, IOException{
		Message msg = new Message();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        
        StringTokenizer st = new StringTokenizer(json, ",");
        String to_email = "";
        String content = "";
        if(st.hasMoreTokens()){
        	to_email = st.nextToken();
        	if(st.hasMoreTokens())
        		content = st.nextToken();
        }
        
        to_email = to_email.substring(7, to_email.lastIndexOf('"'));
        content = content.substring(11, content.lastIndexOf('"'));
        
        int to_Id = dbl.getUserIDFromEmail(to_email);
        int from_Id = dbl.getUserIDFromEmail(loggedInUsername);
        	
        msg.setFromId(from_Id);
        msg.setToId(to_Id);
        msg.setMessage(content);
        		

		if(dbl.doesEmailExist(to_email) && dbl.insertToMessages(from_Id, to_Id, content)){
			return getResponseBuilder( Response.Status.OK ).entity("http://localhost:8080/ClientWeb/regularUser.html").build();
		}
		else
			return getResponseBuilder( Response.Status.UNAUTHORIZED ).entity("http://localhost:8080/ClientWeb/sendMail.html" ).build();
	}
	

	
	@POST
	@Path("/users/update")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateUser(@Context HttpServletRequest request, @Context HttpServletResponse response) throws SQLException, ServletException, IOException{
		User user;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String buf = "";
        if(br != null){
            buf = br.readLine();
        }
        String json = buf.substring(0, buf.lastIndexOf(','))+"}";
        String oldUN = buf.substring(buf.lastIndexOf(",")+16);
        oldUN = oldUN.substring(0,oldUN.lastIndexOf('"') );
        
        ObjectMapper mapper = new ObjectMapper();
        user = mapper.readValue(json, User.class);
		
		if(dbl.doesUNExist (oldUN)){
			dbl.update(user, oldUN);
			return getResponseBuilder( Response.Status.OK ).entity("User updated successfully!").build();
		}
		else
			return getResponseBuilder( Response.Status.UNAUTHORIZED ).entity("The username does not exist! Try again.").build();
	}
	
	@POST
	@Path("/users/delete")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response deleteUser(@Context HttpServletRequest request, @Context HttpServletResponse response) throws SQLException, ServletException, IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String buf = "";
        if(br != null){
            buf = br.readLine();
        }
        String unToDelete = buf.substring(13, buf.lastIndexOf('"'));

		if(dbl.doesUNExist (unToDelete)){
			dbl.deleteUser(unToDelete);
			if(unToDelete.equals(loggedInUsername))
				return getResponseBuilder( Response.Status.OK).entity("http://localhost:8080/ClientWeb/index.html").build();
			else
				return getResponseBuilder( Response.Status.OK ).entity("http://localhost:8080/ClientWeb/adminUser.html" ).build();
		}
		else
			return getResponseBuilder( Response.Status.UNAUTHORIZED ).entity("The username does not exist! Try again." ).build();
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

		if(dbl.doesUNExist (unToRead)){
			User data = dbl.readUser(unToRead);
			return getResponseBuilder( Response.Status.OK).entity(data).build();
		}
		else
			return getResponseBuilder( Response.Status.UNAUTHORIZED ).entity("The username does not exist! Try again." ).build();
	}
	
	@POST
	@Path("/users/login")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response loginUser(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException, SQLException{
		User user = new User();
		Logger.info("HELLO FROM SERVER");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        ObjectMapper mapper = new ObjectMapper();
        user = mapper.readValue(json, User.class);
        Logger.info(user.username+ " "+ user.password);

        user = dbl.getUser(dbl.getUserID(user.getUsername()));
        boolean b = dbl.isPassValid(user);
        Logger.info(b+" "+user.username+ " "+ user.password);
        if(b){
            loggedInUsername = user.username;
        	if((user.type).equals("Regular"))
        		return getResponseBuilder( Response.Status.OK ).entity("http://localhost:8080/ClientWeb/regularUser.html").build();
    		else
        		return getResponseBuilder( Response.Status.OK ).entity("http://localhost:8080/ClientWeb/adminUser.html").build();
        }
        else
			return getResponseBuilder( Response.Status.UNAUTHORIZED ).entity("ERROR" ).build();
        

        
	}
	
}
