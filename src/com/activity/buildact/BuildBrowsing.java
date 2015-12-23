package com.activity.buildact;

import java.util.ArrayList;

import com.activity.context.MapApplication;
import com.activity.main.R;
import com.baidu.mapapi.map.OverlayItem;
import com.function.buildact.CreatActivity;
import com.function.buildact.CreatCheckpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class BuildBrowsing extends Activity{
	private TextView activityname;
	private TextView activitystyle;
	private TextView brifintroduction;
	private TextView headinformation;	
	private TextView checknumber;
	private TextView checkinformation;
	private TextView warnnumber;
	private TextView warninformation;
	private TextView limittimeh;
	private TextView limittimem;
	private TextView lasttime;
	private TextView limittime;
	
	private ImageButton mback;
	private ImageButton mok;
	
	private Intent okintent;
	ArrayList<OverlayItem> overlayItems;
	private Intent backintent;
	
	ArrayList<String> question;//检查点问题
	ArrayList<String> answer;//检查点答案
	ArrayList<String> warning;//警告点信息
	

	private String sactivityname;//活动名称
	private String slasttime;//持续时间
	
	private String sbrifintroduction;//多动简介
	private String pro;//活动类型
	private String peoplenumber;//团体活动人数

	private String slimitTimemin;
	private String slimitTimeh;
	
	ArrayList<String> checkpointLongitude;//检查点经度
	ArrayList<String> checkpointLatitude;//检查点纬度
	ArrayList<String> warningpointLongitude;//警告点经度
	ArrayList<String> warningpointLatitude;//警告点纬度
	String startLongitude;//起点经度,若为双人护照活动，则为活动中心点
	String startLatitude;//起点纬度,若为双人护照活动，则为活动中心点
	
	StringBuffer checkinfo;
	StringBuffer warninginfo;
	
	String userId;
	
 	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.build_browsing);
		
		BuildInformation.buildActivityList.add(this);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		userId = data.getUserId();
		//Toast.makeText(BuildBrowsing.this, userId+"", Toast.LENGTH_SHORT).show();
		
		activityname = (TextView) findViewById(R.id.ActivityName);
		activitystyle = (TextView) findViewById(R.id.ActivityStyle);
		brifintroduction = (TextView) findViewById(R.id.BrifIntroduction);
		headinformation = (TextView) findViewById(R.id.HeadInformation);
		checknumber = (TextView) findViewById(R.id.CheckNumber);
		checkinformation = (TextView) findViewById(R.id.CheckInformation);
		warnnumber = (TextView) findViewById(R.id.WarnNumber);
		warninformation = (TextView) findViewById(R.id.WarnInformation);
		limittime= (TextView) findViewById(R.id.LimitTime);
		//limittimem = (TextView) findViewById(R.id.LimitTimemin);
		lasttime = (TextView) findViewById(R.id.LastTime);
		
		mok = (ImageButton) findViewById(R.id.mOK);
		mback = (ImageButton) findViewById(R.id.mBack);
		question=new ArrayList<String>();
		answer=new ArrayList<String>();
		warning=new ArrayList<String>();
		
		checkpointLongitude=new ArrayList<String>();
		checkpointLatitude=new ArrayList<String>();
		warningpointLongitude=new ArrayList<String>();
		warningpointLatitude=new ArrayList<String>();
		

		
		
        Bundle bundle=this.getIntent().getExtras();
		sactivityname=(String) bundle.getSerializable("sactivityname");
		slasttime=(String) bundle.getSerializable("slasttime");
		slimitTimemin=(String) bundle.getSerializable("slimitTimemin");
		slimitTimeh=(String) bundle.getSerializable("slimitTimeh");
		sbrifintroduction=(String) bundle.getSerializable("sbrifintroduction");
		pro=(String) bundle.getSerializable("pro");
		peoplenumber=(String) bundle.getSerializable("peoplenumber");
		
		startLongitude=(String) bundle.getSerializable("startLongitude");
		startLatitude=(String) bundle.getSerializable("startLatitude");
		
        
		
		answer=(ArrayList<String>) bundle.getSerializable("answer");
		question=(ArrayList<String>) bundle.getSerializable("question");
		warning=(ArrayList<String>) bundle.getSerializable("warning");
		
		checkpointLongitude=(ArrayList<String>) bundle.getSerializable("checkpointLongitude");
		checkpointLatitude=(ArrayList<String>) bundle.getSerializable("checkpointLatitude");
		warningpointLongitude=(ArrayList<String>) bundle.getSerializable("warningpointLongitude");
		warningpointLatitude=(ArrayList<String>) bundle.getSerializable("warningpointLatitude");
		
		
		activityname.setText(sactivityname);
		brifintroduction.setText(sbrifintroduction);
		headinformation.setText("经度： "+startLatitude+"\n"+"  纬度： "+startLongitude);
		//limittimeh.setText(slimitTimeh);
		//limittimem.setText(slimitTimemin);
		limittime.setText(slimitTimeh+" h "+slimitTimemin+" min ");
		lasttime.setText(slasttime);
		
		checknumber.setText(question.size()+" ");
		if(pro.equals("团体活动"))activitystyle.setText(pro+"(团体人数："+peoplenumber+")");
		else activitystyle.setText(pro);
		
		checkinfo = new StringBuffer();
		for(int i = 0; i < question.size(); i++){
	     checkinfo. append("第"+(i+1)+"个检查点"+"\n（经度： "+checkpointLongitude.get(i)+"  纬度： "+checkpointLatitude.get(i)+"）"+"\n");
		 checkinfo. append("问题"+" :  "+question.get(i)+"\n");
		 checkinfo. append("答案"+" :  "+answer.get(i)+"\n");
		}
		checkinformation.setText(checkinfo);
	
		warnnumber.setText(warning.size()+" ");
		warninginfo = new StringBuffer();
		for(int i = 0; i < warning.size(); i++){
			checkinfo. append("第"+(i+1)+"个警告点"+"\n（经度： "+warningpointLongitude.get(i)+"  纬度： "+warningpointLatitude.get(i)+"）"+"\n");
			warninginfo. append("警告"+(i+1)+"内容: "+warning.get(i)+"\n");
		}
		warninformation.setText(warninginfo);
		
		okintent = new Intent(BuildBrowsing.this, BuildUpload.class);
		
		mok.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				if(pro.equals("双人互找"))
				{
					//slimitTimeh="00";
					//slimitTimemin="00";
					peoplenumber="2";
					
				}
				if(pro.equals("单人赛"))
				{
					peoplenumber="1";
				}
				// TODO 传用户ID
				String symbolcre=new CreatActivity().doInBackground(pro,sactivityname,userId,startLatitude,startLongitude,
						slasttime,slimitTimeh,slimitTimemin,question.size()+"",sbrifintroduction,peoplenumber);
				for(int i = 0; i < question.size(); i++){
					new CreatCheckpoint().doInBackground(pro,question.get(i),answer.get(i),checkpointLatitude.get(i),checkpointLongitude.get(i));
					}
				for(int i = 0; i < warning.size(); i++){
					new CreatCheckpoint().doInBackground(pro,warning.get(i),"-1",warningpointLatitude.get(i),warningpointLongitude.get(i));
				}
				
				okintent.putExtra("symbol",symbolcre.toString());
				okintent.putExtra("lat", startLatitude);
				okintent.putExtra("lon", startLongitude);
				okintent.putExtra("type", pro);
				
				startActivity(okintent);
				finish(); 
			}
		});
		
		mback.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();				
			}
		});
	}
}
