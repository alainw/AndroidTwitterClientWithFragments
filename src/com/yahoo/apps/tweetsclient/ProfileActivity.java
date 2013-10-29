package com.yahoo.apps.tweetsclient;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.apps.tweetsclient.fragments.UserTimelineFragment;
import com.yahoo.apps.tweetsclient.models.User;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
	    String screenName = getIntent().getStringExtra("screenName");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment fragment = new UserTimelineFragment(); 
		fragment.setScreenName(screenName);
		ft.replace(R.id.flContainer, fragment);
		ft.commit();
		getUserInfo(screenName);
	}		
	
	private void getUserInfo(String screenName) {
		TweetsClientApp.getRestClient().getUserInfo(screenName,
			    new JsonHttpResponseHandler() {
			        @Override
					public void onFailure(Throwable arg0, JSONArray arg1) { 
						Log.d("DEBUG", "failed1!: " + arg0 + " " + arg1);
					}

					@Override
					public void onFailure(Throwable arg0, JSONObject arg1) {
						Log.d("DEBUG", "failed2!" + arg0 + " " + arg1);    
					}
	 
					@Override  
			        public void onSuccess(JSONObject userInfo) {
			        	Log.d("DEBUG object", userInfo.toString());
			        	User user = User.fromJson(userInfo);
			        	setProfileInfo(user);
			        }  									
			    });				
	}
	
	private void setProfileInfo(User user) {
    	getActionBar().setTitle("@" + user.getScreenName());
	    ImageView ivProfileImg = (ImageView) findViewById(R.id.ivProfileImg);
	    TextView tvName = (TextView) findViewById(R.id.tvName);
	    TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
	    TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
	    TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
	     
	    tvName.setText(user.getName());
	    tvTagline.setText(user.getTagline());
	    tvFollowers.setText(user.getFollowersCount() + " followers");
	    tvFollowing.setText(user.getFriendsCount() + " following");
	    ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImg);	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
