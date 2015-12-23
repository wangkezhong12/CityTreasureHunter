package com.activity.buildact;

import com.activity.context.DoubleTarget;
import com.activity.main.MainInterface;
import com.activity.main.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class BuildSuccess extends Activity{
	private TextView back;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.build_success);
		
		final String type = this.getIntent().getStringExtra("mytype");
		
		back = (TextView) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View arg0){
				// TODO Auto-generated method stub
				back.setTextColor(Color.YELLOW);
				new Handler().postDelayed(new Runnable(){  
				     public void run(){  
				     //execute the task
				    	 Intent intent = null;
				    	 if (type.equals("double"))
				    		 intent = new Intent(BuildSuccess.this,DoubleTarget.class);
				    	 else
				    		 intent = new Intent(BuildSuccess.this,MainInterface.class);
				    	 startActivity(intent);
				    	 finish();				    	 
				     }  
				}, 100);
			}
		});
	}
}
