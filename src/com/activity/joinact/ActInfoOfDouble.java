package com.activity.joinact;

import java.io.IOException;
import java.io.StringReader;

import com.activity.context.DoubleFinder;
import com.activity.context.MapApplication;
import com.activity.context.SingleContext;
import com.activity.main.R;
import com.function.joinact.GetActInfo;
import com.google.gson.stream.JsonReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActInfoOfDouble extends Activity {
	private ImageButton back;
	private ImageButton join;

	private TextView actname;
	private TextView actstyle;
	private TextView brifintrd;
	private TextView lon;
	private TextView lat;
	private TextView limittime;
	private TextView author;
	private TextView starttime;
	private TextView lasttime;

	private Intent intent;
	String type;
	String sid;
	String ns;
	String jsonstr;

	MapSearch ms = new MapSearch();
	GetActInfo gai = new GetActInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinact_doubleactinfo);

		SearchAct.joinActActivityList.add(this);

		if (getIntent().getStringExtra("sid") != null)
			sid = getIntent().getStringExtra("sid");
		if (getIntent().getStringExtra("type") != null)
			type = getIntent().getStringExtra("type");

		// TODO 数据
		MapApplication data = ((MapApplication) getApplicationContext());
		data.setActId(sid);

		back = (ImageButton) this.findViewById(R.id.back);
		join = (ImageButton) this.findViewById(R.id.join);

		actname = (TextView) this.findViewById(R.id.actname);
		actstyle = (TextView) this.findViewById(R.id.actstyle);
		brifintrd = (TextView) this.findViewById(R.id.brifintrd);
		lon = (TextView) this.findViewById(R.id.lon);
		lat = (TextView) this.findViewById(R.id.lat);
		limittime = (TextView) this.findViewById(R.id.limittime);
		author = (TextView) this.findViewById(R.id.author);
		starttime = (TextView) this.findViewById(R.id.starttime);
		lasttime = (TextView) this.findViewById(R.id.lasttime);
		
		lasttime.setText("1天");
		if(type.equals("double"))
		
		jsonstr = gai.GetactinfoDou(sid, type);
		JsonReader reader = new JsonReader(new StringReader(jsonstr));
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String dogname = reader.nextName();
				if (dogname.equals("s_name")) {
					String name = reader.nextString().toString();
					actname.setText(name);
					data.setActName(name);
				}

				else if (dogname.equals("s_type")) {
					String nm = reader.nextString().toString();
					actstyle.setText("双人互找");
				}

				else if (dogname.equals("s_describe")) {
					String describe = reader.nextString().toString();
					brifintrd.setText(describe);
				} else if (dogname.equals("s_lat")) {
					String lats = reader.nextString().toString();
					lon.setText(lats);
				} else if (dogname.equals("s_lon")) {
					String lons = reader.nextString().toString();
					lat.setText(lons);
				}
				else if (dogname.equals("u_name")) {
					String name = reader.nextString().toString();
					author.setText(name);
				}

				else if (dogname.equals("s_starttime")) {
					String start = reader.nextString().toString();
					starttime.setText(start);
				} 
				else if (dogname.equals("s_limittime")) {
					String limit = reader.nextString().toString();
					
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
				int len = SearchAct.joinActActivityList.size();
				for (int i = 0; i < len; i++) {
					if (SearchAct.joinActActivityList.get(i) != null)
						SearchAct.joinActActivityList.get(i).finish();
				}
				// SearchAct.joinActActivityList.clear();
				intent = new Intent(ActInfoOfDouble.this, DoubleFinder.class);
				startActivity(intent);
			}
		});
	}
}
