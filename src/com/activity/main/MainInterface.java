package com.activity.main;

import com.activity.buildact.BuildInformation;
import com.activity.joinact.PracticeSearchAct;
import com.activity.joinact.SearchAct;
import com.activity.personal.PersonAct;
import com.activity.rankinglist.RankAct;
import com.activity.support.SupportMainSelection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

public class MainInterface extends Activity{
	private ImageButton build;
	private ImageButton join;
	private ImageButton practise;
	private ImageButton rank;
	private ImageButton person;
	private ImageButton technology;
	private ImageButton quit;
	
	private Intent buildintent;
	private Intent joinintent;
	private Intent practiseintent;
	private Intent rankintent;
	private Intent personintent;
	private Intent technologyintent;
	private Intent quitintent;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_interface);
		
		build = (ImageButton) findViewById(R.id.Build);
		join = (ImageButton) findViewById(R.id.Join);
		practise = (ImageButton) findViewById(R.id.Practise);
		rank = (ImageButton) findViewById(R.id.Rank);
		person = (ImageButton) findViewById(R.id.Person);
		technology = (ImageButton) findViewById(R.id.Technology);
		quit = (ImageButton) findViewById(R.id.Quit);
		
		// TODO 界面跳转
		practiseintent = new Intent(MainInterface.this,PracticeSearchAct.class);
		buildintent = new Intent(MainInterface.this,BuildInformation.class);
		joinintent = new Intent(MainInterface.this,SearchAct.class);
		rankintent = new Intent(MainInterface.this,RankAct.class);
		personintent = new Intent(MainInterface.this,PersonAct.class);
		technologyintent = new Intent(MainInterface.this,SupportMainSelection.class);
		quitintent = new Intent(MainInterface.this,QuitAct.class);
		
		
		build.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(buildintent);				
			}
		});
		
		join.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(joinintent);				
			}
		});
		
		practise.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(practiseintent);				
			}
		});
		
		rank.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(rankintent);				
			}
		});
		
		person.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(personintent);				
			}
		});
		
		technology.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				startActivity(technologyintent);				
			}
		});
		
		quit.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainInterface.this)
				//.setIcon(R.drawable.icon)
				.setTitle("退出程序")
				.setMessage("确定要退出吗？")				
				.setPositiveButton("确定",new DialogInterface.OnClickListener(){					
					@Override
					public void onClick(DialogInterface dialog, int which){
						// TODO Auto-generated method stub
						setResult(RESULT_OK);
						startActivity(quitintent);
						finish();						
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener(){					
					@Override
					public void onClick(DialogInterface dialog, int which){
						// TODO Auto-generated method stub						
					}
				}).show();							
			}
		});
	}
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // 按下键盘上返回按钮   
	    if (keyCode == KeyEvent.KEYCODE_BACK){
	    	new AlertDialog.Builder(this)
	    	.setTitle("确认退出")
	    	.setMessage("您确定要退出吗？") 
	    	.setNegativeButton("取消",null)
	    	.setPositiveButton("确定",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(MainInterface.this, QuitAct.class);
					startActivity(intent);
					finish();
				}    		
	    	}).show();
	    }
		return true;
	}
}

