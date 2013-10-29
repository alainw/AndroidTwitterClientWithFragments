package com.yahoo.apps.tweetsclient.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yahoo.apps.tweetsclient.R;
import com.yahoo.apps.tweetsclient.TweetsAdapter;
import com.yahoo.apps.tweetsclient.models.Tweet;

public class TweetsFragment extends Fragment {

    private TweetsAdapter tweetsAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		tweetsAdapter = new TweetsAdapter(getActivity(), new ArrayList<Tweet>());
    }   
    
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) { 
		return inf.inflate(R.layout.fragment_tweets, parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetsAdapter);
	}
	
	public void setTweets(List<Tweet> tweets) {
		tweetsAdapter.clear();
		tweetsAdapter.addAll(tweets);
	}

}
