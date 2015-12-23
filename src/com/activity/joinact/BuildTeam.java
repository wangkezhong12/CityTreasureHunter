package com.activity.joinact;

import com.activity.context.MapApplication;
import com.activity.main.R;
import com.function.joinact.GetTeamTotal;
import com.function.joinact.GetTeamid;
import com.function.team.JoinGroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class BuildTeam extends Activity{
	private ImageButton ok;
	private ImageButton back;
	private Intent okintent;
	
	private EditText teamname;
	private EditText teamintro;
	
	String userId;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinact_buildteam);
		
		SearchAct.joinActActivityList.add(this);
		
		final MapApplication data = ((MapApplication) getApplicationContext());
		userId = data.getUserId();
        
		
		ok = (ImageButton) this.findViewById(R.id.OK);
		back = (ImageButton) this.findViewById(R.id.Back);
		teamname = (EditText) this.findViewById(R.id.teamname);
		teamintro = (EditText) this.findViewById(R.id.teamintro);
		
		okintent = new Intent(BuildTeam.this, BuildTeamUpload.class);
		
		ok.setOnClickListener(new View.OnClickListener() {			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = teamname.getText().toString();
				String info = teamintro.getText().toString();
				if (!name.equals("") && !info.equals("")){
					// TODO ÉÏ´«
					data.setIsHead(true);
					data.setTeamName(name);
					
					String tid=new GetTeamid().doInBackground(userId,info);
					data.setTeamId(tid);
					
					String sid = data.getActId();
					String teamMax = new GetTeamTotal().doInBackground(sid);
					int max = Integer.parseInt(teamMax);
					data.setTeamMax(max);
					
					//new JoinGroup().execute(tid,userId,name,true);
					startActivity(okintent);
				}
				else
					Toast.makeText(BuildTeam.this, "ÇëÌîÐ´.", Toast.LENGTH_SHORT).show();	
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
