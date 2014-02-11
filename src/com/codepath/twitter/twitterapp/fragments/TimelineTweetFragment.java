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
		//testNewModel(jsonTwittArray);
		//fetchTweets(totalItems);
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

	
	
  public abstract void fetchTweets(int offset);//{
		
		/*try{
			List<com.codepath.twitter.twitterapp.json.models.Tweet> ts = readFromDatabase();
			if(ts != null && ts.size() > 0){
				for(com.codepath.twitter.twitterapp.json.models.Tweet t: ts){
					System.out.println("Getting from DB :::::::: " + t.getBody());
				}
			}
		} catch(Exception ex){
			System.out.println("No SQLite DB found");		
		}*/
		
		/*MyTwitterApp.getRestClient().getHomeTimeline(String.valueOf(max_id), String.valueOf(offset), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTwittArray) {
				testNewModel(jsonTwittArray);
				tweets = Tweet.fromJson(jsonTwittArray);
				tweetAdapter.addAll(tweets);
				tweetAdapter.notifyDataSetChanged();
				if(tweets != null && tweets.size() > 0){
					max_id = tweets.get(tweets.size() - 1).getTId() - 1;
				}*/
				//int i = tweets.size();
				///if(i != 0){
					//Tweet tweet = tweets.get(i-1);
					//max_id = String.valueOf(tweet.getId());
				//}
				
			//} to be opened

			/*private void testNewModel(JSONArray jsonTwittArray) {
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
		});*/

	//}


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
