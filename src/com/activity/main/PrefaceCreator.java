package com.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PrefaceCreator extends Activity implements AnimationListener{
	private ImageView imageView = null;
	private Animation alphaAnimation = null;
	
	@Override
	protected void onCreate(Bundle saveInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(saveInstanceState);
		setContentView(R.layout.preface_creator);
		imageView = (ImageView)findViewById(R.id.introduce_image);
		alphaAnimation = AnimationUtils.loadAnimation(this,R.anim.introduce_alpha);
		alphaAnimation.setFillEnabled(true);
		alphaAnimation.setFillAfter(true);
		imageView.setAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(this);
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		this.finish();		
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub		
	}
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			return false;
		}
		return false;
	}
}
