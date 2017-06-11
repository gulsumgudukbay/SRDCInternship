package user;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONObject;

@XmlRootElement
public class Message {

	String message, timestamp;
	int toId, fromId;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(toId != 0)
			map.put("TO_ID", toId);
		if(fromId != 0)
			map.put("FROM_ID", fromId);
		map.put("MESSAGE", message);
		map.put("TIMESTAMP", timestamp);
		return map;
	}

	public static JSONObject toJSON(Map<String, Object> map) {
		JSONObject j = new JSONObject(map);
		return j;
	}

}
