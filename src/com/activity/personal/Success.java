package com.activity.personal;

import com.activity.main.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class Success extends Activity{
	private TextView back;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.personal_setsuccessful);
		
		back = (TextView) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View arg0){
				// TODO Auto-generated method stub
				back.setTextColor(Color.YELLOW);
				new Handler().postDelayed(new Runnable(){  
				     public void run(){  
				     //execute the task
				    	 finish();				    	 
				     }  
				}, 100);
			}
		});
	}
}
