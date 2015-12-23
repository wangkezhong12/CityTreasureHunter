package com.activity.support;

import com.activity.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SupportMainSelection extends Activity{
	private ImageButton introduction;
	private ImageButton rule;
	private ImageButton team;
	private ImageButton cancel;
	
	private Intent introductionintent;
	private Intent ruleintent;
	private Intent teamintent;	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.support_mainselection);
		
		introduction = (ImageButton) findViewById(R.id.Introduction);
		rule = (ImageButton) findViewById(R.id.Rule);
		team = (ImageButton) findViewById(R.id.Team);
		cancel = (ImageButton) findViewById(R.id.Cancel);
		
		introductionintent = new Intent(SupportMainSelection.this, SupportSoftware.class);
		ruleintent = new Intent(SupportMainSelection.this, SupportRule.class);
		teamintent = new Intent(SupportMainSelection.this, SupportCreator.class);
		
		introduction.setOnClickListener(new View.OnClickListener(){		
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(introductionintent);				
			}
		});
		
		rule.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(ruleintent);				
			}
		});
		
		team.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(teamintent);
			}
		});
		
		cancel.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
