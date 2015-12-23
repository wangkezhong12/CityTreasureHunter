package com.activity.joinact;

import java.io.IOException;
import java.io.StringReader;

import com.activity.context.MapApplication;
import com.activity.context.SingleContext;
import com.activity.main.R;
import com.function.joinact.GetActInfo;
import com.function.joinact.GetTeamTotal;
import com.google.gson.stream.JsonReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActInfo extends Activity {
	private ImageButton back;
	private ImageButton join;

	private TextView actname;
	private TextView actstyle;
	private TextView brifintrd;
	private TextView lon;
	private TextView lat;
	private TextView checknum;
	private TextView wrannum;
	private TextView limittime;
	private TextView author;
	private TextView starttime;
	private TextView lasttime;

	private Intent intent;

	String sid;
	//String ns;
	String jsonstr;
	String type;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinact_actinfo);

		SearchAct.joinActActivityList.add(this);

		if (getIntent().getStringExtra("sid") != null)
			sid = getIntent().getStringExtra("sid");
		if (getIntent().getStringExtra("type") != null)
			type = getIntent().getStringExtra("type");
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		data.setActId(sid);
		
		MapSearch ms = new MapSearch();
		GetActInfo gai = new GetActInfo();

		back = (ImageButton) this.findViewById(R.id.back);
		join = (ImageButton) this.findViewById(R.id.join);

		actname = (TextView) this.findViewById(R.id.actname);
		actstyle = (TextView) this.findViewById(R.id.actstyle);
		brifintrd = (TextView) this.findViewById(R.id.brifintrd);
		lon = (TextView) this.findViewById(R.id.lon);
		lat = (TextView) this.findViewById(R.id.lat);
		checknum = (TextView) this.findViewById(R.id.checknum);
		wrannum = (TextView) this.findViewById(R.id.wrannum);
		limittime = (TextView) this.findViewById(R.id.limittime);
		author = (TextView) this.findViewById(R.id.author);
		starttime = (TextView) this.findViewById(R.id.starttime);
		lasttime = (TextView) this.findViewById(R.id.lasttime);
		
		//String teamMax = new GetTeamTotal().doInBackground("1");
		//int max = Integer.parseInt(teamMax);
		//data.setTeamMax(max);
		//Toast.makeText(ActInfo.this, teamMax+"", Toast.LENGTH_SHORT).show();

		
			jsonstr = gai.Getactinfo(sid, type);
			JsonReader reader = new JsonReader(new StringReader(jsonstr));

			try {
				reader.beginObject();
				while (reader.hasNext()) {
					String tagname = reader.nextName();
					if (tagname.equals("s_name")) {
						String name = reader.nextString().toString();
						actname.setText(name);
					}

					else if (tagname.equals("s_type")) {

						// 决定指向
						String nm = reader.nextString().toString();
						if (nm.equals("2")) {
							actstyle.setText("  团体赛");
							
							
							String teamMax = new GetTeamTotal().doInBackground(sid);
							int max = Integer.parseInt(teamMax);
							data.setTeamMax(max);
							
							intent = new Intent(ActInfo.this, Team.class);
						} else if (nm.equals("3")) {
							actstyle.setText("  个人赛");
							intent = new Intent(ActInfo.this, SingleContext.class);
						}
						intent.putExtra("sid", sid);
					} else if (tagname.equals("s_describe")) {
						String describe = reader.nextString().toString();
						brifintrd.setText(describe);
					} else if (tagname.equals("s_lat")) {
						String lats = reader.nextString().toString();
						lon.setText(lats);
					} else if (tagname.equals("s_lon")) {
						String lons = reader.nextString().toString();
						lat.setText(lons);
					} else if (tagname.equals("s_total")) {
						String check = reader.nextString().toString();
						checknum.setText(check);
					} else if (tagname.equals("s_limittime")) {
						String limit = reader.nextString().toString();
						limittime.setText(limit);
						
						String[] time=limit.split(":");
						int h = Integer.parseInt(time[0])*60*60*1000;
						int m = Integer.parseInt(time[1])*60*1000;
						int t = h+m;

						data.setLimitTime(t);
					} else if (tagname.equals("s_starttime")) {
						String start = reader.nextString().toString();
						starttime.setText(start);
					} else if (tagname.equals("s_lasttime")) {
						String last = reader.nextString().toString();
						lasttime.setText(last);
					}
					else if (tagname.equals("u_name")) {
						String name = reader.nextString().toString();
						author.setText(name);
					}

				}
				reader.endObject();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		// intent = new Intent(ActInfo.this,Team.class);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		join.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (type.equals("single")){
					int len = SearchAct.joinActActivityList.size();
					for (int i=0;i<len;i++){						
						if (SearchAct.joinActActivityList.get(i) != null)
							SearchAct.joinActActivityList.get(i).finish();
					}
					SearchAct.joinActActivityList.clear();
				}
				
				startActivity(intent);
			}
		});
	}
}
