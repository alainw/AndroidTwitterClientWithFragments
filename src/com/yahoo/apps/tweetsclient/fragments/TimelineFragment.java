package com.yahoo.apps.tweetsclient.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.tweetsclient.TweetsClientApp;
import com.yahoo.apps.tweetsclient.models.Tweet;

public class TimelineFragment extends TweetsFragment {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		
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
			        	setTweets(Tweet.fromJson(jsonTweets));
			        }  
			    });
		
    }
    
}
