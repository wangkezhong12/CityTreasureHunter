package com.activity.main;

import com.function.login.Signup;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginRegister extends Activity{
	private EditText accnum;
	private EditText pwd;
	private EditText repwd;
	
	private ImageButton register;
	private ImageButton reset;
	
	private Intent intent;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_register);
		
		accnum = (EditText) findViewById(R.id.AccountNumber);
		pwd = (EditText) findViewById(R.id.Password);
		repwd = (EditText) findViewById(R.id.RePassword);
		
		register = (ImageButton) findViewById(R.id.register);
		reset = (ImageButton) findViewById(R.id.reset);
		
		intent = new Intent(LoginRegister.this,LoginUploadWait.class);
		
		register.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				Signup sign = new Signup();
				
				String userid = accnum.getText().toString();
				String password = pwd.getText().toString();
				String cfpassword = repwd.getText().toString();
				if (password.equals(cfpassword)){
					if(sign.Insert(userid, password, cfpassword)){
						startActivity(intent);
						finish();
					}
					else{
						Toast.makeText(LoginRegister.this, "上传失败!", Toast.LENGTH_SHORT).show();
					}
				}
				else
					Toast.makeText(LoginRegister.this, "请输入正确的密码!", Toast.LENGTH_SHORT).show();
			}
		});
		
		reset.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				accnum.setText("");
				pwd.setText("");
				repwd.setText("");
			}
		});
	}
}
