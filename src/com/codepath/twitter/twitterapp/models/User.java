package com.codepath.twitter.twitterapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends BaseModel{
	
	public String name;
	public String screen_name;
	public String profile_image_url;

	public String getName() {
		if(jsonObject != null){
			name = getString("name");
		}
        return name;
    }

    public long getId() {
        return getLong("id");
    }

    public String getScreenName() {
    	if(jsonObject != null){
    		screen_name = getString("screen_name");
    	}
        return screen_name;
    }
    
    public String getProfileImageUrl(){
    	if(jsonObject != null){
    		profile_image_url = getString("profile_image_url");
    	}
    	return profile_image_url;
    }

    public String getProfileBackgroundImageUrl() {
        return getString("profile_background_image_url");
    }

    public int getNumTweets() {
        return getInt("statuses_count");
    }

    public int getFollowersCount() {
        return getInt("followers_count");
    }

    public int getFriendsCount() {
        return getInt("friends_count");
    }

    public static User fromJson(JSONObject json) {
        User u = new User();
        try {
         u.jsonObject =  json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
}
