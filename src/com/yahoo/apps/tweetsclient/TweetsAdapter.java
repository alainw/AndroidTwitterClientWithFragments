package com.yahoo.apps.tweetsclient;

import java.util.List; 

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;  

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.apps.tweetsclient.models.Tweet;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) { 
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.tweet_item, null);
		}
		final Tweet t = getItem(position);
		
		ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(t.getUser().getProfileImageUrl() , ivProfile);
		
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		String formattedName = "<b>" + t.getUser().getName() + "</b> <small><font color='#777777'>@" + 
		    t.getUser().getScreenName() + "</font></small>";
		tvName.setText(Html.fromHtml(formattedName));
		
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		tvBody.setText(Html.fromHtml(t.getBody()));

		// Is there a more efficient way to do this? Instead of a new listener for new row?
		ivProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			    //Log.d("DEBUG", "Getting data for: " + t.getUser().getScreenName()); 
			    Intent i = new Intent(v.getContext(), ProfileActivity.class);
			    i.putExtra("screenName", t.getUser().getScreenName());
			    v.getContext().startActivity(i);
			}
		});		
		return convertView;
	}
	
}
