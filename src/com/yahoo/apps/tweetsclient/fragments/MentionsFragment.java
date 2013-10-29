package com.yahoo.apps.tweetsclient.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.tweetsclient.TweetsClientApp;
import com.yahoo.apps.tweetsclient.models.Tweet;

import android.os.Bundle;
import android.util.Log;

public class MentionsFragment extends TweetsFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		
		TweetsClientApp.getRestClient().getMentionsTimeline(
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
			        	setTweets(Tweet.fromJson(jsonTweets));
			        	/*2
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
			    		*/
			        }  
			    });	
		
    }
}
