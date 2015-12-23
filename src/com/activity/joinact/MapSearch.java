package com.activity.joinact;

import java.util.ArrayList;

import com.activity.buildact.BuildSetPoi;
import com.activity.context.MapApplication;
import com.activity.main.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.function.joinact.ActsMapDouble;
import com.function.joinact.ActsMapSingle;
import com.function.joinact.ActsMapTeam;
import com.function.joinact.GetSid;
import com.function.team.GetTeamID;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MapSearch extends Activity {
	private Button but_menu;
	// private Button allact;
	private Button single;
	private Button team;
	private Button doublesearch;
	private LinearLayout progress;

	private PopupWindow m_popupWindow;
	private String sid;
	private String slat;
	private String slon;
	private String type;
	private String userId;
	
	private LocationClient locationClient;
	LocationData locData;
	
	
	private ImageView refresh;
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	ArrayList<OverlayItem> overlayItems;
	Drawable drawable;
	FirstOverlay firstoverlay;
	Drawable mark;
	Intent intent;
	int i;

	OverlayItem overlayItem;
	ActsMapSingle ams = new ActsMapSingle();
	ActsMapTeam amt = new ActsMapTeam();
	ActsMapDouble amd = new ActsMapDouble();
	
	View contentView;
	GetSid gs = new GetSid();
	String tid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SearchAct.joinActActivityList.add(this);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		userId = data.getUserId();
		
		GetTeamID gti =new GetTeamID();
		tid =gti.GetTID(userId);

		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("4B2EBC226F68A4BEC54B0E7E0C6D65EB5FB0809D", null);
		setContentView(R.layout.joinact_mapsearch);
		mMapView = (MapView) findViewById(R.id.listmapsView);
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		final MapController mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		
		GeoPoint point = new GeoPoint((int) (37.5299 * 1E6),
				(int) (122.0606 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(12);// 设置地图zoom级别
		
		/*
         * 定位相关
         */
        locationClient = new LocationClient(getApplicationContext());
        locData = new LocationData();
        LocationClientOption option = new LocationClientOption();
        //option.setPriority(LocationClientOption.NetWorkFirst);
    	option.setOpenGps(true);                                //是否打开GPS 
        option.setCoorType("bd09ll");                           //设置返回值的坐标类型。 
        option.setPriority(LocationClientOption.GpsFirst); 		//设置定位优先级 
        option.setProdName("山大");               		      	//设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。 
        locationClient.setLocOption(option);
              
        //注册位置监听器 
        locationClient.registerLocationListener(new BDLocationListener() { 
             
            @Override 
            public void onReceiveLocation(BDLocation location) {
            	//TODO 定位过程        	
                if (location == null) {
                	Toast.makeText(MapSearch.this, "定位失败", Toast.LENGTH_SHORT).show();
                    return; 
                }
        		
                locData.latitude = location.getLatitude();
                locData.longitude = location.getLongitude();
                if (locData.longitude == 0){
                	GeoPoint point = new GeoPoint((int) (37.5299 * 1E6),(int) (122.0606 * 1E6));
            		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
            		mMapController.animateTo(point);// 设置地图中心点
                }
                else{
                	GeoPoint p = new GeoPoint((int)(locData.latitude * 1E6), (int)(locData.longitude * 1E6));  
                	mMapController.animateTo(p);
                }
            }
            @Override 
            public void onReceivePoi(BDLocation location) { 
            }              
        });        
		locationClient.start();

		overlayItems = new ArrayList<OverlayItem>();

		drawable = getResources().getDrawable(R.drawable.ic_launcher);
		firstoverlay = new FirstOverlay(drawable, mMapView);
		mMapView.getOverlays().add(firstoverlay);
		intent = new Intent(MapSearch.this, SearchAct.class);
		init();
		setListener();
		for (int n = 1; n < 4; n++) {
			String na = String.valueOf(n);
			double[][] rest = null;
			//rest = aml.ChangeToDouble(na,uid);
			if (na.equals("2")) // 团体
			{
				//rest = aml.ChangeToDouble(na,userId);
				rest = amt.ChangeToDoubleTeam(na, tid);
				for (i = 0; i < rest.length; i++) {
					
					GeoPoint pt = new GeoPoint((int) (rest[i][0] * 1E6),
							(int) (rest[i][1] * 1E6));
					mark = getResources().getDrawable(R.drawable.activity_team);
					overlayItem = new OverlayItem(pt, "team", "活动地点：\n经度"
							+ rest[i][0] + "纬度" + rest[i][1]);
					overlayItem.setMarker(mark);
					overlayItems.add(overlayItem);
					firstoverlay.addItem(overlayItem);
					mMapView.refresh();
				}
			}
			if (na.equals("3"))// 个人
			{
				rest = ams.ChangeToDoubleSingle(na, userId);
				//rest = aml.ChangeToDouble(na,userId);
				for (i = 0; i < rest.length; i++) {
					GeoPoint pt = new GeoPoint((int) (rest[i][0] * 1E6),
							(int) (rest[i][1] * 1E6));
					mark = getResources().getDrawable(
							R.drawable.activity_single);
					overlayItem = new OverlayItem(pt, "single", "活动地点：\n经度"
							+ rest[i][0] + "纬度" + rest[i][1]);
					overlayItem.setMarker(mark);
					overlayItems.add(overlayItem);
					firstoverlay.addItem(overlayItem);
					mMapView.refresh();
				}
			}
			if (na.equals("1")) // 双人

			{
				rest = amd.ChangeToDoubleDouble(na);
				//rest = aml.ChangeToDouble(na,userId);
				for (i = 0; i < rest.length; i++) {
					GeoPoint pt = new GeoPoint((int) (rest[i][0] * 1E6),
							(int) (rest[i][1] * 1E6));
					mark = getResources().getDrawable(
							R.drawable.activity_double);
					overlayItem = new OverlayItem(pt, "double", "活动地点：\n经度"
							+ rest[i][0] + "纬度" + rest[i][1]);
					overlayItem.setMarker(mark);
					overlayItems.add(overlayItem);
					firstoverlay.addItem(overlayItem);
					mMapView.refresh();
				}
			}

		}

		// GeoPoint pt =new GeoPoint((int)(37.5299* 1E6),(int)(122.0606* 1E6));
		// GeoPoints.add(pt);
		// int i;
	}

	private void init() {
		contentView = getLayoutInflater().inflate(R.layout.joinact_popupmenu,
				null, true);
		but_menu = (Button) findViewById(R.id.but_menu);
		refresh = (ImageView) findViewById(R.id.Refresh);
		progress = (LinearLayout) findViewById(R.id.progress);

		// allact = (Button) contentView.findViewById(R.id.AllAct);
		single = (Button) contentView.findViewById(R.id.Single);
		team = (Button) contentView.findViewById(R.id.Team);
		doublesearch = (Button) contentView.findViewById(R.id.DoubleSearch);

		m_popupWindow = new PopupWindow(contentView, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT, true);

		m_popupWindow.setBackgroundDrawable(new BitmapDrawable());// 有了这句才可以点击返回（撤销）按钮dismiss()popwindow
		m_popupWindow.setOutsideTouchable(true);
		m_popupWindow.setAnimationStyle(R.style.PopupAnimation);
	}

	private void setListener() {
		contentView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				m_popupWindow.dismiss();
			}
		});
		but_menu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (m_popupWindow.isShowing()) {
						m_popupWindow.dismiss();
					}
					m_popupWindow.showAsDropDown(v);
				} catch (Exception e) {
					Toast.makeText(MapSearch.this, e.getMessage(),
							Toast.LENGTH_SHORT);
				}
			}
		});

		refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {				
				// TODO Auto-generated method stub
				refresh.setVisibility(View.GONE);
				progress.setVisibility(View.VISIBLE);
				Toast.makeText(MapSearch.this, "正在刷新列表", Toast.LENGTH_SHORT)
						.show();
				new Handler().postDelayed(new Runnable() {
					public void run() {
						// execute the task
						refresh.setVisibility(View.VISIBLE);
						progress.setVisibility(View.GONE);
					}
				}, 2000);
			}
		});

		/*
		 * allact.setOnClickListener(new View.OnClickListener() {
		 * 
		 * public void onClick(View arg0) { m_popupWindow.dismiss();
		 * but_menu.setText("全部活动≡");
		 * 
		 * 
		 * } });
		 */
		single.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				m_popupWindow.dismiss();
				but_menu.setText("单人赛≡");

				firstoverlay.removeAll();
				overlayItems.clear();
				int i;

				//double[][] rest = aml.ChangeToDouble("3",userId);
				double[][] rest = ams.ChangeToDoubleSingle("3", userId);

				for (i = 0; i < rest.length; i++) {
					GeoPoint pt = new GeoPoint((int) (rest[i][0] * 1E6),
							(int) (rest[i][1] * 1E6));
					mark = getResources().getDrawable(
							R.drawable.activity_single);
					overlayItem = new OverlayItem(pt, "single", "活动地点：\n经度"
							+ rest[i][0] + "纬度" + rest[i][1]);
					System.out.println(rest[i][0]);
					overlayItem.setMarker(mark);
					overlayItems.add(overlayItem);
					firstoverlay.addItem(overlayItem);
					mMapView.refresh();
				}

			}
		});

		team.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				m_popupWindow.dismiss();
				but_menu.setText("团体赛≡");
				firstoverlay.removeAll();
				overlayItems.clear();
				int i;
				//double[][] rest = aml.ChangeToDouble("2",userId);
				double[][] rest = amt.ChangeToDoubleTeam("2", tid);
