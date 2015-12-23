package com.activity.joinact;

import com.activity.context.Group;
import com.activity.context.MapApplication;
import com.activity.main.R;
import com.function.joinact.GetTeamDescribe;
import com.function.joinact.GetTeamMemberInfo;
import com.function.joinact.GetTeamTotal;
import com.function.joinact.Getpractactinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TeamInfo extends Activity{
	private TextView teamname;
	private TextView author;
	private TextView member;
	private TextView teamdescription;
	
	private ImageButton back;
	private ImageButton join;
	
	private Intent intent;
	String tid,tnamein;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinact_teaminfo);
		
		SearchAct.joinActActivityList.add(this);
		
		if (getIntent().getStringExtra("tid") != null) {
			tid = getIntent().getStringExtra("tid");
		}
		if (getIntent().getStringExtra("tname") != null) {
			tnamein = getIntent().getStringExtra("tname");
		}
		
		MapApplication data = ((MapApplication)getApplicationContext());
		data.setTeamId(tid);
		data.setTeamName(tnamein);
        data.setIsHead(false);
		
		String[] teammemberinfo = new GetTeamMemberInfo().doInBackground(tid.toString());
		
		teamname = (TextView) this.findViewById(R.id.teamname);
		author = (TextView) this.findViewById(R.id.author);
		member = (TextView) this.findViewById(R.id.teammember);
		teamdescription = (TextView) this.findViewById(R.id.teamdescription);
		
		
		back = (ImageButton) this.findViewById(R.id.back);
		join = (ImageButton) this.findViewById(R.id.join);
		
		
		intent = new Intent(TeamInfo.this,Group.class);
		
		teamname.setText(tnamein.toString());
		author.setText(teammemberinfo[0].toString());
		
		String description = new GetTeamDescribe().doInBackground(tid);
		teamdescription.setText(description);
		
		
		
		for(int i= 1;i<teammemberinfo.length;i++)
			member.append(teammemberinfo[i]+"\n");
		//int teamMax = Integer.parseInt(teammemberinfo[i]);
		
		back.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		join.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				int len = SearchAct.joinActActivityList.size();
				for (int i=0;i<len;i++){
					if (SearchAct.joinActActivityList.get(i) != null)
						SearchAct.joinActActivityList.get(i).finish();
				}
				SearchAct.joinActActivityList.clear();
				
				startActivity(intent);
			}
		});
	}
}
