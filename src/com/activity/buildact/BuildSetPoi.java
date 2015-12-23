package com.activity.buildact;

import java.io.Serializable;
import java.util.ArrayList;

import com.activity.context.SingleContext;
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
import com.baidu.platform.comapi.map.Projection;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BuildSetPoi extends Activity implements Serializable {
	protected static Object handler;
	private ImageButton last;
	private ImageButton reset;
	private ImageButton next;

	private Intent lastintent;
	private Intent nextintent;

	BMapManager mBMapMan = null;
	MapView mMapView = null;
	int userLatitude;
	int userLongitude;
	FirstOverlay firstoverlay;

	String text1;
	String text2;
	GeoPoint pt;

	TextView tvinfo;
	EditText et1;
	EditText et2;
	ListView List;
	TextView tvmany;
	ArrayList<OverlayItem> overlayItems;
	ArrayList<String> question;
	ArrayList<String> answer;
	ArrayList<String> warning;

	ArrayList<String> checkpointLongitude;
	ArrayList<String> checkpointLatitude;
	ArrayList<String> warningpointLongitude;
	ArrayList<String> warningpointLatitude;
	String startLongitude;
	String startLatitude;

	boolean ifstart = false;// 是否将其设为起点
	int itap = 0;// 点击次数
	EditText etq;
	EditText eta;
	EditText etw;
	Drawable drawable;
	// Information info;

	String textq;
	String texta;
	String textw;

	boolean queNotEmpty = false;
	boolean ansNotEmpty = false;

	private String sactivityname;
	private String slasttime;
	private String slimittime;
	private String sbrifintroduction;
	private String pro;
	private String peoplenumber;

	private String slimitTimemin;
	private String slimitTimeh;
	
	private LocationClient locationClient;
	LocationData locData;	

	int m = 0;
	int n = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BuildInformation.buildActivityList.add(this);	
		
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("4B2EBC226F68A4BEC54B0E7E0C6D65EB5FB0809D", null);
		setContentView(R.layout.build_setpoi);
		mMapView = (MapView) findViewById(R.id.checkmapsView);
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		final MapController mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (37.5299 * 1E6),(int) (122.0606 * 1E6));
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
                	Toast.makeText(BuildSetPoi.this, "定位失败", Toast.LENGTH_SHORT).show();
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
		locationClient.requestLocation();

		overlayItems = new ArrayList<OverlayItem>();
		drawable = getResources().getDrawable(R.drawable.green);
		firstoverlay = new FirstOverlay(drawable, mMapView);
		mMapView.getOverlays().add(firstoverlay);
		last = (ImageButton) findViewById(R.id.Last);
		reset = (ImageButton) findViewById(R.id.Reset);
		next = (ImageButton) findViewById(R.id.Next);

		tvinfo = (TextView) findViewById(R.id.tvinfo);

		// lastintent = new Intent(SetHeadAct.this, Introduction.class);
		nextintent = new Intent(BuildSetPoi.this, BuildBrowsing.class);

		Bundle bundle = this.getIntent().getExtras();

		slimitTimemin = (String) bundle.getSerializable("slimitTimemin");
		slimitTimeh = (String) bundle.getSerializable("slimitTimeh");
		sactivityname = (String) bundle.getSerializable("sactivityname");
		slasttime = (String) bundle.getSerializable("slasttime");
		slimittime = (String) bundle.getSerializable("slimittime");
		sbrifintroduction = (String) bundle
				.getSerializable("sbrifintroduction");
		pro = (String) bundle.getSerializable("pro");
		peoplenumber = (String) bundle.getSerializable("peoplenumber");

		if (pro.equals("双人互找"))
			tvinfo.setText("请选择活动中心点");
		else
			tvinfo.setText("请设置起点、检查点、警告点:");

		last.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// startActivity(lastintent);
				BuildSetPoi.this.finish();
				// System.out.println(peoplenumber);
			}
		});

		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				mMapView.getOverlays().clear();
				overlayItems.clear();
				ifstart = false;
				mMapView.refresh();

			}

		});

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				question = new ArrayList<String>();
				answer = new ArrayList<String>();
				warning = new ArrayList<String>();
				checkpointLongitude = new ArrayList<String>();
				checkpointLatitude = new ArrayList<String>();
				warningpointLongitude = new ArrayList<String>();
				warningpointLatitude = new ArrayList<String>();

				for (int i = 1; i <= itap; i++) {
					if (overlayItems.get(i).getTitle() == "警告点信息") {
						warning.add(overlayItems.get(i).getSnippet());
						warningpointLatitude.add((overlayItems.get(i).getPoint().getLatitudeE6() /1000000.0)+ "");
						warningpointLongitude.add((overlayItems.get(i).getPoint().getLongitudeE6() /1000000.0)+ "");
					} else {
						checkpointLatitude.add((overlayItems.get(i).getPoint()
								.getLatitudeE6() / 1E6)
								+ "");
						checkpointLongitude.add((overlayItems.get(i).getPoint()
								.getLongitudeE6() / 1E6)
								+ "");
						question.add(overlayItems.get(i).getTitle());
						answer.add(overlayItems.get(i).getSnippet());

					}
				}

				if (pro.equals("双人互找") && !ifstart) {
					Toast.makeText(BuildSetPoi.this, "请设置一个活动中心点",
							Toast.LENGTH_SHORT).show();
				}

				else if (!pro.equals("双人互找")
						&& (!ifstart || question.isEmpty())) {

					Toast.makeText(BuildSetPoi.this, "请设置至少一个起点，一个检查点",
							Toast.LENGTH_SHORT).show();
				}
				else {
					startLatitude = (overlayItems.get(0).getPoint().getLatitudeE6() / 1E6) + "";
					startLongitude = (overlayItems.get(0).getPoint().getLongitudeE6() / 1E6) + "";

					Bundle bundle = new Bundle();

					bundle.putSerializable("sactivityname", sactivityname);
					bundle.putSerializable("slasttime", slasttime);

					bundle.putSerializable("slimitTimemin", slimitTimemin);
					bundle.putSerializable("slimitTimeh", slimitTimeh);

					bundle.putSerializable("sbrifintroduction",sbrifintroduction);
					bundle.putSerializable("pro", pro);
					bundle.putSerializable("peoplenumber", peoplenumber);

					bundle.putSerializable("answer", answer);
					bundle.putSerializable("question", question);
					bundle.putSerializable("warning", warning);

					bundle.putSerializable("checkpointLongitude",checkpointLongitude);
					bundle.putSerializable("checkpointLatitude",checkpointLatitude);
					bundle.putSerializable("warningpointLongitude",warningpointLongitude);
					bundle.putSerializable("warningpointLatitude",warningpointLatitude);
					bundle.putSerializable("startLongitude", startLongitude);
					bundle.putSerializable("startLatitude", startLatitude);
					nextintent.putExtras(bundle);
					startActivity(nextintent);
				}
			}
		});
	}

	// 获得屏幕点击的位置 将像素坐标转为地址坐标

	@Override
	public boolean onTouchEvent(final MotionEvent event) {

		if (pro.equals("双人互找")) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			// 将像素坐标转为地址坐标
			Projection proj = mMapView.getProjection();
			pt = proj.fromPixels(x, y);
			if (!ifstart) {
				AlertDialog.Builder builder = new AlertDialog.Builder(BuildSetPoi.this);
				builder.setTitle("确定？");// 获取标题
				builder.setMessage("确定将此处作为活动中心点？");// 对话框内容
				builder.setIcon(R.drawable.ic_launcher);
				builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								Drawable startmark = getResources().getDrawable(R.drawable.green);
								OverlayItem overlayItem = new OverlayItem(pt,"活动", "中心点");
								overlayItem.setMarker(startmark);
								overlayItems.add(0, overlayItem);

								// mMapView.getOverlays().add(firstoverlay);
								firstoverlay.addItem(overlayItem);
								mMapView.refresh();
								ifstart = true;
							}
						});
				builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,int which) {	
								dialog.cancel();
							}
						});
				Dialog dialog = builder.create();// 创建对话框
				dialog.show();
			} else {
				Toast.makeText(BuildSetPoi.this, "只能设置一个活动中心点",Toast.LENGTH_SHORT).show();
			}
		} else {
			// 获得屏幕点击的位置

			int x = (int) event.getX();
			int y = (int) event.getY();
			// 将像素坐标转为地址坐标
			Projection proj = mMapView.getProjection();
			pt = proj.fromPixels(x, y);
			if (!ifstart) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						BuildSetPoi.this);
				builder.setTitle("确定？");// 获取标题
				builder.setMessage("确定将此处作为活动起点？");// 对话框内容
				builder.setIcon(R.drawable.ic_launcher);
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								Drawable startmark = getResources()
										.getDrawable(R.drawable.green);
								OverlayItem overlayItem = new OverlayItem(pt,
										"活动", "起点");
								overlayItem.setMarker(startmark);
								overlayItems.add(0, overlayItem);

								// mMapView.getOverlays().add(firstoverlay);
								firstoverlay.addItem(overlayItem);
								mMapView.refresh();
								ifstart = true;
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								dialog.cancel();
							}
						});
				Dialog dialog = builder.create();// 创建对话框
				dialog.show();

			} else {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						BuildSetPoi.this);
				builder.setTitle("提示");// 获取标题
				builder.setMessage("请选择将此处作为活动检查点还是警告点");// 对话框内容
				builder.setIcon(R.drawable.ic_launcher);
				builder.setPositiveButton("检查点",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								final Dialog checkdialog = new Dialog(
										BuildSetPoi.this);
								checkdialog
										.setContentView(R.layout.build_checkpoidialog);
								etq = (EditText) checkdialog
										.findViewById(R.id.checkquestion);
								eta = (EditText) checkdialog
										.findViewById(R.id.checkanswer);

								final Button send = (Button) checkdialog
										.findViewById(R.id.send);
								final Button cancel = (Button) checkdialog
										.findViewById(R.id.cancel);

								cancel.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										
										checkdialog.dismiss();
									}
								});
								etq.addTextChangedListener(new TextWatcher() {

									@Override
									public void afterTextChanged(Editable s) {
										
										if (etq.getText().toString().equals(""))
											queNotEmpty = false;
										else
											queNotEmpty = true;
										if (queNotEmpty && ansNotEmpty) {
											send.setClickable(true);
											send.setHint(null);
											send.setText("提交");
											send.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View v) {
													
													// method stub
													texta = eta.getText()
															.toString();
													textq = etq.getText()
															.toString();
													itap++;
													Drawable checkmark = getResources()
															.getDrawable(
																	R.drawable.red);
													OverlayItem overlayItem = new OverlayItem(
															pt, textq, texta);
													overlayItem
															.setMarker(checkmark);
													overlayItems.add(itap,
															overlayItem);
													// mMapView.getOverlays().clear();
													firstoverlay
															.addItem(overlayItem);
													mMapView.refresh();
													checkdialog.dismiss();
												}
											});
										} else {
											send.setOnClickListener(null);
											send.setText(null);
											send.setHint("提交");
											send.setClickable(false);
										}
									}

									@Override
									public void beforeTextChanged(
											CharSequence s, int start,
											int count, int after) {
										

									}

									@Override
									public void onTextChanged(CharSequence s,
											int start, int before, int count) {
										

									}

								});
								eta.addTextChangedListener(new TextWatcher() {

									@Override
									public void afterTextChanged(Editable s) {
										
										if (eta.getText().toString().equals(""))
											ansNotEmpty = false;
										else
											ansNotEmpty = true;
										if (queNotEmpty && ansNotEmpty) {
											send.setClickable(true);
											send.setHint(null);
											send.setText("提交");
											send.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View v) {
													
													// method stub
													texta = eta.getText()
															.toString();
													textq = etq.getText()
															.toString();
													itap++;
													Drawable checkmark = getResources()
															.getDrawable(
																	R.drawable.red);
													OverlayItem overlayItem = new OverlayItem(
															pt, textq, texta);
													overlayItem
															.setMarker(checkmark);
													overlayItems.add(itap,
															overlayItem);
													// mMapView.getOverlays().clear();
													firstoverlay
															.addItem(overlayItem);
													mMapView.refresh();
													checkdialog.dismiss();
												}
											});
										} else {
											send.setOnClickListener(null);
											send.setText(null);
											send.setHint("提交");
											send.setClickable(false);
										}
									}

									@Override
									public void beforeTextChanged(
											CharSequence s, int start,
											int count, int after) {
										

									}

									@Override
									public void onTextChanged(CharSequence s,
											int start, int before, int count) {
										

									}

								});
								checkdialog.show();

							}
						});
				builder.setNegativeButton("警告点",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								final Dialog warningdialog = new Dialog(
										BuildSetPoi.this);
								warningdialog
										.setContentView(R.layout.build_warningpoidialog);
								etw = (EditText) warningdialog
										.findViewById(R.id.warning);

								final Button send = (Button) warningdialog
										.findViewById(R.id.send);
								final Button cancel = (Button) warningdialog
										.findViewById(R.id.cancel);

								cancel.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										
										warningdialog.dismiss();
									}
								});
								etw.addTextChangedListener(new TextWatcher() {

									@Override
									public void afterTextChanged(Editable s) {
										
										if (etw.getText().toString().equals("")) {
											send.setOnClickListener(null);
											send.setText(null);
											send.setHint("提交");
											send.setClickable(false);
										} else {
											send.setClickable(true);
											send.setHint(null);
											send.setText("提交");
											send.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View v) {
													// method stub
													textw = etw.getText()
															.toString();
													itap++;
													Drawable warningmark = getResources()
															.getDrawable(
																	R.drawable.yellow);
													OverlayItem overlayItem = new OverlayItem(
															pt, "警告点信息", textw);
													overlayItem
															.setMarker(warningmark);
													overlayItems.add(itap,
															overlayItem);
													// mMapView.getOverlays().clear();
													firstoverlay
															.addItem(overlayItem);
													mMapView.refresh();
													warningdialog.dismiss();
												}
											});
										}
									}

									@Override
									public void beforeTextChanged(
											CharSequence s, int start,
											int count, int after) {
								

									}

									@Override
									public void onTextChanged(CharSequence s,
											int start, int before, int count) {
										

									}

								});
								warningdialog.show();

							}
						});
				Dialog dialog = builder.create();// 创建对话框
				dialog.show();

			}

		}

		return super.onTouchEvent(event);

	}

	public class FirstOverlay extends ItemizedOverlay<OverlayItem> {

		public FirstOverlay(Drawable marker, MapView mapview) {
			super(marker, mapview);
			
		}

		@Override
		protected boolean onTap(final int index) {
			if (ifstart) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						BuildSetPoi.this);

				builder.setTitle("相关信息");// 获取标题
				if (index == 0) {
					builder.setMessage("此处为"
							+ overlayItems.get(index).getTitle()
							+ overlayItems.get(index).getSnippet());// 对话框内容
				} else {
					if (overlayItems.get(index).getTitle() == "警告点信息")
						builder.setMessage("提示: "
								+ overlayItems.get(index).getSnippet());
					builder.setMessage("问题: "
							+ overlayItems.get(index).getTitle() + "\n"
							+ "答案: " + overlayItems.get(index).getSnippet());
				}
				builder.setIcon(R.drawable.ic_launcher);
				builder.setNegativeButton("清除该点",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								

								if (overlayItems.get(index).getTitle() == "活动") {

									FirstOverlay.this.removeItem(overlayItems
											.get(0));
									ifstart = false;
									// System.out.println("起点"+index);

								} else {
									FirstOverlay.this.removeItem(overlayItems
											.get(index));

									for (int i = index; i < itap; i++)
										overlayItems.add(i,
												overlayItems.get(i + 1));
									itap--;
								}
								mMapView.refresh();

							}
						});
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								// System.out.println("确定"+index);
								dialog.cancel();
							}
						});
				Dialog dialog = builder.create();// 创建对话框
				dialog.show();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						BuildSetPoi.this);

				builder.setTitle("相关信息");// 获取标题
				builder.setMessage("此处为"
						+ overlayItems.get(index + 1).getTitle()
						+ overlayItems.get(index + 1).getSnippet());// 对话框内容
				builder.setIcon(R.drawable.ic_launcher);
				builder.setNegativeButton("清除该点",new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								

								FirstOverlay.this.removeItem(overlayItems
										.get(index));
								// System.out.println("检查点"+index);

								for (int i = index; i < itap; i++)
									overlayItems.add(i, overlayItems.get(i + 1));
								itap--;

								mMapView.refresh();

							}
						});
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								// System.out.println("确定"+index);
								dialog.cancel();
							}
						});
				Dialog dialog = builder.create();// 创建对话框
				dialog.show();

			}

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
