package com.codepath.twitter.twitterapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.activeandroid.ActiveAndroid;
import com.codepath.twitter.twitterapp.fragments.HomeTimeLineFragment;
import com.codepath.twitter.twitterapp.fragments.MentionsFragment;
import com.codepath.twitter.twitterapp.fragments.TimelineTweetFragment;
import com.codepath.twitter.twitterapp.json.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

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
	//Tab tabHome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
		//setupNavigationTabs();
		//fragmentTimeline = (TimelineTweetFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTimeLine);
		
		//fetchTweets(totalItems);
		//setListView();
		
		
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
		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
		    .newTab()
		    .setText("Home")
		    .setIcon(R.drawable.ic_home_new)
		    .setTag("HomeTimelineFragment")
		    .setTabListener(new SherlockTabListener<HomeTimeLineFragment>(R.id.frame_container, this,
                        "Home", HomeTimeLineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
		    .newTab()
		    .setText("Mentions")
		    .setIcon(R.drawable.ic_mentions)
		    .setTag("MentionsTimelineFragment")
		    .setTabListener(new SherlockTabListener<MentionsFragment>(R.id.frame_container, this,
                        "Mentions", MentionsFragment.class));
		actionBar.addTab(tab2);
		
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

	private void fetchTweets(int offset){
		
		try{
			List<com.codepath.twitter.twitterapp.json.models.Tweet> ts = readFromDatabase();
			if(ts != null && ts.size() > 0){
				for(com.codepath.twitter.twitterapp.json.models.Tweet t: ts){
					System.out.println("Getting from DB :::::::: " + t.getBody());
				}
			}
		} catch(Exception ex){
			System.out.println("No SQLite DB found");		
		}
		
		MyTwitterApp.getRestClient().getHomeTimeline(String.valueOf(max_id), String.valueOf(offset), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTwittArray) {
				//testNewModel(jsonTwittArray);
				tweets = Tweet.fromJson(jsonTwittArray);
				fragmentTimeline.getAdapter().addAll(tweets);
				//tweetAdapter.notifyDataSetChanged();
				//if(tweets != null && tweets.size() > 0){
				//	max_id = tweets.get(tweets.size() -1).getId() - 1;
				//}
				//int i = tweets.size();
				///if(i != 0){
					//Tweet tweet = tweets.get(i-1);
					//max_id = String.valueOf(tweet.getId());
				//}
				
			}

			private void testNewModel(JSONArray jsonTwittArray) {
				ArrayList<com.codepath.twitter.twitterapp.json.models.Tweet> tws = new ArrayList<com.codepath.twitter.twitterapp.json.models.Tweet>();
				tws = com.codepath.twitter.twitterapp.json.models.Tweet.fromJson(jsonTwittArray);
				if(tws != null && tws.size() > 0){
					ActiveAndroid.beginTransaction();
					try{
						for(com.codepath.twitter.twitterapp.json.models.Tweet tw: tws){
							System.out.println("Remote Id = " + tw.getRemoteId() + "::: TEXT ::: " + tw.getBody());
							System.out.println("User INFO NAME :::::::: " + tw.getUser().getName() + ":::::::::: Screen_Name ::::: " + tw.getUser().getScreenName() + "::::: UserId :::: " + tw.getUser().getUserId());
							System.out.println("Now Saving to DB");
								
							tw.getUser().save();
							System.out.println("User Saved");
							tw.save();
							System.out.println("Tweet Saved");
							
						}
						ActiveAndroid.setTransactionSuccessful();
					} catch(SQLException sqe){
						System.out.println("Exception to be ignored :::");
					}
					catch(Exception e){
						
					}
					finally{
						ActiveAndroid.endTransaction();
					}
				}
			}
		});

	}
	
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE){
			if(resultCode == RESULT_OK){
				/*Tweet tw = new Tweet();
				String newTweet = data.getStringExtra(TWEETS);
				if(tweets != null && tweets.size() > 0){
					tw.user = tweets.get(0).getUser();
				} 
				
				tw.created_at = new Date().toString();
				tw.body = newTweet;
				tw.getUser().name = "Himanshu Kapse";
				tw.getUser().screen_name = "hemankaps";
				tweetAdapter.add(tw);
				tweetAdapter.notifyDataSetChanged();*/
				
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
