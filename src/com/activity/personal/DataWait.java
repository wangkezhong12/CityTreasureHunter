package com.activity.personal;

import java.util.Timer;
import java.util.TimerTask;

import com.activity.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DataWait extends Activity{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_upload);
        final Intent it = new Intent(this, Success.class); //你要转向的Activity  
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
        	@Override
        	public void run(){
        		finish();
        		startActivity(it); //执行
        	}
        };
        timer.schedule(task, 1000 * 1); //1秒后
    }
}

