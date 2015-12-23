package com.activity.main;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.function.switchscroll.MyScrollLayout;
import com.function.switchscroll.OnViewChangeListener;

public class SwitchView extends Activity implements OnViewChangeListener, OnClickListener{
    /** Called when the activity is first created. */

	private MyScrollLayout mScrollLayout;
	
	private ImageView[] mImageViews;
	
	private int mViewCount;	
	private int mCurSel;
	
	private ImageButton startBtn;
	
	private TextView skip;
	
	private RelativeLayout mainRLayout;
	
	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	private LinearLayout animLayout;
	private LinearLayout linearLayout;
	
	Intent it;
	Timer timer;
	TimerTask task;
	
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preface_scrollpicture);                
        init();
    }
    
    private void init(){
    	startBtn = (ImageButton) findViewById(R.id.startBtn);
		startBtn.setOnClickListener(onClick);
		
		skip = (TextView) findViewById(R.id.skip);
		skip.setOnClickListener(onClick);
		
		mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
		
		leftLayout  = (LinearLayout) findViewById(R.id.leftLayout);
		rightLayout  = (LinearLayout) findViewById(R.id.rightLayout);
		animLayout = (LinearLayout) findViewById(R.id.animLayout);
		linearLayout = (LinearLayout)findViewById(R.id.llayout);
		
    	mScrollLayout = (MyScrollLayout)findViewById(R.id.ScrollLayout); 	
    	   	
    	mViewCount = mScrollLayout.getChildCount();
    	
    	mImageViews = new ImageView[mViewCount];
    	
    	for(int i = 0; i < mViewCount; i++){
    		mImageViews[i] = (ImageView)linearLayout.getChildAt(i);
    		mImageViews[i].setEnabled(true);
    		mImageViews[i].setOnClickListener(this);
    		mImageViews[i].setTag(i);
    	}    	
    	mCurSel = 0;
    	mImageViews[mCurSel].setEnabled(false);    	
    	mScrollLayout.SetOnViewChangeListener(this);
    }

    private void setCurPoint(int index){
    	if (index < 0 || index > mViewCount - 1 || mCurSel == index){
    		return ;
    	}    	
    	mImageViews[mCurSel].setEnabled(true);
    	mImageViews[index].setEnabled(false);    	
    	mCurSel = index;
    }

    @Override
	public void OnViewChange(int view){
		// TODO Auto-generated method stub
		setCurPoint(view);
	}

	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		int pos = (Integer)(v.getTag());
		setCurPoint(pos);
		mScrollLayout.snapToScreen(pos);
	}
	
	private View.OnClickListener onClick = new View.OnClickListener(){
		@Override
		public void onClick(View v){
			final Animation leftOutAnimation = AnimationUtils.loadAnimation(SwitchView.this, R.anim.translate_left);
			final Animation rightOutAnimation = AnimationUtils.loadAnimation(SwitchView.this, R.anim.translate_right);
			switch (v.getId()){
			case R.id.startBtn:
				mScrollLayout.setVisibility(View.GONE);
				linearLayout.setVisibility(View.GONE);
				animLayout.setVisibility(View.VISIBLE);
				mainRLayout.setBackgroundResource(R.drawable.whatsnew_bg);
				leftLayout.startAnimation(leftOutAnimation);
				rightLayout.startAnimation(rightOutAnimation);
				leftOutAnimation.setAnimationListener(new AnimationListener(){
					@SuppressLint("ResourceAsColor")
					@Override
					public void onAnimationStart(Animation animation){
						mainRLayout.setBackgroundColor(R.color.bgColor);
					}
					@Override
					public void onAnimationRepeat(Animation animation){
					}
					@Override
					public void onAnimationEnd(Animation animation){
						leftLayout.setVisibility(View.GONE);
						rightLayout.setVisibility(View.GONE);
					}
				});
				it = new Intent(SwitchView.this, MainInterface.class); //你要转向的Activity  
		        timer = new Timer();
		        task = new TimerTask(){
		        	@Override
		        	public void run() {
		        		startActivity(it); //执行
		        		finish();
		        	}
		        };
		        timer.schedule(task, 3500);
				break;
			case R.id.skip:
				skip.setTextColor(Color.MAGENTA);
				new Handler().postDelayed(new Runnable(){  
				     public void run(){  
				     //execute the task
				    	mScrollLayout.setVisibility(View.GONE);
						linearLayout.setVisibility(View.GONE);
						animLayout.setVisibility(View.VISIBLE);
						mainRLayout.setBackgroundResource(R.drawable.whatsnew_bg);
						leftLayout.startAnimation(leftOutAnimation);
						rightLayout.startAnimation(rightOutAnimation);
						leftOutAnimation.setAnimationListener(new AnimationListener(){
							@SuppressLint("ResourceAsColor")
							@Override
							public void onAnimationStart(Animation animation){
								mainRLayout.setBackgroundColor(R.color.bgColor);
							}
							@Override
							public void onAnimationRepeat(Animation animation){
							}
							@Override
							public void onAnimationEnd(Animation animation){
								leftLayout.setVisibility(View.GONE);
								rightLayout.setVisibility(View.GONE);
							}
						});
				     }  
				  }, 100);
				it = new Intent(SwitchView.this, MainInterface.class); //你要转向的Activity  
		        timer = new Timer();
		        task = new TimerTask(){
		        	@Override
		        	public void run() {
		        		startActivity(it); //执行
		        		finish();
		        	}
		        };
		        timer.schedule(task, 3500);
				break;
			}
		}
	};
}