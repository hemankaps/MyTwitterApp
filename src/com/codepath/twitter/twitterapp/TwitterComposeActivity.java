package com.codepath.twitter.twitterapp;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.codepath.twitter.twitterapp.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class TwitterComposeActivity extends Activity {
	public TextView etTweet;
	public String newTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_compose);
		etTweet = (TextView) findViewById(R.id.etTweet);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.twitter_compose, menu);
		return true;
	}
	
	public void cancelAction(View v){
		Intent data = new Intent();
		setResult(RESULT_CANCELED, data);
		finish();
	}
	
	public void postTweet(View v){
		newTweet = etTweet.getEditableText().toString();
		postData();
		Intent data = new Intent();
		data.putExtra(TimelineActivity.TWEETS, newTweet);
		setResult(RESULT_OK, data);
		finish();
	}		
	
	private void postData(){
		MyTwitterApp.getRestClient().postTweet(new AsyncHttpResponseHandler(), newTweet);
		
	}


}

