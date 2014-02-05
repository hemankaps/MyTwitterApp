package com.codepath.twitter.twitterapp;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.twitter.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	
	public static final String TWEETS = "tweets";
	public static final int REQUEST_CODE=200;
	public ListView lvTweets;
	public TweetAdapter tweetAdapter;
	public ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	public int totalItems = 0;
	public final int visibleThreshold = 25;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		fetchTweets(0);
		setListView();
		
		
	lvTweets.setOnScrollListener(new EndlessScrollListener(visibleThreshold) {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				
				fetchTweets(totalItemsCount);
				tweetAdapter.addAll(tweets);
				tweetAdapter.notifyDataSetInvalidated();
			}
		});
	}

	private void fetchTweets(int offset){
		MyTwitterApp.getRestClient().getHomeTimeline(new Integer(offset).toString(), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTwittArray) {
				tweets.clear();
				tweets = Tweet.fromJson(jsonTwittArray);
				tweetAdapter.addAll(tweets);
				tweetAdapter.notifyDataSetInvalidated();
				totalItems = tweets.size();
			}
		});

	}
	
	public void setListView(){
		tweetAdapter = new TweetAdapter(getBaseContext(),tweets);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetAdapter);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onComposeAction(MenuItem mi){
		Intent it = new Intent(this, TwitterComposeActivity.class);

		startActivityForResult(it, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE){
			if(resultCode == RESULT_OK){
				Tweet tw = new Tweet();
				String newTweet = data.getStringExtra(TWEETS);
				tw.user = tweets.get(0).getUser();
				
				tw.created_at = new Date().toString();
				tw.body = newTweet;
				tweets.add(0,tw);
				tweetAdapter.add(tw);
				tweetAdapter.notifyDataSetInvalidated();
				
			} else if(resultCode == RESULT_CANCELED){
				
			}
		}
	
	}
	

}
