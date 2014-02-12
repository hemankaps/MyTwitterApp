package com.codepath.twitter.twitterapp;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.twitter.twitterapp.json.models.Tweet;
import com.codepath.twitter.twitterapp.json.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetAdapter extends ArrayAdapter<Tweet> {
	public TweetAdapter(Context context, List<Tweet> tweets){
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		
		Tweet tweet = getItem(position);
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		if(null != tweet.getUser() && tweet.getUser().getProfileImageUrl() != null){
			ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
		}
		imageView.setTag(tweet.getUser());
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), ProfileViewActivity.class);
				Bundle bd = new Bundle();
				bd.putSerializable("screen_name",(User) v.getTag());
				i.putExtras(bd);
				getContext().startActivity(i);
			}
		});
		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		//String formattedName = "<b>" + tweet.getUser().getName() + "<b>" + "<small><font color='#777777'>@" + tweet.getUser().getScreenName() + "</font> " + tweet.getCreationTime()+ "</small>";
		String formattedName = tweet.getUser().getName() + "@" + tweet.getUser().getScreenName() + " " + tweet.getCreated_at();
//		nameView.setText(Html.fromHtml(formattedName));
		nameView.setText(formattedName);
		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		
		return view;
	}
}
