package com.codepath.twitter.twitterapp.fragments;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;

import com.codepath.twitter.twitterapp.MyTwitterApp;
import com.codepath.twitter.twitterapp.json.models.Tweet;
import com.codepath.twitter.twitterapp.json.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimeLineFragment extends TimelineTweetFragment {
	
	User user;
	int offset = 25;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		user = (User) getActivity().getIntent().getSerializableExtra("screen_name");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fetchTweets(offset);
	}

	@Override
	public void fetchTweets(int offset) {
		MyTwitterApp.getRestClient().getUserTimeline(user.getScreenName(), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTwittArray) {
				tweets = Tweet.fromJson(jsonTwittArray);
				tweetAdapter.addAll(tweets);
				tweetAdapter.notifyDataSetChanged();
			}

		});
	}
	
	

}
