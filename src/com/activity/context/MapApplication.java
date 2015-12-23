package com.activity.context;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class MapApplication extends Application{
	private String userId;
	private String username;
	private String actId;
	private String actName;
	private int limitTime;
	private String teamId;
	private String teamName;
	private int teamMax;
	private boolean isHead;

	private static MapApplication mInstance;
    public boolean m_bKeyRight = true;
    public BMapManager mBMapManager;
    
    public static final String strKey = "4B2EBC226F68A4BEC54B0E7E0C6D65EB5FB0809D";
 
    @Override
    public void onCreate(){
    	super.onCreate();
    	mInstance = this;
    	initEngineManager(this);
    }
    
    @Override
	//建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapManager != null) {
			mBMapManager.destroy();
			mBMapManager = null;
		}
		super.onTerminate();
	}
    
    // 状态反馈
    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if ( !mBMapManager.init(strKey,new MyGeneralListener()) ) {
            Toast.makeText(MapApplication.getInstance().getApplicationContext(), 
                    	   "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
  
    public static MapApplication getInstance(){
		return mInstance;
	}
    
    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
 	// 该接口返回网络状态，授权验证等结果，用户需要实现该接口以处理相应事件
    public static class MyGeneralListener implements MKGeneralListener{
		
		@Override
		public void onGetNetworkState(int error){
			if (error == MKEvent.ERROR_NETWORK_CONNECT){
                Toast.makeText(MapApplication.getInstance().getApplicationContext(), "您的网络出错啦！",
                    Toast.LENGTH_LONG).show();
            }
            else if (error == MKEvent.ERROR_NETWORK_DATA){
                Toast.makeText(MapApplication.getInstance().getApplicationContext(), "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
            else if (error == MKEvent.ERROR_PERMISSION_DENIED){
            }
			// .....
		}

		@Override
		public void onGetPermissionState(int error) {
			if (error ==  MKEvent.ERROR_PERMISSION_DENIED) {
                //授权Key错误：
                Toast.makeText(MapApplication.getInstance().getApplicationContext(), 
                        	   "请在 MapApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
                MapApplication.getInstance().m_bKeyRight = false;
            }
		}
    }
    
    // TODO 全局存储返回
    public void setUserId(String id){
    	userId = id;
    }
    
    public String getUserId(){
    	return userId;
    }
    
    public void setUserName(String name){
    	username = name;
    }
    
    public String getUserName(){
    	return username;
    }
    
    public void setActId(String id){
    	actId = id;
    }
    
    public String getActId(){
    	return actId;
    }
    
    public void setActName(String name){
    	actName = name;
    }
    
    public String getActName(){
    	return actName;
    }
    
    public void setTeamId(String id){
    	teamId = id;
    }
    
    public String getTeamId(){
    	return teamId;
    }
    
    public void setTeamName(String name){
    	teamName = name;
    }
    
    public String getTeamName(){
    	return teamName;
    }
    
    public void setIsHead(boolean b){
    	isHead = b;
    }
    
    public boolean getIsHead(){
    	return isHead;
    } 
    
    public void setTeamMax(int num){
    	teamMax = num;
    }
    
    public int getTeamMax(){
    	return teamMax;
    }
    
    public void setLimitTime(int time){
    	limitTime = time;
    }
    
    public int getLimitTime(){
    	return limitTime;
    }
}
