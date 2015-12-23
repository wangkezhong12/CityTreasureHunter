package com.activity.personal;

import com.activity.main.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class PersonAct extends TabActivity implements OnCheckedChangeListener{
	
	private TabHost tabhost;
	
	private Intent perdataintent;
	private Intent accsafeintent;
	private Intent useractintent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personal_maintabs);
        
        this.perdataintent = new Intent(this,PersonalDataActivity.class);
        this.accsafeintent = new Intent(this,AccNumSafetyActivity.class);
        this.useractintent = new Intent(this,UserActivityActivity.class);
        
		((RadioButton) findViewById(R.id.PersonalData))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.AccNumSafety))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.UserActivity))
		.setOnCheckedChangeListener(this);
        
        setupIntent();
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
		if(isChecked){
			switch (buttonView.getId()){
			case R.id.PersonalData:
				this.tabhost.setCurrentTabByTag("perdata_TAB");
				break;
			case R.id.AccNumSafety:
				this.tabhost.setCurrentTabByTag("accsafe_TAB");
				break;
			case R.id.UserActivity:
				this.tabhost.setCurrentTabByTag("useract_TAB");
				break;
			}
		}
		
	}
	
	private void setupIntent(){
		this.tabhost = getTabHost();
		TabHost localTabHost = this.tabhost;

		localTabHost.addTab(buildTabSpec("perdata_TAB", R.string.personaldata, R.drawable.gr1, this.perdataintent));

		localTabHost.addTab(buildTabSpec("accsafe_TAB", R.string.accnumsafety, R.drawable.aq, this.accsafeintent));

		localTabHost.addTab(buildTabSpec("useract_TAB", R.string.useractivity, R.drawable.hd, this.useractintent));
	}
	
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon, final Intent content){
		return this.tabhost.newTabSpec(tag).setIndicator(getString(resLabel), getResources().getDrawable(resIcon)).setContent(content);
	}
}