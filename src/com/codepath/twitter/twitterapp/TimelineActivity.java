package com.codepath.twitter.twitterapp;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.codepath.twitter.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	
	public static final String TWEETS = "tweets";
	public static final int REQUEST_CODE=200;
	public ListView lvTweets;
	public TweetAdapter tweetAdapter;
	public ArrayList<Tweet> tweets;
	public int totalItems = 25;
	public final int visibleThreshold = 25;
	public long max_id=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		fetchTweets(totalItems);
		setListView();
		
		
	/**lvTweets.setOnScrollListener(new EndlessScrollListener(visibleThreshold) {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				System.out.println("Total item count in load more :: " + totalItemsCount); 
				fetchTweets(totalItemsCount);
				tweetAdapter.addAll(tweets);
				tweetAdapter.notifyDataSetInvalidated();
			}
		});**/
	}

	private void fetchTweets(int offset){
		
		MyTwitterApp.getRestClient().getHomeTimeline(String.valueOf(max_id), String.valueOf(offset), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTwittArray) {
				//tweets.clear();
				tweets = Tweet.fromJson(jsonTwittArray);
				tweetAdapter.addAll(tweets);
				tweetAdapter.notifyDataSetChanged();
				if(tweets != null && tweets.size() > 0){
					max_id = tweets.get(tweets.size() -1).getId() - 1;
				}
				//int i = tweets.size();
				///if(i != 0){
					//Tweet tweet = tweets.get(i-1);
					//max_id = String.valueOf(tweet.getId());
				//}
				
			}
		});

	}
	
	public void setListView(){
		tweets = new ArrayList<Tweet>();
		tweetAdapter = new TweetAdapter(getBaseContext(),tweets);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		
		Button btnLoadMore = new Button(this);
		btnLoadMore.setText("Load More");
		btnLoadMore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   fetchTweets(totalItems);	
			}
		});
		
		// Adding button to listview at footer
		lvTweets.addFooterView(btnLoadMore);
		
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
				if(tweets != null && tweets.size() > 0){
					tw.user = tweets.get(0).getUser();
				} 
				
				tw.created_at = new Date().toString();
				tw.body = newTweet;
				tw.getUser().name = "Himanshu Kapse";
				tw.getUser().screen_name = "hemankaps";
				//tweets.add(tw);
				//tweetAdapter.addAll(tweets);
				//tweets.addAll(tweets);
				tweetAdapter.add(tw);
				tweetAdapter.notifyDataSetChanged();
				
			} else if(resultCode == RESULT_CANCELED){
				//do nothing
				
			}
		}
	
	}
	

}
