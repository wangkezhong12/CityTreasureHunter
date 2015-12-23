package com.activity.context;

import com.activity.main.R;
import com.function.singlecontext.GetSingleRecord;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecordOfSingle extends Activity{
	private TextView teamname;
	private TextView actname;
	private TextView usetime;
	private TextView checknumx;
	private TextView checknumy;
	
	private ImageButton ok;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.context_recordofsingle);
		actname = (TextView)this.findViewById(R.id.actname);
		usetime = (TextView)this.findViewById(R.id.usetime);
		checknumx = (TextView)this.findViewById(R.id.checknumx);
		checknumy = (TextView)this.findViewById(R.id.checknumy);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		String uid = data.getUserId();
		String sid = data.getActId();
			
		ok = (ImageButton)this.findViewById(R.id.ok);
		String[] singlerecord=new GetSingleRecord().doInBackground(uid,sid,"single");
		actname.setText(singlerecord[0].toString());
		usetime.setText(singlerecord[1].toString());
		checknumx.setText(singlerecord[2].toString());
		ok.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
