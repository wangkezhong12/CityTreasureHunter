package com.activity.personal;

import com.activity.context.MapApplication;
import com.activity.main.R;
import com.function.personal.CreatProtection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetProtection extends Activity{
	private TextView question;	
	private TextView changequestion;
	
	private ImageButton back;
	private ImageButton ok;
	
	private EditText answer;
		
	private Intent okintent;
	int i;
	
	String userId;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.personal_setprotection);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		userId = data.getUserId();
		
		question = (TextView) findViewById(R.id.Question);
		
		answer = (EditText) findViewById(R.id.Answer);
		
		changequestion = (TextView) findViewById(R.id.ChangeQuestion);
		back = (ImageButton) findViewById(R.id.Back);
		ok = (ImageButton) findViewById(R.id.OK);
				
		okintent = new Intent(SetProtection.this, DataWait.class);
		question.setText("您父亲的姓名是？");
		
		changequestion.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				changequestion.setTextColor(Color.MAGENTA);
				new Handler().postDelayed(new Runnable(){  
				     public void run(){  
				     //execute the task
				    	 //Toast.makeText(SetProtection.this, "更换问题", Toast.LENGTH_SHORT).show();
				    	 if(question.getText().toString().equals("您父亲的姓名是？"))
							{
								i=1;
							}
							if(question.getText().toString().equals("您母亲的姓名是？"))
							{
								i=2;
							}
							
							if(question.getText().toString().equals("您高中学校的名字是？"))
							{
								i=3;
							}
							switch(i) {  
							case 1:  question.setText("您母亲的姓名是？"); break; 
							case 2:  question.setText("您高中学校的名字是？"); break; 
							case 3:  question.setText("您父亲的姓名是？"); break; 
							default:break; 
							}
				     }  
				  }, 100);
			}
		});
		
		back.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();				
			}
		});
		final CreatProtection creprotection=new CreatProtection();
		ok.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				question = (TextView) findViewById(R.id.Question);
				answer = (EditText) findViewById(R.id.Answer);
				creprotection.execute(question,answer,userId);
				// TODO Auto-generated method stub
				finish();
				startActivity(okintent);				
			}
		});				
	}
}
