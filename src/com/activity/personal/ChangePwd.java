package com.activity.personal;

import com.activity.context.MapApplication;
import com.activity.main.R;
import com.function.personal.ChangePasswd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangePwd extends Activity{
	private EditText pastpwd;
	private EditText changepwd;
	private EditText rewritepwd;
	
	private ImageButton ok;
	private ImageButton back;
		
	private Intent okintent;
	
	private String userId;
	
	ChangePasswd cp = new ChangePasswd();
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.personal_changepassword);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		userId = data.getUserId();
		
		pastpwd = (EditText) findViewById(R.id.PastPwd);		
		changepwd = (EditText) findViewById(R.id.ChangePwd);
		rewritepwd = (EditText) findViewById(R.id.RewritePwd);
				
		back = (ImageButton) findViewById(R.id.Back);
		ok = (ImageButton) findViewById(R.id.OK);
				
		okintent = new Intent(ChangePwd.this, DataWait.class);
		
	
		back.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		ok.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				String opasswd = pastpwd.getText().toString();
				String newpasswd = changepwd.getText().toString();
				String renewpasswd = rewritepwd.getText().toString();
				if(!opasswd.equals("")){
					if(!newpasswd.equals(renewpasswd)){
					Toast.makeText(ChangePwd.this, "请输入相同的新密码！", Toast.LENGTH_LONG).show();
				}
				else if(cp.GetChanged(userId, opasswd, newpasswd)){
					
					Toast.makeText(ChangePwd.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
					startActivity(okintent);
					finish();
				}
				else{
					Toast.makeText(ChangePwd.this, "密码错误！", Toast.LENGTH_SHORT).show();
				}
				}
				
				
						
				
				
			}
		});				
	}
}
