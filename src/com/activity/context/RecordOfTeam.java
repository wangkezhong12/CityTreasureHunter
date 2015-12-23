package com.activity.context;

import com.activity.main.R;
import com.function.singlecontext.GetSingleRecord;
import com.function.team.GetTeamRecord;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecordOfTeam extends Activity{
	private TextView teamname;
	private TextView actname;
	private TextView usetime;
	private TextView checknumx;
	private TextView checknumy;
	
	private ImageButton ok;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.context_recordofteam);
		
		teamname = (TextView)this.findViewById(R.id.teamname);
		actname = (TextView)this.findViewById(R.id.actname);
		usetime = (TextView)this.findViewById(R.id.usetime);
		checknumx = (TextView)this.findViewById(R.id.checknumx);
		checknumy = (TextView)this.findViewById(R.id.checknumy);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		String tid = data.getTeamId();
		String sid = data.getActId();
		
		ok = (ImageButton)this.findViewById(R.id.ok);
		String[] teamrecord=new GetTeamRecord().doInBackground(tid,sid,"team");
		teamname.setText(teamrecord[0].toString());
		actname.setText(teamrecord[1].toString());
		usetime.setText(teamrecord[2].toString());
		checknumx.setText(teamrecord[3].toString());
		ok.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
