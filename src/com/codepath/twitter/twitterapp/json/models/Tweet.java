package com.codepath.twitter.twitterapp.json.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

public class Tweet extends Model {
	

	@Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	public String remoteId;
	 @Column(name = "Created_At")
	public String created_at;
	 @Column(name = "Text")
	public String body;
	 @Column(name = "User")
	public User user;
	public long id;
	public Tweet(){
		super();
	}
	
	public User getUser(){
		return user;
	}
	
	public String getRemoteId() {
		return remoteId;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	
	public String getBody() {
		return body;
	}
	  public long getTId() {
	        return id;
	    }
	
	  public static Tweet fromJson(JSONObject jsonObject) {
	        Tweet tweet = new Tweet();
	        try {
	            //tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
	        	tweet.id = jsonObject.getLong("id");
	        	tweet.remoteId = jsonObject.getString("id");
	        	tweet.created_at = jsonObject.getString("created_at");
	        	tweet.body = jsonObject.getString("text");
	        	tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
	        	
	        } catch (JSONException e) {
	            e.printStackTrace();
	          //  return null;
	        }
	        return tweet;
	    }

	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
	
	}
	
	public static List<Tweet> getAll(User u) {
	    return new Select()
	        .from(Tweet.class)
	        .where("User=?", u.getId())
	        .execute();
	}
	

}
