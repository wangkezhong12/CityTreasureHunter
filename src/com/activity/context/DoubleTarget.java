package com.activity.context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.activity.joinact.SearchAct;
import com.activity.main.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.baidu.platform.comapi.map.Projection;
import com.function.UrlIP.UrlIP;
import com.function.doubletarget.DoubleTrails;
import com.function.doubletarget.ExitDoubleGame;
import com.function.doubletarget.GetDoubleExit;
import com.function.doubletarget.GetDoubleInfo;
import com.function.doubletarget.GetDoubleLatLon;
import com.function.doubletarget.GetUsedPrivileges;
import com.function.doubletarget.JoinDouble;
import com.function.doubletarget.StartDoubleGame;
import com.function.doubletarget.UsePrivileges;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class DoubleTarget extends Activity implements Runnable{
	private MapView mMapView;
	private MapController mMapController;
	
	private LocationClient locationClient;
	LocationData locData;	
	private static final int UPDATE_TIME = 2000;	
	private int locationControl = 1;
	
	int myWriteIn = 0;
	int finWriteIn = 0;
	
	//轨迹、模糊图层
	GraphicsOverlay myPathOverlay;
	GraphicsOverlay targetPathOverlay;

	//目标图层
	FinderPoiOverlay finderPoiOverlay;
	
	//定位、趋紧警告图层
	MyLocationOverlay mLocationOverlay;
	LocationListener mLocationListener;
	MKMapViewListener mMapListener;
	BDNotifyListener mNotifyListner;	
	
	//传感器	
	SensorManager manager;
	Sensor sensor;	
	float angle;

	boolean isStart = false;
	boolean isOver = false;
	
	Button startAndEnd;
	Button save;
	private Button prompt;
	private Button prompta;
	private Button promptb;
	private Button promptc;
	PopupWindow popupWindow;
	private LinearLayout isshow;
	boolean flag = false;
	
	// 聊天室
	private EditText historyEdit; 
	private EditText messageEdit;
	private Button sendButton;
	private String ip = new UrlIP().getIP();
	String chat_txt,chat_in;
	private static final int PORT = 8521;	
	Socket socket;
	Thread thread;
	DataInputStream in;
	DataOutputStream out;
	boolean sender = false;		//控制发送者不震动，接受者震动
	
	private PopupWindow m_popupWindow;
	View contentView;
	
	//双人互找
	Handler handler;
	Handler chanceHappenHandler;
	Timer showFinderTimer;
	Runnable getChanceRun;
	Runnable startContexRun;		//检查是否达到比赛人数
	Runnable exitRun;				//退出检查
	Runnable endContextRun;			//比赛倒计时
	Runnable recoverChan1;
	Runnable recoverChan2;
	Runnable recoverChan3;
	Runnable showFinderRun;
	Runnable getFinderPoiRun;
	
	private final int showFinderTime = 1000*20;		//显示找方时间（MS）	
		
	// 双方位置
	ArrayList<GeoPoint> myLocPoi = new ArrayList<GeoPoint>();
	ArrayList<GeoPoint> finderLocPoi = new ArrayList<GeoPoint>();
	String finderName;
	
	//机会次数
	int tarchan1 = 1;
	int tarchan2 = 1;
	int tarchan3 = 2;
	int tarmax1 = 1;
	int tarmax2 = 1;
	int tarmax3 = 2;	
		
	// 玩家信息
	String userId; 
	private String username;
	String actId;
	int channel;				//频道
	//int team;
	
	final String type = "double";
	final int DURING_TIME = 2*60*60*1000;
	
	int targetChan1 = 1;
	int targetChan2 = 2;
	int targetChan3 = 3;
	
	final int reTarChan1 = 10*60*1000;
	final int reTarChan2 = 8*60*1000;
	final int reTarChan3 = 15*60*1000;
	
	boolean chan3Available = true;
	//
	String usedExitName;
	String[] usedNewName;
	String chanceNumber = "0";
	
	MyThread myThread;
	    
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.context_doubletarget);	
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		userId = data.getUserId();
		username = data.getUserName();
		actId = data.getActId();
		//channel = 0 - Integer.parseInt(actId);
		channel = Integer.parseInt(actId);

		manager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
		sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
		MapApplication app = (MapApplication)this.getApplication();
		
		//未初始化则进行
		if (app.mBMapManager == null){
			app.mBMapManager = new BMapManager(this);
			app.mBMapManager.init(MapApplication.strKey, new MapApplication.MyGeneralListener());
		}
		app.mBMapManager.start();
		
		mMapView = (MapView)findViewById(R.id.bmapsView);  
		mMapView.setBuiltInZoomControls(true);  
		//设置启用内置的缩放控件  
		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(14);//设置地图比例级别 
		mMapController.setCompassMargin(20, 20);
		mMapController.setCenter(new GeoPoint((int) (37.5299 * 1E6), (int)(122.0606 * 1E6)));
		
		// 添加轨迹图层
		myPathOverlay = new GraphicsOverlay(mMapView);
		targetPathOverlay = new GraphicsOverlay(mMapView);			
		// 添加定位图层
        mLocationOverlay = new MyLocationOverlay(mMapView);
        mLocationOverlay.enableCompass();
		mMapView.getOverlays().add(mLocationOverlay);
		// 添加目标位置图层
		finderPoiOverlay = new FinderPoiOverlay(null, mMapView);
		mMapView.getOverlays().add(finderPoiOverlay);
		
		locationClient = new LocationClient(getApplicationContext());
        locData = new LocationData();
        LocationClientOption option = new LocationClientOption();
        //option.setPriority(LocationClientOption.NetWorkFirst);
    	option.setOpenGps(true);                                //是否打开GPS 
        option.setCoorType("bd09ll");                           //设置返回值的坐标类型。 
        option.setPriority(LocationClientOption.GpsFirst); 		//设置定位优先级 
        option.setProdName("山大");               		      	//设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。 
        option.setScanSpan(UPDATE_TIME);                        //设置定时定位的时间间隔。单位毫秒 
        locationClient.setLocOption(option);
              
        //注册位置监听器 
        locationClient.registerLocationListener(new BDLocationListener() { 
             
            @Override 
            public void onReceiveLocation(BDLocation location) {
            	//TODO 定位过程
                if (location == null) {
                	Toast.makeText(DoubleTarget.this, "定位失败", Toast.LENGTH_SHORT).show();
                    return; 
                }
        		
                locData.latitude = location.getLatitude();
                locData.longitude = location.getLongitude();
                locData.accuracy = location.getRadius();	//精度
                
                //drawable --> bitmap并旋转
                Drawable marker = getResources().getDrawable(R.drawable.arrow);
    			BitmapDrawable bd = (BitmapDrawable)marker;
                Bitmap bm = bd.getBitmap();
                Matrix matrix=new Matrix(); 
                matrix.postRotate(angle);
                Bitmap dstbmp=Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);
                BitmapDrawable newbd = new BitmapDrawable(dstbmp);                  		
                newbd.setBounds(0, 0, newbd.getIntrinsicWidth(), newbd.getIntrinsicHeight());  
                mLocationOverlay.setMarker(newbd);
                
                mLocationOverlay.setData(locData);
                mMapView.refresh();
            	GeoPoint p = new GeoPoint((int)(locData.latitude * 1E6), (int)(locData.longitude * 1E6));                
                if (locationControl == 1){
                	mMapController.animateTo(p);
                	locationControl = 0;
                }
                if (isStart && myWriteIn == 0 && locData.latitude != 0)
                	myLocPoi.add(p);
                myWriteIn = (myWriteIn+1)%3;
                
            }
            @Override 
            public void onReceivePoi(BDLocation location) { 
            }              
        });        
		locationClient.start();
		//发起多次定位
		locationClient.requestLocation();		
		////
		//locData.latitude = 37.5299;
		//locData.longitude = 122.0606;	
		
		startAndEnd = (Button)findViewById(R.id.end);
        save = (Button)findViewById(R.id.save); 
        startAndEnd.setClickable(false); 
        save.setVisibility(View.INVISIBLE);
	
		handler = new Handler();
		
		//倒计时（被找）
		endContextRun = new Runnable(){
			@Override
			public void run() {
				Toast.makeText(DoubleTarget.this, "时间到！您获胜了！", Toast.LENGTH_LONG).show();
				//handler.removeCallbacks(finderPoiRun);
				/*
				handler.removeCallbacks(recoverChan1);
				handler.removeCallbacks(recoverChan2);
				handler.removeCallbacks(recoverChan3);
				handler.removeCallbacks(exitRun);
				handler.removeCallbacks(getFinderPoiRun);*/
				myThread.stopHandler();
        		
				locationClient.stop();
				isOver = true;	
				mMapView.getOverlays().clear();
				myPathOverlay.setData(myPath(myLocPoi, true));
				mMapView.getOverlays().add(myPathOverlay);
				targetPathOverlay.setData(myPath(finderLocPoi, false));						
				mMapView.getOverlays().add(targetPathOverlay);
				mMapView.refresh();
				Toast.makeText(DoubleTarget.this, "活动轨迹:我方——蓝/对方——红", Toast.LENGTH_LONG).show();
				
				save.setVisibility(View.VISIBLE);
				startAndEnd.setText("结束离开");
				startAndEnd.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// 跳转页面
						leave();
						Intent intent = new Intent(DoubleTarget.this,RecordOfDouble.class);
						startActivity(intent);
						finish();
					}							
				});//end of listener
			}			
		};
		
		//final double mlat = 37.5299;
		//final double mlon = 122.0606;
		// TODO 发送位置、获得对方位置
		getFinderPoiRun = new Runnable(){
			@Override
			public void run() {
				if (!isOver){
					//上传位置
					new DoubleTrails().execute(actId,userId,locData.latitude,locData.longitude);

					String[] finderPoi = new GetDoubleLatLon().doInBackground(actId,userId,"utargetlatlon");
					
					if (!finderPoi[0].equals("-1") && !finderPoi[0].equals("0")){
						double lat = Double.valueOf(finderPoi[0]);
						double lon = Double.valueOf(finderPoi[1]);
						GeoPoint fPoi = new GeoPoint((int)(lat*1e6),(int)(lon*1e6));
					
						//Toast.makeText(DoubleTarget.this, lat+"   "+lon, Toast.LENGTH_SHORT).show();
						if (isStart && finWriteIn == 0)
							finderLocPoi.add(fPoi);
						finWriteIn = (finWriteIn+1)%3;
					
						//GeoPoint myPoi = new GeoPoint((int) (locData.latitude * 1E6), (int)(locData.longitude * 1E6));
						GeoPoint myPoi = new GeoPoint((int) (locData.latitude * 1E6), (int)(locData.longitude * 1E6));
						double distance = DistanceUtil.getDistance(myPoi, fPoi);
						if (distance < 150){
							isOver = true;
							Toast.makeText(DoubleTarget.this, "您已被找到!", Toast.LENGTH_LONG).show();
							/*
							handler.removeCallbacks(startContexRun);
		        			handler.removeCallbacks(exitRun);	
		        			handler.removeCallbacks(showCircle);
		        			handler.removeCallbacks(recoverChan1);
		        			handler.removeCallbacks(recoverChan2);
		        			handler.removeCallbacks(recoverChan3);
		        			handler.removeCallbacks(getChanceRun);*/
							//handler.removeCallbacks(getFinderPoiRun);
							myThread.stopHandler();
		        		
							save.setVisibility(View.VISIBLE);
							prompt.setClickable(false);
		        			
							mMapView.getOverlays().clear();
							myPathOverlay.setData(myPath(myLocPoi, true));
							mMapView.getOverlays().add(myPathOverlay);
							targetPathOverlay.setData(myPath(finderLocPoi, false));						
							mMapView.getOverlays().add(targetPathOverlay);
							mMapView.refresh();
							Toast.makeText(DoubleTarget.this, "活动轨迹:我方——蓝/对方——红", Toast.LENGTH_LONG).show();
		        		
							startAndEnd.setText("结束离开");
							startAndEnd.setOnClickListener(new OnClickListener(){
								@Override
								public void onClick(View v) {
									// 跳转页面
									leave();
									Intent intent = new Intent(DoubleTarget.this,RecordOfDouble.class);
									startActivity(intent);
									mMapView.destroy();
									finish();
								}							
							});
						}
					}
					handler.postDelayed(this, 2100);
				}
				else
					handler.removeCallbacks(this);
			}			
		};
		
		// TODO 机会恢复
		recoverChan1 = new Runnable(){
			@Override
			public void run() {
				if (isStart){
					if (tarchan1 < tarmax1)
						tarchan1++;
					handler.postDelayed(this, reTarChan1);
				}
				else
					handler.removeCallbacks(this);
			}			
		};
		
		recoverChan2 = new Runnable(){
			@Override
			public void run() {
				if (isStart){
					if (tarchan2 < tarmax2)
						tarchan2++;
					handler.postDelayed(this, reTarChan2);
				}
				else
					handler.removeCallbacks(this);
			}			
		};
		
		recoverChan3 = new Runnable(){
			@Override
			public void run() {
				if (isStart){
					if (tarchan3 < tarmax3)
						tarchan3++;
					handler.postDelayed(this, reTarChan3);
				}
				else
					handler.removeCallbacks(this);
			}			
		};
		

		myThread = new MyThread(handler);
		
		// TODO 开始信号		
		new JoinDouble().execute(actId,userId);
		
		usedNewName = new GetDoubleInfo().doInBackground(actId,"uname");
		startContexRun = new Runnable(){
			@Override
			public void run() {	
				String[] newName = new GetDoubleInfo().doInBackground(actId,"uname");
				int len = newName.length;
				if (len == 2){	
					if (usedNewName.length == 1){
						
						for (int i=0;i<2;i++){
							if (!(usedNewName[0].equals(newName[i])))
								finderName = newName[i];
						}
						Toast.makeText(DoubleTarget.this, "玩家："+finderName+"已进入！", Toast.LENGTH_SHORT).show();
						Vibrator vibrator = (Vibrator) DoubleTarget.this.getSystemService(Context.VIBRATOR_SERVICE);
						vibrator.vibrate(1000);
							
						startAndEnd.setHint("");
						startAndEnd.setText("开始");
						startAndEnd.setClickable(true);
						startAndEnd.setOnClickListener(new OnClickListener(){
							@Override
							public void onClick(View v) {
								isStart = true;
								new StartDoubleGame().execute(actId,userId,"doublestart");			
								/*
								handler.post(recoverChan1);
								handler.post(recoverChan2);
								handler.post(recoverChan3);
								handler.postDelayed(endContextRun, DURING_TIME);
								handler.post(getFinderPoiRun);
								handler.post(getChanceRun);*/
								myThread.start();
								
								handler.removeCallbacks(startContexRun);//
								
								Toast.makeText(DoubleTarget.this, "比赛开始,时限"+(DURING_TIME/(1000*60))+"分钟！", Toast.LENGTH_LONG).show();
									
								prompt.setHint("");
								prompt.setText("机会▼");
								prompt.setClickable(true);
								startAndEnd.setHint("");
								startAndEnd.setText("退出游戏");
								startAndEnd.setOnClickListener(new ExitGameOnClickListener());
							}							
						});		
					}//end of inner if
				}
				else{
					startAndEnd.setHint("开始");
					startAndEnd.setText("");
					startAndEnd.setClickable(false);					
				}
				usedNewName = newName;
				if (!isStart)
					handler.postDelayed(this, 2000);
				else
					handler.removeCallbacks(this);								
			}			
		};		
		handler.postDelayed(startContexRun,50);
		
		// TODO 退出信号
		usedExitName = new GetDoubleExit().doInBackground(actId);
		exitRun = new Runnable(){
			@Override
			public void run() {
				String exitName = new GetDoubleExit().doInBackground(actId);
				if (!exitName.equals(usedExitName) && !exitName.equals("-1")){
					if (isStart){
						locationClient.stop();
						Toast.makeText(DoubleTarget.this, "玩家："+exitName+"退出游戏，您获胜了！", Toast.LENGTH_LONG).show();
						isOver = true;
						save.setVisibility(View.VISIBLE);
						
						prompt.setHint("机会▼");
						prompt.setText("");
						prompt.setClickable(false);
						
						/*
						handler.removeCallbacks(endContextRun);
						handler.removeCallbacks(recoverChan1);
						handler.removeCallbacks(recoverChan2);
						handler.removeCallbacks(recoverChan3);
						handler.removeCallbacks(getFinderPoiRun);
						handler.removeCallbacks(exitRun);*///
						myThread.stopHandler();
			        		
						mMapView.getOverlays().clear();
						myPathOverlay.setData(myPath(myLocPoi, true));
						mMapView.getOverlays().add(myPathOverlay);
						targetPathOverlay.setData(myPath(finderLocPoi, false));						
						mMapView.getOverlays().add(targetPathOverlay);
						mMapView.refresh();
						Toast.makeText(DoubleTarget.this, "活动轨迹:我方——蓝/对方——红", Toast.LENGTH_LONG).show();
							
						startAndEnd.setText("结束离开");
						startAndEnd.setOnClickListener(new OnClickListener(){
							@Override
							public void onClick(View v) {
								// 跳转页面
								leave();
								Intent intent = new Intent(DoubleTarget.this,RecordOfDouble.class);
								startActivity(intent);
								finish();
							}							
						});//end of listener		
					}
					else{
						Toast.makeText(DoubleTarget.this, exitName+"已退出,请等待下一位玩家", Toast.LENGTH_SHORT).show();
					}
				}
				usedExitName = exitName;	
				handler.postDelayed(this, 2400);
				if (isOver)
					handler.removeCallbacks(this);				
			}			
		};
		handler.post(exitRun);
		
		// TODO 显示找方(机会)
		showFinderRun = new Runnable(){
			@Override
			public void run() {
				if (!isOver){
					int len = finderLocPoi.size();
					GeoPoint p = finderLocPoi.get(len-1);
					if (locationControl == 1){
						Toast.makeText(DoubleTarget.this, "对方在这里！", Toast.LENGTH_SHORT).show();
						mMapController.animateTo(p);
						locationControl = 0;
					}
					finderPoiOverlay.removeAll();
					OverlayItem item = new OverlayItem(p, finderName, null);
					Drawable marker = getResources().getDrawable(R.drawable.finder);
					//marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());		
					item.setMarker(marker);
					finderPoiOverlay.addItem(item);
					mMapView.refresh();					
					handler.postDelayed(this, 5000);
				}
				else
					handler.removeCallbacks(this);
			}		
		};
		
		// TODO 区别机会
		chanceHappenHandler = new Handler(){					
			@SuppressLint("HandlerLeak")
			public void handleMessage(Message msg) {  
				Vibrator vibrator = (Vibrator) DoubleTarget.this.getSystemService(Context.VIBRATOR_SERVICE);
				switch(msg.what){
				case 3:
					handler.post(showFinderRun);
					chan3Available = false;
					//promptc.setText("");
					//promptc.setHint("显示找方位置("+tarchan3+"/"+tarmax3+")");
					promptc.setClickable(false);
					break;
				case -3:
					handler.removeCallbacks(showFinderRun);
					Toast.makeText(DoubleTarget.this, "显示时间到!", Toast.LENGTH_SHORT).show();
					locationControl = 1;
					chan3Available = true;
					//promptc.setHint("");
					//promptc.setText("显示找方位置("+tarchan3+"/"+tarmax3+")");
					promptc.setClickable(true);
					finderPoiOverlay.removeAll();
					mMapView.refresh();
					break;
				case 4:
					Toast.makeText(DoubleTarget.this, "我方模糊半径被缩小!", Toast.LENGTH_SHORT).show();
					vibrator.vibrate(500);
					break;		
				case 5:
					Toast.makeText(DoubleTarget.this, "我方偏移量被缩小！", Toast.LENGTH_SHORT).show();
					vibrator.vibrate(500);
					break;
				default:
					break;
				}
			};			        
		};
		
		//接收机会
		getChanceRun = new Runnable(){
			@Override
			public void run() {
				if (!isOver){
					String newChance = new GetUsedPrivileges().doInBackground(actId);
					if (!chanceNumber.equals(newChance)){
						int activingChan = Math.abs(Integer.parseInt(newChance));
						Message message = new Message();  
						message.what = activingChan;  
						chanceHappenHandler.sendMessage(message);	
					}
					chanceNumber = newChance;	
					handler.postDelayed(this, 2000);
				}
				else
					handler.removeCallbacks(this);
			}			
		};	

		mLocationListener = new LocationListener(){		
			public void onLocationChanged(Location location) {
				if (location != null){
					GeoPoint pt = new GeoPoint((int)(location.getLatitude()*1e6),
							(int)(location.getLongitude()*1e6));
					mMapView.getController().animateTo(pt);
				}
			}

			@Override
			public void onProviderDisabled(String provider) {	
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {	
			}
        };
	
		mMapListener = new MKMapViewListener(){
		   	@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
		        //处理地图点击事件
				String title = "";
				if (mapPoiInfo != null){
					title = mapPoiInfo.strText;
					Toast.makeText(DoubleTarget.this,title,Toast.LENGTH_SHORT).show();
					mMapController.animateTo(mapPoiInfo.geoPt);
				}
			}
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onGetCurrentMap(Bitmap bitmap) {	
				// TODO 处理截图事件
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
				Date curDate = new Date(System.currentTimeMillis());//获取当前时间     
				String filename = formatter.format(curDate)+".jpg";
				
				// 判断sd卡是否存在
				boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
				File root = null;
				if (sdCardExist) {   
		            root = Environment.getExternalStorageDirectory();		// 获取根目录   
		        } else {   
		        	Toast.makeText(DoubleTarget.this, "未找到内存卡！", Toast.LENGTH_LONG).show();
		        	return;
		        }
				String filePath = root.toString()+"/CityTreasureHunter/DoubleContext";		// 获得路径
				Toast.makeText(DoubleTarget.this, "保存中，请稍后", Toast.LENGTH_LONG).show();
				// 保存
		        File file = new File(filePath);
		        if (!file.exists()) { 
					file.mkdirs();  //创建父级文件夹         
		        }
		        file = new File(filePath,filename);  
		        try {
					file.createNewFile();
				} catch (IOException e) {
					Toast.makeText(DoubleTarget.this, "文件已存在！", Toast.LENGTH_SHORT).show();    
				}
		        
		        FileOutputStream fos = null;
		        try {   
		        	// -------??		        	
		        	fos = new FileOutputStream(file);  
		            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);   
		            fos.flush();   
		            fos.close();
		            Toast.makeText(DoubleTarget.this, "成功！"+"图片保存路径:"+filePath, Toast.LENGTH_SHORT).show();   
		        }catch (FileNotFoundException e) {   
		        	Toast.makeText(DoubleTarget.this, "未找到文件！", Toast.LENGTH_SHORT).show();    
		        }catch (IOException e) {   
		        	Toast.makeText(DoubleTarget.this, "存储出错！", Toast.LENGTH_SHORT).show();   
		        } 	        
			}
			@Override
			public void onMapAnimationFinish() {				
			}
			@Override
			public void onMapMoveFinish() {
			}  	
		};
		// 用户注册地图监听器
		mMapView.regMapViewListener(MapApplication.getInstance().mBMapManager, mMapListener);
		
		contentView = getLayoutInflater().inflate(R.layout.context_promptmenu, null,true);
		isshow = (LinearLayout)this.findViewById(R.id.isshow);
		
		m_popupWindow = new PopupWindow(contentView, LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT, true);
		m_popupWindow.setBackgroundDrawable(new BitmapDrawable());// 有了这句才可以点击返回（撤销）按钮dismiss()popwindow
		m_popupWindow.setOutsideTouchable(true);
		m_popupWindow.setAnimationStyle(R.style.PopupAnimation);
        
        prompt = (Button)this.findViewById(R.id.prompt);
		prompta = (Button) contentView.findViewById(R.id.prompta);
		promptb = (Button) contentView.findViewById(R.id.promptb);
		promptc = (Button) contentView.findViewById(R.id.promptc);
		
		
		prompt.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				try {
					if (m_popupWindow.isShowing()) {
						m_popupWindow.dismiss();
					}
					else
						m_popupWindow.showAsDropDown(v);
					
					prompta.setText("增大模糊半径("+tarchan1+"/"+tarmax1+")");
					promptb.setText("随机增大偏移量("+tarchan2+"/"+tarmax2+")");				
					
					if (tarchan1 == 0){
						prompta.setText("");
						prompta.setHint("扩大我方模糊半径(0/"+tarmax1+")");
						prompta.setClickable(false);
					}
					else{
						prompta.setHint("");
						prompta.setClickable(true);
					}
					
					if (tarchan2 == 0){
						promptb.setText("");
						promptb.setHint("随机增大我方偏移量(0/"+tarmax1+")");
						promptb.setClickable(false);
					}
					else{
						promptb.setHint("");
						promptb.setClickable(true);
					}
					/*
					if (tarchan3 == 0){
						promptc.setText("");
						promptc.setHint("显示找方位置(0/"+tarmax1+")");
						promptc.setClickable(false);
					}
					else{
						promptc.setHint("");
						promptc.setClickable(true);
					}*/
					if (chan3Available && tarchan3 != 0){
						promptc.setText("显示双方距离("+tarchan3+"/"+tarmax3+")");
						promptc.setHint("");
						promptc.setClickable(true);
					}
					else{
						promptc.setText("");
						promptc.setHint("显示双方距离("+tarchan3+"/"+tarmax3+")");
						promptc.setClickable(false);
					}
				} catch (Exception e) {
					Toast.makeText(DoubleTarget.this, e.getMessage(),Toast.LENGTH_SHORT);
				}
			}
		});
		prompt.setClickable(false);
		
		prompta.setOnClickListener(new View.OnClickListener() {					
			@SuppressLint("ShowToast")
			public void onClick(View arg0) {				
				m_popupWindow.dismiss();
				tarchan1--;
				new UsePrivileges().execute(actId,targetChan1);		
				targetChan1 = 0-targetChan1;
			}
		});
		
		promptb.setOnClickListener(new View.OnClickListener() {					
			public void onClick(View arg0) {
				tarchan2--;
				m_popupWindow.dismiss();
				new UsePrivileges().execute(actId,targetChan2);
				targetChan2 = 0-targetChan2;
			}
		});
		
		promptc.setOnClickListener(new View.OnClickListener() {					
			public void onClick(View arg0) {	
				tarchan3--;
				m_popupWindow.dismiss();
				new UsePrivileges().execute(actId,targetChan3);
				targetChan3 = 0-targetChan3;
				
				showFinderTimer = new Timer();
				showFinderTimer.schedule(new TimerTask(){
					@Override
					public void run() {
						Message message = new Message();  
					    message.what = -3; 
					    chanceHappenHandler.sendMessage(message);
					}		
				}, showFinderTime);
			}
		});  
        
        Button btn_show = (Button) findViewById(R.id.btn_show);
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.context_slide, null);
		popupWindow =new PopupWindow(layout, LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		Button btn_dismiss = (Button) layout.findViewById(R.id.btn_dismiss);
        
		historyEdit=(EditText) layout.findViewById(R.id.history); 
		messageEdit=(EditText) layout.findViewById(R.id.message);
		sendButton=(Button) layout.findViewById(R.id.send);
		
		// TODO 聊天室按钮		
		sendButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				send();
			}
		}); 
					
		historyEdit.addTextChangedListener(new TextWatcher(){
		@Override
		public void afterTextChanged(Editable s) {			
		}

		@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {	
		}

		@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {	
				// 信息震动
				if (sender == false){
					Vibrator vibrator = (Vibrator) DoubleTarget.this.getSystemService(Context.VIBRATOR_SERVICE);
					vibrator.vibrate(1000);
					//Toast.makeText(DoubleTarget.this,"震动！",Toast.LENGTH_SHORT).show();
				}	
				sender = false;
			}		
		});
 
        save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				mMapView.getCurrentMap();
			}       	
        }); 
        
        login();  
        
        btn_dismiss.setOnClickListener(new OnClickListener(){
			 @Override
			 public void onClick(View v){				 
				 isshow.setVisibility(View.VISIBLE);
				 new Handler().postDelayed(new Runnable(){  
				     public void run(){  
				     //execute the task
				    	 openMenu();
				     }  
				  }, 100);
			 }
		 });
		
		btn_show.setOnClickListener(new OnClickListener(){
			 @Override
			 public void onClick(View v){
				 openMenu();
				 new Handler().postDelayed(new Runnable(){  
				     public void run(){  
				     //execute the task
				    	 isshow.setVisibility(View.INVISIBLE);
				     }  
				  }, 2000);		 
			 }
		 });		
	}
	
	// TODO 聊天室
	@SuppressLint("SimpleDateFormat")
	public void login(){		
		try{
			//客户端socket
			socket = new Socket(ip,PORT);				
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
				
			//服务器端输出信息
			out.writeInt(channel);
			//out.writeUTF("用户：  "+username+"  "+nowStr+"上线了！");						
		}catch(IOException e){
			e.printStackTrace();
		}
		//开线程，监听服务器消息，有则及时更新
		thread = new Thread(DoubleTarget.this);
		thread.start();	
	}
	
	public void leave(){		
		try{			
			out.writeUTF("<"+username+"> "+"下线了！");
			//先后
			out.close();
			in.close();
			socket.close();
		}catch(IOException e){					
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public void send(){		
		chat_txt = messageEdit.getText().toString();
		
		if (chat_txt == null && chat_txt.equals("")){
			Toast.makeText(DoubleTarget.this,"发送不能为空！",Toast.LENGTH_SHORT).show();
		}
		else{
			sender = true;
			Date now=new Date(System.currentTimeMillis()); 
			SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
			String nowStr = format.format(now);
			try {
				out.writeInt(channel);
				out.writeUTF(username+" "+nowStr+"说：\n"+chat_txt);
			} catch (IOException e) {
				e.printStackTrace();
			}			
			messageEdit.setText("");		
		}	
	}
	
	@Override
	public void run() {
		while (true){
			try{
				//读取发送来的信息
				//if (isStart){
					chat_in = in.readUTF();
					chat_in = chat_in+"\n";
					
					myHandler.sendMessage(myHandler.obtainMessage());
				//}
			}catch(IOException e){
			}
		}
	}
	@SuppressLint("HandlerLeak")
	
	//刷新界面(原本无法再非主线程更新界面)
	Handler myHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			//
			historyEdit.append(chat_in);
			super.handleMessage(msg);
		}
	};
	
	//TODO --返回键提醒--
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // 按下键盘上返回按钮   
	    if (keyCode == KeyEvent.KEYCODE_BACK) {  
	    	if (!isOver){
	    		new AlertDialog.Builder(this) 
	    		.setTitle("退出")  
	    		.setMessage("您确定要退出比赛吗？")  
	    		.setNegativeButton("取消",null)  
	    		.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
	    			@SuppressWarnings("unchecked")
					public void onClick(DialogInterface dialog,int whichButton) {
	    				//退出
			        	new ExitDoubleGame().execute(actId,userId,"doubletargetexit");
			        	locationClient.stop();
			        	
			        	handler.removeCallbacks(exitRun);
						isOver = true;
						
						if (isStart){
							handler.removeCallbacks(endContextRun);
							handler.removeCallbacks(recoverChan1);
				        	handler.removeCallbacks(recoverChan2);
				        	handler.removeCallbacks(recoverChan3);
				        	handler.removeCallbacks(getFinderPoiRun);
				        	handler.removeCallbacks(getChanceRun);
				        	
							save.setVisibility(View.VISIBLE);
							
							prompt.setHint("机会▼");
							prompt.setText("");
							prompt.setClickable(false);
			        		
							mMapView.getOverlays().clear();
							myPathOverlay.setData(myPath(myLocPoi, true));
							mMapView.getOverlays().add(myPathOverlay);
							targetPathOverlay.setData(myPath(finderLocPoi, false));						
							mMapView.getOverlays().add(targetPathOverlay);
							mMapView.refresh();
							Toast.makeText(DoubleTarget.this, "活动轨迹:我方——蓝/对方——红", Toast.LENGTH_LONG).show();
						}
						else
							handler.removeCallbacks(startContexRun);
						
						startAndEnd.setText("结束离开");
						startAndEnd.setOnClickListener(new OnClickListener(){
							@Override
							public void onClick(View v) {
								// 跳转页面
								if (isStart)
									leave();
								Intent intent = null;
								if (isStart)
									intent = new Intent(DoubleTarget.this,RecordOfDouble.class);
								else
									intent = new Intent(DoubleTarget.this,SearchAct.class);
								startActivity(intent);
								mMapView.destroy();
								finish();
							}							
						});
						//.... 
	    			}  
	    		}).show(); 
	    	}
	        else{
	        	Intent intent = new Intent(DoubleTarget.this,SearchAct.class);
				startActivity(intent);
				finish();
	        }
	    	return true; 
	    } 
	    else{  
	        return super.onKeyDown(keyCode, event);  
	    }  
	} 
	
	//TODO --方向传感器--
    SensorEventListener listener=new SensorEventListener(){
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			angle = event.values[0];
		}
	};
	
	// TODO 获得轨迹
	public Graphic myPath(ArrayList<GeoPoint> LocPoi, boolean mine){
		// ---获得点---
		/*
		GeoPoint gpoint1 = new GeoPoint((int) (37.5299 * 1E6), (int)(122.0606 * 1E6));
		GeoPoint gpoint2 = new GeoPoint((int) (37.5302 * 1E6), (int)(122.0654 * 1E6));
		GeoPoint gpoint3 = new GeoPoint((int) (37.5273 * 1E6), (int)(122.0683 * 1E6));
			
		LocPoi.add(gpoint1);
		LocPoi.add(gpoint2);
		LocPoi.add(gpoint3);*/
		
		//将调用两次
		int len = LocPoi.size();		
		GeoPoint[] geolist = new GeoPoint[len];
		
		for (int i=0;i<len;i++){
			geolist[i] = LocPoi.get(i);
		}
		// 像素与经纬转换
		Projection projection = mMapView.getProjection();
			
		Path path = new Path();
		Point loc = new Point();
		//画线
		Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(4);
            
		if (len == 0)
			Toast.makeText(DoubleTarget.this, "无点可记录！", Toast.LENGTH_SHORT).show();
		else{
			mMapController.animateTo(geolist[0]);
			projection.toPixels(geolist[0], loc);
			path.moveTo(loc.x, loc.y);
			for (int i=1;i<len;i++){
				projection.toPixels(geolist[i], loc);
				path.lineTo(loc.x, loc.y);
			}				
		}
		Geometry geometry = new Geometry();
		geometry.setPolyLine(geolist);
		Symbol symbol = new Symbol();	//创建样式 
		Symbol.Color color = symbol.new Color();	//创建颜色样式
		
		if (mine){
			color.blue = 255;
			color.red = 0;
		}
		else{
			color.blue = 0;
			color.red = 255;
		}
		color.green = 0;	
		color.alpha = 126;
		symbol.setLineSymbol(color, 4);
		Graphic graphic = new Graphic(geometry, symbol);
		return graphic;
	}
	
	//聊天室按钮
	public void btn_showOnClicked() {
		 openMenu();
		
	} 


	public void btn_dismissOnClicked() {
		 openMenu();
	}
	public void openMenu(){
		if (!flag){
			 popupWindow.setAnimationStyle(R.style.PopupAnimationb);
			 popupWindow.showAtLocation(findViewById(R.id.btn_show), Gravity.NO_GRAVITY, 0, 0);
			 popupWindow.setFocusable(true);
			 popupWindow.update();
			 flag =true;		 		
		}else{
			 popupWindow.dismiss();
			 popupWindow.setFocusable(false);
			 flag =false;
		}
	}
	
	// TODO ---找方位置图层---
	class FinderPoiOverlay extends ItemizedOverlay<OverlayItem>{
		public FinderPoiOverlay(Drawable arg0, MapView arg1) {
			super(arg0, arg1);
		}
			
		protected boolean onTap(int index){
			String name = this.getItem(index).getTitle();
			Toast.makeText(DoubleTarget.this, "玩家："+name, Toast.LENGTH_SHORT).show();
			return true;			
		}
	}
	
	// TODO 结束单击相应
	class ExitGameOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			new AlertDialog.Builder(DoubleTarget.this) 
			.setTitle("退出")  
			.setMessage("您确定要退出游戏吗？")  
			.setNegativeButton("取消",null)  
			.setPositiveButton("确定",new DialogInterface.OnClickListener() {  
				
				@SuppressWarnings("unchecked")
				public void onClick(DialogInterface dialog,int whichButton) { 				
					//退出
		        	new ExitDoubleGame().execute(actId,userId,"doubletargetexit");
		        	locationClient.stop();
		        	handler.removeCallbacks(exitRun);
		        	
					isOver = true;
					
					if (isStart){
						save.setVisibility(View.VISIBLE);
						
						prompt.setHint("机会▼");
						prompt.setText("");
						prompt.setClickable(false);
						
						/*
						handler.removeCallbacks(endContextRun);	
			        	handler.removeCallbacks(getFinderPoiRun);
			        	handler.removeCallbacks(recoverChan1);
			        	handler.removeCallbacks(recoverChan2);
			        	handler.removeCallbacks(recoverChan3);
			        	handler.removeCallbacks(getChanceRun);*/
						myThread.stopHandler();
						
						mMapView.getOverlays().clear();
						myPathOverlay.setData(myPath(myLocPoi, true));
						mMapView.getOverlays().add(myPathOverlay);
						targetPathOverlay.setData(myPath(finderLocPoi, false));						
						mMapView.getOverlays().add(targetPathOverlay);
						mMapView.refresh();
						Toast.makeText(DoubleTarget.this, "活动轨迹:我方——蓝/对方——红", Toast.LENGTH_LONG).show();
					}
					else
						handler.removeCallbacks(startContexRun);
					startAndEnd.setText("结束离开");
					startAndEnd.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
							// 跳转页面
							if (isStart)
								leave();
							Intent intent = null;
							if (isStart)
								intent = new Intent(DoubleTarget.this,RecordOfDouble.class);
							else
								intent = new Intent(DoubleTarget.this,SearchAct.class);
							startActivity(intent);
							mMapView.destroy();
							finish();
						}							
					});//end of listener
				}  
			}).show();//end of AlertDialog			
		}		
	}
	
	// TODO 线程
	class MyThread extends Thread{
		Handler mHandler;
		
		public MyThread(Handler handler){
			mHandler = handler;
		}
		
		public void run(){
			mHandler.post(recoverChan1);
			mHandler.post(recoverChan2);
			mHandler.post(recoverChan3);
			mHandler.postDelayed(endContextRun, DURING_TIME);
			mHandler.postDelayed(getFinderPoiRun,50);
			mHandler.postDelayed(getChanceRun,100);
		}
		
		public void stopHandler(){
			mHandler.removeCallbacks(endContextRun);
			mHandler.removeCallbacks(recoverChan1);
			mHandler.removeCallbacks(recoverChan2);
			mHandler.removeCallbacks(recoverChan3);
			mHandler.removeCallbacks(getFinderPoiRun);
			mHandler.removeCallbacks(getChanceRun);
			mHandler.removeCallbacks(showFinderRun);
		}	
	}
	
	@Override 
    protected void onDestroy() { 
        if (locationClient != null && locationClient.isStarted()) { 
            locationClient.stop();            
            locationClient = null;
        } 
        if (handler != null)
        	handler = null;
        if (myThread != null)
        	myThread = null;
        mMapView.destroy();
        mLocationOverlay.disableCompass(); // 关闭指南针
        manager.unregisterListener(listener);
        leave();       
        super.onDestroy();
    }
	
	@Override 
	protected void onPause(){
		MapApplication app = (MapApplication)this.getApplication();
        mLocationOverlay.disableCompass(); // 关闭指南针
		app.mBMapManager.stop();
		manager.unregisterListener(listener);
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		MapApplication app = (MapApplication)this.getApplication();
		// 注册定位事件，定位后将地图移动到定位点
        mLocationOverlay.enableCompass(); // 打开指南针
		app.mBMapManager.start();
		manager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}
}
