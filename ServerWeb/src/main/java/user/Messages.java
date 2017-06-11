package user;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

public class Messages {

	List<Message> arr = new ArrayList<Message>();

	
	public void add(Message msg){
		arr.add(msg);
	}
	
	public List<Message> getArr() throws JSONException{
		if(arr.isEmpty())
			return null;
		else
			return arr;
	}

	
}
