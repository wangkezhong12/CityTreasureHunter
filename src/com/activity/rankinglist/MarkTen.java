package com.activity.rankinglist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.activity.main.R;
import com.function.rankinglist.Getrecord;
import com.function.rankinglist.IfOnline;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MarkTen extends Activity{
	private LinearLayout myListLayout;
	private ListView tripListView;
	private ImageButton ok;	
	String sname,sport,sid;
	String isonline;
	List<Map<String,Object>>list = new ArrayList<Map<String,Object>>();
	Getrecord getrecord =new Getrecord();
	String[][] records=null;
	private TextView actname;
	private TextView ison;
	@Override
	public void onCreate(Bundle savedInstanceState){
		//TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		this.setContentView(R.layout.rankinglist_rank);
		
		if(getIntent().getStringExtra("sid") != null) {  
			sid = getIntent().getStringExtra("sid");
			}
		if(getIntent().getStringExtra("sname") != null) {  
			sname = getIntent().getStringExtra("sname");
			
			}
		if(getIntent().getStringExtra("sport") != null) {  
			sport = getIntent().getStringExtra("sport");
			}
		records=getrecord.doInBackground(sid,sport);
		String ifonlinesym=new IfOnline().doInBackground(sid, sport);
		if(ifonlinesym.equals("-1"))
			isonline="已下线";
		if(ifonlinesym.equals("1"))
			isonline="在线";
		myListLayout = (LinearLayout) this.findViewById(R.id.myListView);
		ok = (ImageButton) this.findViewById(R.id.ok);
		actname = (TextView) this.findViewById(R.id.actName);
		actname.setText(sname);
		
		ison = (TextView) this.findViewById(R.id.isOn);
		ison.setText(isonline);
		tripListView = new ListView(this);
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		tripListView.setCacheColorHint(Color.WHITE);
		myListLayout.addView(tripListView,tripListViewParam);
		SimpleAdapter adapter = new SimpleAdapter(this,getTripListData(),R.layout.rankinglist_listview,new String[]{"name","checknum","time"},new int[]{R.id.UserName,R.id.CheckNumber,R.id.Time});
		tripListView.setAdapter(adapter);
		ok.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	Map<String,Object> map;
	
	
	private List<Map<String, Object>> getTripListData() {
		// TODO Auto-generated method stub
		int length;
		String name = null;
		if(records.length>=10)
			length=10;
		else
			length=records.length;
		if(sport.equals("sport2"))
			name="队伍名字：";
		if(sport.equals("sport3"))
			name="用户：";
		for(int i=0;i<length;i++)
		{
			map = new HashMap<String,Object>();
			map.put("name", name.toString()+records[i][0]);
			map.put("checknum", "完成检查点个数："+records[i][1]);
			map.put("time", "用时："+records[i][2]);
			list.add(map);
			
		}
		
		return list;
	}	
}
