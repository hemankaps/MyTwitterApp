package com.codepath.twitter.twitterapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class SherlockTabListener<T extends SherlockFragment> implements
		TabListener {
	private SherlockFragment mFragment;
	private final SherlockFragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	private final int mfragmentContainerId;
	private FragmentManager fManager;

	// This version defaults to replacing the entire activity content area
	// new SherlockTabListener<SomeFragment>(this, "first", SomeFragment.class))
	public SherlockTabListener(SherlockFragmentActivity activity, String tag,
			Class<T> clz) {
		mActivity = activity;
		fManager = activity.getSupportFragmentManager();
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = android.R.id.content;
	}

	// This version supports specifying the container to replace with fragment
	// content
	// new SherlockTabListener<SomeFragment>(R.id.flContent, this, "first",
	// SomeFragment.class))
	public SherlockTabListener(int fragmentContainerId,
			SherlockFragmentActivity activity, String tag, Class<T> clz) {
		mActivity = activity;
		fManager = activity.getSupportFragmentManager();
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = fragmentContainerId;
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft = fManager.beginTransaction();
		// Check if the fragment is already initialized
		//if (mFragment == null) {
			// If not, instantiate and add it to the activity
			mFragment = (SherlockFragment) SherlockFragment.instantiate(
					mActivity, mClass.getName());
			ft.add(mfragmentContainerId, mFragment, mTag);
		//}
			/*else {
			// If it exists, simply attach it in order to show it
			ft.attach(mFragment);
		}*/
		ft.commit();
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft = fManager.beginTransaction();
		if (mFragment != null) {
			// Detach the fragment, because another one is being attached
			ft.detach(mFragment);
		}
		ft.commit();
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// User selected the already selected tab. Usually do nothing.
		
	}
}