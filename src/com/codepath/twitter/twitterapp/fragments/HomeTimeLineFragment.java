package com.codepath.twitter.twitterapp.fragments;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.twitter.twitterapp.MyTwitterApp;
import com.codepath.twitter.twitterapp.TimelineActivity;
import com.codepath.twitter.twitterapp.json.models.Tweet;
import com.codepath.twitter.twitterapp.json.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimeLineFragment extends TimelineTweetFragment {
	int offset = 25;
	User u;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		view.invalidate();
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		//super.setListView("Home");
		
	}
	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setHasOptionsMenu(true);
		this.fetchTweets(offset);
	 }
	 
	 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 return super.onCreateView(inflater, container, savedInstanceState);
		
		
	}
	 
	 private void fetchUserProfile() {
			MyTwitterApp.getRestClient().getUserInfo(new JsonHttpResponseHandler(){
					
					@Override
					public void onSuccess(JSONObject jsonObj) {
						u = User.fromJson(jsonObj);
					}
				});
		}
	 @Override
	public void onStart() {
		 if(u == null){
			 fetchUserProfile();
		 }
		 Intent i = getActivity().getIntent();
		 if (i != null){
			String newTweet = getActivity().getIntent().getStringExtra(TimelineActivity.TWEETS);
			 if(null != newTweet){
				 
				 Tweet tw = new Tweet();
				 if(null != u){
					 tw.user = u;
				 } else {
					 	tw.user = new User();
					    tw.getUser().name = "Himanshu Kapse";
						tw.getUser().screen_name = "hemankaps";
						tw.getUser().profile_image_url = "";
				 }
					tw.created_at = new Date().toString();
					tw.body = newTweet;
					
					
					getAdapter().add(tw);
			 }
		 }
		 super.onStart();
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
