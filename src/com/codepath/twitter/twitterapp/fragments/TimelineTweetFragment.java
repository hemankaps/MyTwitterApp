package com.codepath.twitter.twitterapp.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.activeandroid.ActiveAndroid;
import com.codepath.twitter.twitterapp.MyTwitterApp;
import com.codepath.twitter.twitterapp.R;
import com.codepath.twitter.twitterapp.TweetAdapter;
import com.codepath.twitter.twitterapp.json.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TimelineTweetFragment extends SherlockFragment {
	public TweetAdapter tweetAdapter;
	public long max_id=0;
	public ArrayList<Tweet> tweets;
	ListView lvTweets;
	public int totalItems = 25;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setListView();
	}
	
	private List<Tweet> readFromDatabase() {
		// TODO Auto-generated method stub
		List<com.codepath.twitter.twitterapp.json.models.User> us = com.codepath.twitter.twitterapp.json.models.User.getAll();
		if(us != null && us.size() > 0){
			for(com.codepath.twitter.twitterapp.json.models.User u: us){
			 System.out.println("User Objects ::::: " + u.getId());
			}
		}
		return com.codepath.twitter.twitterapp.json.models.Tweet.getAll(us.get(0));
	}

	
	
  public abstract void fetchTweets(int offset);


 public void setListView(){
	tweets = new ArrayList<Tweet>();
	tweetAdapter = new TweetAdapter(getActivity(),tweets);
	lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
	
	Button btnLoadMore = new Button(getActivity());
	btnLoadMore.setText("Load More");
	btnLoadMore.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			fetchTweets(totalItems);
		}
	});
	
	// Adding button to listview at footer
	lvTweets.addFooterView(btnLoadMore);
	
	lvTweets.setAdapter(tweetAdapter);
 }
	public TweetAdapter getAdapter(){
		return tweetAdapter;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_timeline, container, false);
	}
	

}
