package com.codepath.twitter.twitterapp;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.twitter.twitterapp.fragments.HomeTimeLineFragment;
import com.codepath.twitter.twitterapp.fragments.MentionsFragment;
import com.codepath.twitter.twitterapp.fragments.TimelineTweetFragment;
import com.codepath.twitter.twitterapp.json.models.Tweet;

public class TimelineActivity extends SherlockFragmentActivity  {
	
	public static final String TWEETS = "tweets";
	public static final int REQUEST_CODE=200;
	public ListView lvTweets;
	public TweetAdapter tweetAdapter;
	public ArrayList<Tweet> tweets;
	public int totalItems = 25;
	public final int visibleThreshold = 25;
	public long max_id=0;
	TimelineTweetFragment fragmentTimeline;
	public String currentUser;
	Tab tabMentions;
	Tab tabHome;
	public String newTweet;
	com.actionbarsherlock.app.ActionBar actionBar;
	//Tab tabHome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
		//setupNavigationTabs();
		
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
	
	private void setupTabs() {
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		tabHome = actionBar
		    .newTab()
		    .setText("Home")
		    .setIcon(R.drawable.ic_home_new)
		    .setTag("HomeTimelineFragment")
		    .setTabListener(new SherlockTabListener<HomeTimeLineFragment>(R.id.frame_container, this,
                        "Home", HomeTimeLineFragment.class));

		actionBar.addTab(tabHome);
		actionBar.selectTab(tabHome);

		tabMentions = actionBar
		    .newTab()
		    .setText("Mentions")
		    .setIcon(R.drawable.ic_mentions)
		    .setTag("MentionsTimelineFragment")
		    .setTabListener(new SherlockTabListener<MentionsFragment>(R.id.frame_container, this,
                        "Mentions", MentionsFragment.class));
		actionBar.addTab(tabMentions);
		
	}

	public void onProfileView(MenuItem mi){
		Intent i = new Intent(this, ProfileViewActivity.class);
		
		startActivity(i);
	}

	/*private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		tabHome = actionBar.newTab().setText("Home").setTag("HomeTimeLineFragment").setIcon(R.drawable.ic_home_new).setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsFragment").setIcon(R.drawable.ic_mentions).setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
		
	}*/

	
	
	private List<com.codepath.twitter.twitterapp.json.models.Tweet> readFromDatabase() {
		// TODO Auto-generated method stub
		List<com.codepath.twitter.twitterapp.json.models.User> us = com.codepath.twitter.twitterapp.json.models.User.getAll();
		if(us != null && us.size() > 0){
			for(com.codepath.twitter.twitterapp.json.models.User u: us){
			 System.out.println("User Objects ::::: " + u.getId());
			}
		}
		return com.codepath.twitter.twitterapp.json.models.Tweet.getAll(us.get(0));
	}

	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.timeline, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.miProfile:
	            onProfileView(item);
	           break;
	        case R.id.miCompose:
	        	onComposeAction(item);
	        	break;
	        default:
	        	return true;
	    }
	    return true;
	}
	
	public void onComposeAction(MenuItem mi){
		Intent it = new Intent(this, TwitterComposeActivity.class);
		startActivityForResult(it, REQUEST_CODE);
	}
	
	 public String getNewTweet(){
		 return newTweet;
	 }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE){
			if(resultCode == RESULT_OK){

				this.setIntent(data);
				/*Tweet tw = new Tweet();
				tw.user = new User();
				String newTweet = data.getStringExtra(TWEETS);
				if(tweets != null && tweets.size() > 0){
					tw.user = tweets.get(0).getUser();
				} 
				
				tw.created_at = new Date().toString();
				tw.body = newTweet;
				tw.getUser().name = "Himanshu Kapse";
				tw.getUser().screen_name = "hemankaps";*/
				
			} else if(resultCode == RESULT_CANCELED){
				//do nothing
				
			}
		}
	
	}

	/*@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		
	}*/

	/*@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
		if(tab.getTag() == "HomeTimeLineFragment"){
			ft.replace(R.id.frame_container, new HomeTimeLineFragment());
		}else {
			ft.replace(R.id.frame_container, new MentionsFragment());
		}
		ft.commit();
	}
*/
	/*@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		
	}*/
	

}
