package com.yahoo.apps.tweetsclient;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.yahoo.apps.tweetsclient.fragments.MentionsFragment;
import com.yahoo.apps.tweetsclient.fragments.TimelineFragment;

public class TimelineActivity extends FragmentActivity implements TabListener {

	public static final int REQ_OPTIONS_OK = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		Log.d("DEBUG", "creating timeline activity"); 
		setupNavigationMenu();
	}  
	
	private void setupNavigationMenu() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home).setTabListener(this);
        Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsTimelineFragment").setIcon(R.drawable.ic_mentions).setTabListener(this);
        actionBar.addTab(tabHome);
        actionBar.addTab(tabMentions);
        actionBar.selectTab(tabHome);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onClickCompose(MenuItem mi) { 
	    Intent i = new Intent(getApplicationContext(), ComposeTweetActivity.class);
	    startActivityForResult(i, REQ_OPTIONS_OK);
	}
	
	public void onClickProfile(MenuItem mi) {
	    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
	    startActivity(i);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager fm = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = fm.beginTransaction();
		if (tab.getTag().equals("HomeTimelineFragment")) {
		    fts.replace(R.id.flContainer, new TimelineFragment());	
		} else if (tab.getTag().equals("MentionsTimelineFragment")) {
			fts.replace(R.id.flContainer, new MentionsFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {	
	}	
	
}
