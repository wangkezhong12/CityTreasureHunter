package com.activity.main;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class LoginRegisterSuccess extends Activity{
	private TextView login;
	//private Intent intent;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.login_registersuccess);
		
		login = (TextView) findViewById(R.id.login);
		//intent = new Intent(LoginRegisterSuccess.this, Login.class);
		login.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View arg0){
				// TODO Auto-generated method stub
				login.setTextColor(Color.YELLOW);
				new Handler().postDelayed(new Runnable(){  
				     public void run(){  
				     //execute the task
				    	 finish();
				    	 //startActivity(intent);
				     }  
				}, 100);
			}
		});
	}

}
