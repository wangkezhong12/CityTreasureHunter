package com.activity.context;

import com.activity.main.R;
import com.function.doublefinder.GetDoubleRecord;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecordOfDouble extends Activity{
	//private TextView teamname;
	private TextView actname;
	private TextView usetime;

	
	private ImageButton ok;
	String sid;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.context_recordofdouble);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		sid = data.getActId();
		
		//teamname = (TextView)this.findViewById(R.id.teamname);
		actname = (TextView)this.findViewById(R.id.actname);
		usetime = (TextView)this.findViewById(R.id.usetime);
		
		ok = (ImageButton)this.findViewById(R.id.ok);
		String[] doublerecord=new GetDoubleRecord().doInBackground(sid.toString());
		actname.setText(doublerecord[0].toString());
		usetime.setText(doublerecord[1].toString());
		ok.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
