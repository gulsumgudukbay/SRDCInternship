package user;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONObject;

@XmlRootElement
public class User {

	String username, password, email, name, surname, type;
	int user_id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("USERNAME", username);
		map.put("PASSWORD", password);
		map.put("EMAIL", email);
		map.put("NAME", name);
		map.put("SURNAME", surname);
		map.put("TYPE", type);
		map.put("ID", user_id);
		return map;
	}

	public static JSONObject toJSON(Map<String, Object> map) {
		JSONObject j = new JSONObject(map);
		return j;
	}

}
