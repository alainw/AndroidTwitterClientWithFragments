package com.yahoo.apps.tweetsclient;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.tweetsclient.models.Tweet;
import com.yahoo.apps.tweetsclient.models.User;

public class TimelineActivity extends Activity {

	public static final int REQ_OPTIONS_OK = 1;
	
	private ListView lvTweets;  
	private List<Tweet> tweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		Log.d("DEBUG", "creating timeline activity"); 
		lvTweets = (ListView) findViewById(R.id.lvTweets); 
        refresh();
	} 

	private void refresh() {
		tweets = new Select().from(Tweet.class).execute(); // returns in desired order
		Log.d("DEBUG", "tweets from cache: " + (tweets==null ? "null":tweets));
		if (tweets != null && tweets.size() > 0) {
		    lvTweets.setAdapter(new TweetsAdapter(getBaseContext(), tweets));
		}
		TweetsClientApp.getRestClient().getHomeTimeline(
			    new JsonHttpResponseHandler() {
			        @Override
					public void onFailure(Throwable arg0, JSONArray arg1) { 
			        	Log.d("DEBUG", "JSONArray: " + arg0 + " " + arg1);  
					}

					@Override
					public void onFailure(Throwable arg0, JSONObject arg1) {
						Log.d("DEBUG", "JSONObject: " + arg0 + " " + arg1);    
					} 
	 
					@Override  
			        public void onSuccess(JSONArray jsonTweets) {
			        	tweets = Tweet.fromJson(jsonTweets);
			        	TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
			    		lvTweets.setAdapter(adapter);
			    		ActiveAndroid.beginTransaction();
			    		try {
			    			new Delete().from(User.class).execute();
			    			new Delete().from(Tweet.class).execute();
			    		    for (Tweet t : tweets) {
			    		    	//Log.d("DEBUG", "Saving: " + t.getBody() + ", user: " + t.getUser());
			    		    	t.save();
			    		    }
			    		    ActiveAndroid.setTransactionSuccessful();
			    		} finally {
			    			ActiveAndroid.endTransaction();
			    		}
			        }  
			    });		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	public void onClickRefresh(MenuItem mi) {
	    refresh();	
	}
	
	public void onClickCompose(MenuItem mi) { 
	    Intent i = new Intent(getApplicationContext(), ComposeTweetActivity.class);
	    startActivityForResult(i, REQ_OPTIONS_OK);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK && requestCode == REQ_OPTIONS_OK) {
		     refresh();
	    }
	}	
	
}
