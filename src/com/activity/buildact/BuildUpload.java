package com.activity.buildact;

import java.util.Timer;
import java.util.TimerTask;

import com.activity.context.DoubleTarget;
import com.activity.context.MapApplication;
import com.activity.main.R;
import com.function.joinact.GetSid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class BuildUpload extends Activity{
	String symbol="";
	Intent it;
	
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_upload);

        if(getIntent().getStringExtra("symbol") != null) {  
			symbol = getIntent().getStringExtra("symbol");
		}
         
        String lat = this.getIntent().getStringExtra("lat");
        String lon = this.getIntent().getStringExtra("lon");
        String type = this.getIntent().getStringExtra("type");
        //Toast.makeText(BuildUpload.this, lat+" "+lon, Toast.LENGTH_SHORT).show(); 
        
        if (type.equals("双人互找")){
        	String actId = new GetSid().Getsid(lon, lat, "double");
        	//Toast.makeText(BuildUpload.this, "!"+actId+"!", Toast.LENGTH_SHORT).show(); 
        	MapApplication data = ((MapApplication) getApplicationContext());
    		data.setActId(actId);
        	//it = new Intent(this, DoubleTarget.class);
        }
        //else
        it = new Intent(this, BuildSuccess.class); //你要转向的Activity 
        it.putExtra("mytype", "double");
        
        final Intent back = new Intent(this, BuildBrowsing.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
        	@Override
        	public void run(){
        		if(symbol.equals("1")){
        			int len = BuildInformation.buildActivityList.size();
        	        for (int i=0;i<len;i++){
        	        	if (BuildInformation.buildActivityList.get(i) != null)
        	        		BuildInformation.buildActivityList.get(i).finish();
        	        }
            		startActivity(it); //执行
            		finish();
        		}
        		else{
        			Toast.makeText(BuildUpload.this, "抱歉，创建活动失败，请重新尝试", Toast.LENGTH_SHORT).show();
            		startActivity(back); //执行
            		finish();

        		}
        			
        		
        	}
        };
        timer.schedule(task, 1000 * 3); //1秒后
    }
}
