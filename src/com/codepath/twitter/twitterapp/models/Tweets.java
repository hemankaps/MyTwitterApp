package com.codepath.twitter.twitterapp.models;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

public class Tweets extends Model {

	 // This is how you avoid duplicates
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int remoteId;
    @Column(name = "Body")
    public String body;
    @Column(name="ScreenName")
    public String screen_name;
    @Column(name="profile_image_url")
    public String profile_image_url;
    @Column(name="created_at")
    public String created_at;
    

    // Make sure to have a default constructor for every ActiveAndroid model
    public Tweets(){
       super();
    }
    
    public static List<Tweets> getAll(Tweets tweet) {
        // This is how you execute a query
        return new Select()
          .from(Tweets.class)
          .where("Tweets = ?", tweet.getId())
          .orderBy("Name ASC")
          .execute();
    }
}
