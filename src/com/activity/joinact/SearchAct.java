package com.activity.joinact;

import java.util.ArrayList;
import java.util.List;

import com.activity.main.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class SearchAct extends TabActivity implements OnCheckedChangeListener{
	public static List<Activity> joinActActivityList = new ArrayList<Activity>();
	
	private TabHost tabhost;
	private Intent mapsearchintent;
	private Intent listsearchintent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.joinact_searchact);
        
        joinActActivityList.add(this);
        
        this.mapsearchintent = new Intent(this,MapSearch.class);
        this.listsearchintent = new Intent(this,ListSearch.class);
        
		((RadioButton) findViewById(R.id.mapsearch))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.listsearch))
		.setOnCheckedChangeListener(this);
        
        setupIntent();
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			switch (buttonView.getId()) {
			case R.id.mapsearch:
				this.tabhost.setCurrentTabByTag("mapsearch_TAB");
				break;
			case R.id.listsearch:
				this.tabhost.setCurrentTabByTag("listsearch_TAB");
				break;
			}
		}
		
	}
	
	private void setupIntent() {
		this.tabhost = getTabHost();
		TabHost localTabHost = this.tabhost;

		localTabHost.addTab(buildTabSpec("mapsearch_TAB", R.string.mapsearch,R.drawable.dt, this.mapsearchintent));
		

		localTabHost.addTab(buildTabSpec("listsearch_TAB", R.string.listsearch,
				R.drawable.lb, this.listsearchintent));

	}
	
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.tabhost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	}
}
