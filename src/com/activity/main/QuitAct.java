package com.activity.main;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;

public class QuitAct extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quit);
		
		Timer timer = new Timer();
        TimerTask task = new TimerTask(){
        	@Override
        	public void run(){
        		finish();        		//÷¥––
        	}
        };
        timer.schedule(task, 1000 * 2); //1√Î∫Û
	}
}
