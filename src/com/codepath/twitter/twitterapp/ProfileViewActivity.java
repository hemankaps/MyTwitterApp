package com.codepath.twitter.twitterapp;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.codepath.twitter.twitterapp.fragments.UserTimeLineFragment;
import com.codepath.twitter.twitterapp.json.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileViewActivity extends SherlockFragmentActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		//setContentView(R.layout.activity_profile_view);
		
		Intent i = getIntent();
		User user = (User) i.getSerializableExtra("screen_name");
		/*if(user != null){
			getActionBar().setTitle("@" + user.getScreenName());
			populateHeader(user);
			
		} else {
			fetchUserProfile();
		}*/
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.fragmentUserTimeline, new UserTimeLineFragment());
		ft.commit();
	}

	 // Should be called manually when an async task has started
    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true); 
    }

    // Should be called when an async task has finished
    public void hideProgressBar() {
        setProgressBarIndeterminateVisibility(false); 
    }
    
	private void fetchUserProfile() {
		//showProgressBar();
		MyTwitterApp.getRestClient().getUserInfo(new JsonHttpResponseHandler(){
				
				@Override
				public void onSuccess(JSONObject jsonObj) {
					User u = User.fromJson(jsonObj);
					getActionBar().setTitle("@" + u.getScreenName());
			//		populateHeader(u);
				}
			});
		//hideProgressBar();
	}
	
	

	protected void populateHeader(User u) {
	/*	TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollower);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImg = (ImageView) findViewById(R.id.ivProfileImage);
		
		//set the values
		tvName.setText(u.getName());
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFollowing() + " Following");
		tvTagLine.setText(u.getTagLine());
		
		//load Profile Image
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImg);*/
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.profile_view, menu);
		return onCreateOptionsMenu(menu);
	}

}
