package com.activity.main;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoginWait extends Activity{	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_loginwait);
		final Intent it = new Intent(this, SwitchView.class); //你要转向的Activity  
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
        	@Override
        	public void run() {
        		finish();
        		startActivity(it); //执行
        	}
        };
        timer.schedule(task, 1000 * 1); //1秒后
	}
}
