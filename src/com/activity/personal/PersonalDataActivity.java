package com.activity.personal;

import java.io.IOException;
import java.io.StringReader;

import com.activity.context.MapApplication;
import com.activity.main.Login;
import com.activity.main.R;
import com.function.personal.AddPerInfo;
import com.function.personal.GetPerInfo;
import com.google.gson.stream.JsonReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class PersonalDataActivity extends Activity{
	private ImageView save;
	private ImageView write;
	private ImageView back;
	
	private RadioButton rb;
	private RadioButton rbf;
	
	private EditText yearb;
	private EditText year;
	private EditText month;
	private EditText day;
	private EditText location;
	private EditText signature;
	
	private LinearLayout progress;
	String sexs;
	
	private String userId;
	AddPerInfo addper = new AddPerInfo();
	GetPerInfo gpi = new GetPerInfo();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_data);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		userId = data.getUserId();
		Toast.makeText(PersonalDataActivity.this, userId+"", Toast.LENGTH_SHORT).show();
		
		save = (ImageView) findViewById(R.id.Save);
		write = (ImageView) findViewById(R.id.Write);
		back = (ImageView) findViewById(R.id.Back);
		
		rb = (RadioButton) findViewById(R.id.male);
		rbf = (RadioButton) findViewById(R.id.female);
		
		yearb = (EditText) findViewById(R.id.Yearb);
		year = (EditText) findViewById(R.id.Year);
		month = (EditText) findViewById(R.id.Month);
		day = (EditText) findViewById(R.id.Day);
		location = (EditText) findViewById(R.id.Location);
		signature = (EditText) findViewById(R.id.Signature);
		
		rb.setEnabled(false);
		rbf.setEnabled(false);
		
		year.setEnabled(false);
		year.setEnabled(false);
		month.setEnabled(false);
		day.setEnabled(false);
		signature.setEnabled(false);
		location.setEnabled(false);

		String jsonstr = gpi.GetPerInfos(userId);
		if(jsonstr.equals("")){
			rb.setEnabled(true);
			rbf.setEnabled(true);
			
			year.setEnabled(true);
			month.setEnabled(true);
			day.setEnabled(true);
			
			signature.setEnabled(true);
			location.setEnabled(true);
		}
		else{
			System.out.println(jsonstr);
			JsonReader reader = new JsonReader(new StringReader(jsonstr));
			try {
				reader.beginArray();
				while(reader.hasNext()){
						reader.beginObject();
				while(reader.hasNext()){
					String tagname = reader.nextName();
					
					if(tagname.equals("u_sex")){
						String sex = reader.nextString();
						if (sex.equals("男"))
							rb.setChecked(true);
						else if (sex.equals("女"))
							rbf.setChecked(true);
						}
					else if(tagname.equals("u_birthinfo")){
						String birthinfo  = reader.nextString();
						if(!birthinfo.equals("")){
							String[] ymd = birthinfo.split("-");
							year.setText(ymd[0]);
							month.setText(ymd[1]);
							day.setText(ymd[2]);
						}
						
						
					}
					else if(tagname.equals("u_location")){
						location.setText(reader.nextString()); 
					}
					else if(tagname.equals("u_signature")){
						signature.setText(reader.nextString()); 
					}
				}	reader.endObject();
				}
				reader.endArray();
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		progress = (LinearLayout) findViewById(R.id.progress);
		
		save.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub		
				//String sexs = sex.getText().toString();
				String years =  year.getText().toString();
				if (years.equals(""))
					years = "1900";
				String months = month.getText().toString();
				if (months.equals(""))
					months = "1";
				String days = day.getText().toString();
				if (days.equals(""))
					days = "1";
				//Toast.makeText(PersonalDataActivity.this, days+"1"+months, Toast.LENGTH_SHORT).show();
				
				String birthday = years+"-"+months+"-"+days; 
				String address = location.getText().toString();
				if (address.equals(""))
					address = " ";
				String qianming = signature.getText().toString(); 
				if (qianming.equals(""))
					qianming = " ";
				if (sexs == null || sexs.equals(""))
					sexs = " ";
				
				int y = Integer.parseInt(years);
				int m = Integer.parseInt(months);
				int d = Integer.parseInt(days);
				if (y >= 1900 && m>0 && m<13 && d>0 && d<32){
					if(addper.isAdd(userId, sexs, birthday, address, qianming)){
						Toast.makeText(PersonalDataActivity.this, "信息已保存！", Toast.LENGTH_LONG).show();
					}
					else{
						Toast.makeText(PersonalDataActivity.this, "信息保存失败！", Toast.LENGTH_LONG).show();
					}
				}
				else
					Toast.makeText(PersonalDataActivity.this, "日期格式不正确！", Toast.LENGTH_LONG).show();
			}
		});
		
		write.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub	
			
				//sex.setEnabled(true);
				rb.setEnabled(true);
				rbf.setEnabled(true);
				
				year.setEnabled(true);
				month.setEnabled(true);
				day.setEnabled(true);
				
				signature.setEnabled(true);
				location.setEnabled(true);
			}
		});
		
		back.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				finish();				
			}
		});
		
		// TODO 监听
		rb.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sexs = "男";
				//Toast.makeText(PersonalDataActivity.this, "性别：男", Toast.LENGTH_SHORT).show();
			}
		});	
		
		rbf.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				sexs = "女";
				//Toast.makeText(PersonalDataActivity.this, "性别：女", Toast.LENGTH_SHORT).show();
			}
		});	
	}
}
