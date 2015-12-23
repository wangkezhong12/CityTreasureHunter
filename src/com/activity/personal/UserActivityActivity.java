package com.activity.personal;

import com.activity.context.MapApplication;
import com.activity.main.R;
import com.function.personal.PerActs;
import com.function.personal.PerCreateActs;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivityActivity extends Activity{
	private ImageView mrefresh;
	private ImageView back;
	private LinearLayout progress;
	
	private TextView joined;
	private TextView built;

	private String userId;
	PerActs pa = new PerActs();
	PerCreateActs pca =  new PerCreateActs();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.personal_userrecord);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		userId = data.getUserId();
		
		String[] actjioned = pa.GetActList(userId);
		String[] actcreated = pca.doInBackground(userId);
		
			
		back = (ImageView) findViewById(R.id.Back);
		progress = (LinearLayout) findViewById(R.id.progress);
		
		joined = (TextView) findViewById(R.id.Joined);
		built = (TextView) findViewById(R.id.Built);
		
		
		for(int i=0;i<actjioned.length;i++){
			joined.append(actjioned[i].toString()+"\n");
		}
		
		for(int i=0;i<actcreated.length;i++){
			built.append(actcreated[i].toString()+"\n");
		}
		
		back.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}	
}