;

				for (i = 0; i < rest.length; i++) {
					GeoPoint pt = new GeoPoint((int) (rest[i][0] * 1E6),
							(int) (rest[i][1] * 1E6));
					mark = getResources().getDrawable(R.drawable.activity_team);
					overlayItem = new OverlayItem(pt, "team", "活动地点：\n经度"
							+ rest[i][0] + "纬度" + rest[i][1]);
					overlayItem.setMarker(mark);
					overlayItems.add(overlayItem);
					firstoverlay.addItem(overlayItem);
					mMapView.refresh();
				}

			}
		});

		doublesearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				m_popupWindow.dismiss();
				but_menu.setText("双人互找≡");
				firstoverlay.removeAll();
				overlayItems.clear();
				int i;
				//double[][] rest = aml.ChangeToDouble("1",userId);
				double[][] rest = amd.ChangeToDoubleDouble("1");
;

				for (i = 0; i < rest.length; i++) {
					GeoPoint pt = new GeoPoint((int) (rest[i][0] * 1E6),
							(int) (rest[i][1] * 1E6));
					mark = getResources().getDrawable(
							R.drawable.activity_double);
					overlayItem = new OverlayItem(pt, "double", "活动地点：\n经度"
							+ rest[i][0] + "纬度" + rest[i][1]);
					overlayItem.setMarker(mark);
					overlayItems.add(overlayItem);
					firstoverlay.addItem(overlayItem);
					mMapView.refresh();
				}

			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (m_popupWindow != null && m_popupWindow.isShowing()) {
				m_popupWindow.dismiss();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public class FirstOverlay extends ItemizedOverlay<OverlayItem> {

		public FirstOverlay(Drawable marker, MapView mapview) {
			super(marker, mapview);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean onTap(int index) {
			// 第index+1个活动

			double lon = overlayItems.get(index).getPoint().getLatitudeE6() / 1000000.0;
			double lat = overlayItems.get(index).getPoint().getLongitudeE6() / 1000000.0;
			slat = String.valueOf(lat);
			slon = String.valueOf(lon);
			type = overlayItems.get(index).getTitle();
			// Bundle mBundle = new Bundle();
			// mBundle.putString("sid", gs.Getsid(slat, slon, ns));
			// System.out.println(slon);
			// System.out.println(slat);
			// System.out.println(ns);
			System.out.println(gs.Getsid(slat, slon, type));

			// sid = gs.Getsid(slat, slon, ns);

			
			if (type.equals("double")){
				intent = new Intent(MapSearch.this, ActInfoOfDouble.class);
				intent.putExtra("sid", gs.Getsid(slat, slon, type).toString());
				intent.putExtra("type", type.toString());
			}
				
			else{
				intent = new Intent(MapSearch.this, ActInfo.class);
				intent.putExtra("sid", gs.Getsid(slat, slon, type).toString());
				intent.putExtra("type", type.toString());
			}
				
			
			// intent.putExtras(mBundle);
			startActivity(intent);
			return true;

		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {	
		if (locationClient != null && locationClient.isStarted()) { 
            locationClient.stop();            
            locationClient = null;
        } 
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}
