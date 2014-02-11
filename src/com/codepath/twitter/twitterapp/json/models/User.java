package com.codepath.twitter.twitterapp.json.models;


import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

public class User extends Model implements Serializable {

	@Column(name = "user_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	public String userId;
	 @Column(name = "Name")
	public String name;
	 @Column(name = "Screen_Name")
	public String screen_name;
	 @Column(name = "Profile_Image_Url")
	public String profile_image_url;
	public String followers_count;
	public String following;
	public String tagLine;
	
	
	public User(){
		super();
	}
	public String getName() {
		return name;
	}
	public String getScreenName() {
		return screen_name;
	}
	public String getProfileImageUrl() {
		return profile_image_url;
	}
	public String getUserId() {
		return userId;
	}
	
	public String getFollowersCount() {
		return followers_count;
	}
	public String getFollowing() {
		return following;
	}
	
	public String getTagLine(){
		return tagLine;
	}
	
	  public static User fromJson(JSONObject usr) {
	        User u = new User();
	        try {
	        	//JSONObject usr = jsonObject.getJSONObject("user");
	        	u.userId = usr.getString("id");
	        	u.name = usr.getString("name");
	        	u.screen_name = usr.getString("screen_name");
	        	u.profile_image_url = usr.getString("profile_image_url");
	        	u.followers_count = usr.getString("followers_count");
	        	u.following = usr.getString("friends_count");
	        	u.tagLine = usr.getString("description");
	        	
	        } catch (JSONException e) {
	            e.printStackTrace();
	          //  return null;
	        }
	        return u;
	    }

	public static List<User> getAll(){
		
		return new Select().from(User.class).execute();
		
	}
}
