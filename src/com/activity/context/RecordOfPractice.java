package com.activity.context;

import com.activity.main.R;
import com.function.singlecontext.GetSingleRecord;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecordOfPractice extends Activity{
	//private TextView actname;
	private TextView usetime;
	private TextView checknumx;
	private TextView checknumy;
	
	private ImageButton ok;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.context_recordofpractice);
		
		int poiNum = this.getIntent().getIntExtra("arrivedPoi", 0);
		int poiMax = this.getIntent().getIntExtra("poiMax", 0);
		
		long practicetime = this.getIntent().getLongExtra("practicetime", 0);
		long hour = practicetime/(1000*60*60);
		long min = practicetime/(1000*60)-hour*60;
		long s = practicetime/1000-hour*60*60-min*60;
		String time = hour+" ±"+min+"∑÷"+s+"√Î";
		
		//actname = (TextView)this.findViewById(R.id.practiceactname);
		usetime = (TextView)this.findViewById(R.id.practiceusetime);
		checknumx = (TextView)this.findViewById(R.id.practicechecknumx);
		checknumy = (TextView)this.findViewById(R.id.practicechecknumy);
		//String uid="1";
		//String sid="1";
		ok = (ImageButton)this.findViewById(R.id.ok);
		//String[] singlerecord=new GetSingleRecord().doInBackground(uid,sid,"single");
		
		//MapApplication data = ((MapApplication)getApplicationContext()); 
		//String actName = data.getActName();
		
		//actname.setText(actName);
		usetime.setText(time);
		int t = Integer.parseInt(time);
		MapApplication data = ((MapApplication)getApplicationContext()); 
		data.setLimitTime(t);
		
		checknumx.setText(poiNum+"");
		checknumy.setText(poiMax+"");
		ok.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
