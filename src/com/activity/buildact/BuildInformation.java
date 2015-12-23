package com.activity.buildact;

import java.util.ArrayList;
import java.util.List;

import com.activity.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


public class BuildInformation extends Activity{
	
	public static List<Activity> buildActivityList = new ArrayList<Activity>();
	
	private Spinner mySpinner;
	private EditText edit;
	
	private ImageButton last;
	private ImageButton next;
	private ImageButton reset;
	
	private Intent intent;
	
	private EditText activityname;
	private EditText lasttime;
	private EditText limittime;
	private EditText brifintroduction;
	private EditText limitTimemin;
	private EditText limitTimeh;
	
	private String sactivityname;
	private String slasttime;
	private String slimittime;
	private String sbrifintroduction;
	private String pro;
	private String peoplenumber;
	private String slimitTimemin;
	private String slimitTimeh;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.build_information);
		
		buildActivityList.add(this);
		
		mySpinner = (Spinner)this.findViewById(R.id.mySpinner);		
		edit = (EditText)this.findViewById(R.id.EditText001);
		
		last = (ImageButton)this.findViewById(R.id.Last);
		next = (ImageButton)this.findViewById(R.id.Next);
		reset = (ImageButton) findViewById(R.id.Reset);
		
		activityname = (EditText)this.findViewById(R.id.ActivityName);
		lasttime = (EditText)this.findViewById(R.id.LastTime);
		//limittime = (EditText)this.findViewById(R.id.LimitTime);
		brifintroduction = (EditText)this.findViewById(R.id.BrifIntroduction);
		limitTimemin= (EditText)this.findViewById(R.id.LimitTimemin);
		limitTimeh= (EditText)this.findViewById(R.id.LimitTimeh);
		
		intent = new Intent(BuildInformation.this, BuildSetPoi.class);
		
		ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
		mySpinner.setAdapter(adapter);
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener(){			
			@Override
			public void onNothingSelected(AdapterView<?> arg0){
				//TODO Auto-generated method stub			
			}
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
				// TODO Auto-generated method stub			
				Spinner spinner = (Spinner) parent;
				pro = (String) spinner.getItemAtPosition(position);
				if (pro.equals("团体赛")){													        
			        edit.setVisibility(View.VISIBLE);
			        lasttime.setText("");
			        lasttime.setEnabled(true);
				}
				else if(pro.equals("双人互找")){
					edit.setVisibility(View.INVISIBLE);
					lasttime.setText("1");
			        lasttime.setEnabled(false);
			        //limitTimemin.setText("0");
			        //limitTimemin.setEnabled(false);
			        //limitTimeh.setText("2");
			        //limitTimeh.setEnabled(false);
				}
				
				else{					
					edit.setVisibility(View.INVISIBLE);
					lasttime.setText("");
			        lasttime.setEnabled(true);					
				}
			}
		});
		
		last.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//activityname.
				
			}
			
		});
		
		next.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				if(activityname.getText().toString().trim().equals("")||
						limitTimemin.getText().toString().trim().equals("")||
						limitTimeh.getText().toString().trim().equals("")||
						lasttime.getText().toString().trim().equals("")||
						brifintroduction.getText().toString().trim().equals("")||
						pro.equals("-请选择-")||(pro.equals("团体赛")&&edit.getText().toString().trim().equals("")))
						Toast.makeText(BuildInformation.this, "请填写", Toast.LENGTH_SHORT).show(); 
				else{
					if(Integer.valueOf(limitTimemin.getText().toString().trim())<0
							||Integer.valueOf(limitTimemin.getText().toString().trim())>60
							||Integer.valueOf(lasttime.getText().toString().trim())>30
							||Integer.valueOf(lasttime.getText().toString().trim())<0
							
							||(pro.equals("团体赛")&&(Integer.valueOf(edit.getText().toString().trim())<3
							||Integer.valueOf(edit.getText().toString().trim())>5))
							)
						Toast.makeText(BuildInformation.this, "持续时间应为1-30天之间,团队人数应为3-5人,限制分钟数应小于60", Toast.LENGTH_SHORT).show();
					else{
					
					sactivityname=activityname.getText().toString().trim();
					slasttime=lasttime.getText().toString().trim();
					slimitTimemin=limitTimemin.getText().toString().trim();
					slimitTimeh=limitTimeh.getText().toString().trim();
					sbrifintroduction=brifintroduction.getText().toString().trim();
					peoplenumber=edit.getText().toString().trim();
					Bundle bundle=new Bundle();
					bundle.putSerializable("sactivityname", sactivityname);
					bundle.putSerializable("slasttime", slasttime);
					bundle.putSerializable("slimitTimemin",slimitTimemin);
					bundle.putSerializable("slimitTimeh",slimitTimeh);
					bundle.putSerializable("sbrifintroduction", sbrifintroduction);
					bundle.putSerializable("pro", pro);
					bundle.putSerializable("peoplenumber", peoplenumber);
					intent.putExtras(bundle);
					startActivity(intent);
					}
					
				}			
			}
		});
	}
}