package com.activity.joinact;

import com.activity.context.MapApplication;
import com.activity.context.SinglePractice;
import com.activity.main.R;
import com.function.joinact.Getofflinedpersonal;
import com.function.joinact.Getpractactinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PracticeActInfo extends Activity {
	private ImageButton back;
	private ImageButton join;

	String sname, sid;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinact_practiceactinfo);
		
		PracticeSearchAct.practiceActivityList.add(this);

		if (getIntent().getStringExtra("sid") != null) {
			sid = getIntent().getStringExtra("sid");
		}
		if (getIntent().getStringExtra("sname") != null) {
			sname = getIntent().getStringExtra("sname");
		}
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		data.setActId(sid);
		data.setActName(sname);

		String[] practactinfo = new Getpractactinfo().doInBackground(sid
				.toString());
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

		actname.setText(sname.toString() + "  ");
		actstyle.setText("已下线的单人活动");
		brifintrd.setText(practactinfo[7].toString() + "  ");
		lon.setText(practactinfo[2].toString() + "  ");
		lat.setText(practactinfo[1].toString() + "  ");
		checknum.setText(practactinfo[6].toString() + "  ");
		wrannum.setText(practactinfo[8].toString() + "  ");
		limittime.setText(practactinfo[5].toString() + "  ");
		author.setText(practactinfo[0].toString() + "  ");
		starttime.setText(practactinfo[3].toString() + "  ");
		lasttime.setText(practactinfo[4].toString() + "  ");

		intent = new Intent(PracticeActInfo.this, SinglePractice.class);

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
				int len = PracticeSearchAct.practiceActivityList.size();
				for (int i=0;i<len;i++){
					if (PracticeSearchAct.practiceActivityList.get(i) != null)
						PracticeSearchAct.practiceActivityList.get(i).finish();
				}
				intent.putExtra("actId", sid);
				
				startActivity(intent);
			}
		});
	}
}
