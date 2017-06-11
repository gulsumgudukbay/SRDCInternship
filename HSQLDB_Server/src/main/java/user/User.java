/**
 * @author Gulsum Gudukbay
 *
 */
package user;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONObject;

import com.google.gson.JsonObject;

@XmlRootElement
public class User {

	private Long id;
	private String password; //ADDED
	private String sub;
	private String preferredUsername;
	private String name;
	private String givenName;
	private String familyName;
	private String middleName;
	private String nickname;
	private String profile;
	private String picture;
	private String website;
	private String email;
	private Boolean emailVerified;
	private String gender;
	private String zoneinfo;
	private String locale;
	private String phoneNumber;
	private Boolean phoneNumberVerified;
	private String address;
	private String updatedTime;
	private String birthdate;
	private String role;
	private String team;
	private String teamRole;
	private transient JsonObject src; // source JSON if this is loaded remotely
	
	//Constructor
	public User(Long id, String password, String sub, String preferredUsername, String name, String givenName,
			String familyName, String middleName, String nickname, String profile, String picture, String website,
			String email, Boolean emailVerified, String gender, String zoneinfo, String locale, String phoneNumber,
			Boolean phoneNumberVerified, String address, String updatedTime, String birthdate, String role, String team,
			String teamRole) {
		this.id = id;
		this.password = password;
		this.sub = sub;
		this.preferredUsername = preferredUsername;
		this.name = name;
		this.givenName = givenName;
		this.familyName = familyName;
		this.middleName = middleName;
		this.nickname = nickname;
		this.profile = profile;
		this.picture = picture;
		this.website = website;
		this.email = email;
		this.emailVerified = emailVerified;
		this.gender = gender;
		this.zoneinfo = zoneinfo;
		this.locale = locale;
		this.phoneNumber = phoneNumber;
		this.phoneNumberVerified = phoneNumberVerified;
		this.address = address;
		this.updatedTime = updatedTime;
		this.birthdate = birthdate;
		this.role = role;
		this.team = team;
		this.teamRole = teamRole;
	}
	
	public User(){
		id=(long) 0;
		password="";
		sub="";
		preferredUsername="";
		name="";
		givenName="";
		familyName="";
		middleName="";
		nickname="";
		profile="";
		picture="";
		website="";
		email="";
		emailVerified=true;
		gender="";
		zoneinfo="";
		locale="";
		phoneNumber="";
		phoneNumberVerified=true;
		address="";
		updatedTime="";
		birthdate="";
		role="";
		team="";
		teamRole="";
	}


	//ACCESSORS AND MUTATORS
	
	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getPreferredUsername() {
		return preferredUsername;
	}

	public void setPreferredUsername(String preferredUsername) {
		this.preferredUsername = preferredUsername;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getZoneinfo() {
		return zoneinfo;
	}

	public void setZoneinfo(String zoneinfo) {
		this.zoneinfo = zoneinfo;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean getPhoneNumberVerified() {
		return phoneNumberVerified;
	}

	public void setPhoneNumberVerified(Boolean phoneNumberVerified) {
		this.phoneNumberVerified = phoneNumberVerified;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getTeamRole() {
		return teamRole;
	}

	public void setTeamRole(String teamRole) {
		this.teamRole = teamRole;
	}

	public JsonObject getSrc() {
		return src;
	}

	public void setSrc(JsonObject src) {
		this.src = src;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", sub=" + sub + ", preferredUsername=" + preferredUsername
				+ ", name=" + name + ", givenName=" + givenName + ", familyName=" + familyName + ", middleName="
				+ middleName + ", nickname=" + nickname + ", profile=" + profile + ", picture=" + picture + ", website="
				+ website + ", email=" + email + ", emailVerified=" + emailVerified + ", gender=" + gender
				+ ", zoneinfo=" + zoneinfo + ", locale=" + locale + ", phoneNumber=" + phoneNumber
				+ ", phoneNumberVerified=" + phoneNumberVerified + ", address=" + address + ", updatedTime="
				+ updatedTime + ", birthdate=" + birthdate + ", role=" + role + ", team=" + team + ", teamRole="
				+ teamRole + "]";
	}

	////OLD ////
	public static JSONObject toJSON(Map<String, Object> map) {
		JSONObject j = new JSONObject(map);
		return j;
	}

}
