package com.activity.support;

import com.activity.main.R;

import android.app.TabActivity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class SupportRule extends TabActivity{		 
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
		
		final TabHost tabHost = this.getTabHost();
		final TabWidget tabWidget = tabHost.getTabWidget();
		LayoutInflater.from(this).inflate(R.layout.support_rule, tabHost.getTabContentView(), true);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("单人赛",getResources().getDrawable(R.drawable.danren)).setContent(R.id.view1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("团体赛",getResources().getDrawable(R.drawable.tuanti)).setContent(R.id.view2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("双人互找",getResources().getDrawable(R.drawable.shuangren)).setContent(R.id.view3));		
		
		for(int i = 0; i < tabWidget.getChildCount(); i++){ 
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			View v = tabWidget.getChildAt(i);
			if (tabHost.getCurrentTab() == i){
				v.setBackgroundColor(Color.BLACK);
				tv.setTextColor(Color.WHITE);
				tv.setTextSize(13);
				tv.setTypeface(Typeface.SERIF, 2); 
			} else{
				v.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.BLACK);
				tv.setTextSize(13);
				tv.setTypeface(Typeface.SERIF, 2); 
			}
		}
		 
		tabHost.setOnTabChangedListener(new OnTabChangeListener(){			 
			public void onTabChanged(String tabId) {
			 // TODO Auto-generated method stub			 
				for(int i = 0; i < tabWidget.getChildCount(); i++){
					TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);					
					View v = tabWidget.getChildAt(i);
					if (tabHost.getCurrentTab() == i) {
						v.setBackgroundColor(Color.BLACK);
						tv.setTextColor(Color.WHITE);
						tv.setTextSize(13);
						tv.setTypeface(Typeface.SERIF, 2); 
					} else {
						v.setBackgroundColor(Color.GRAY);
						tv.setTextColor(Color.BLACK);
						tv.setTextSize(13);
						tv.setTypeface(Typeface.SERIF, 2); 
					}
				}
			}
		});			 
	}
}
