package com.codepath.twitter.twitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.activeandroid.ActiveAndroid;
import com.codepath.twitter.twitterapp.MyTwitterApp;
import com.codepath.twitter.twitterapp.json.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;

public class HomeTimeLineFragment extends TimelineTweetFragment {
	int offset = 25;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		//super.setListView("Home");
		
	}
	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
		fetchTweets(offset);
	 }
	 
	 public void fetchTweets(int offset){
		 MyTwitterApp.getRestClient().getHomeTimeline(String.valueOf(max_id), String.valueOf(offset), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONArray jsonTwittArray) {
					tweets = Tweet.fromJson(jsonTwittArray);
					getAdapter().addAll(tweets);
					tweetAdapter.notifyDataSetChanged();
					if(tweets != null && tweets.size() > 0){
						max_id = tweets.get(tweets.size() -1).getTId() - 1;
					}
				}

			});
	 }

	
}
