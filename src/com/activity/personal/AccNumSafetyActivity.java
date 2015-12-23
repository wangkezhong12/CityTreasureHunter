package com.activity.personal;

import com.activity.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AccNumSafetyActivity extends Activity{
	private ImageButton changepwd;
	private ImageButton setprotection;
	private ImageButton back;
	
	private Intent changeintent;
	private Intent setintent;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.personal_accnumsafety);
		
		changepwd = (ImageButton) findViewById(R.id.ChangePwd);
		setprotection = (ImageButton) findViewById(R.id.SetProtection);
		back = (ImageButton) findViewById(R.id.Back);
		
		changeintent = new Intent(AccNumSafetyActivity.this, ChangePwd.class);
		setintent = new Intent(AccNumSafetyActivity.this, SetProtection.class);
		
		changepwd.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(changeintent);				
			}
		});
		
		setprotection.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(setintent);				
			}
		});
		
		back.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});
	}	
}







