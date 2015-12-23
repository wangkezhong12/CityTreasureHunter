package com.activity.joinact;

import java.util.ArrayList;

import com.activity.context.MapApplication;
import com.activity.main.R;
import com.function.joinact.ActsListDouble;
import com.function.joinact.ActsListSingle;
import com.function.joinact.ActsListTeam;
import com.function.joinact.GetActInfo;
import com.function.team.GetTeamID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class ListSearch extends Activity implements OnScrollListener {
	private static final String TAG = "RankAct";
	private Button but_menu;
	private Button single;
	private Button team;
	private Button doublesearch;

	private ListView listView;
	private View moreView; // 加载更多页面
	// private SimpleAdapter adapter;
	ArrayAdapter<String> adapter;
	// private ArrayList<HashMap<String, String>> listData;
	private ArrayList<String> listData;
	private ArrayList<String> listid = new ArrayList<String>();
	private LinearLayout progress;
	private int lastItem;
	private int count = 20;
	private int countold;
	private PopupWindow m_popupWindow;
	private ImageView refresh;
	int offset = 20;// 跨过十个开始
	int offsetperson = 20;
	int limit = 10;// 最多加载10个
	
	int symbol = 3;// 单人赛和团体赛的标识
	String type = "single";// 默认单人赛
	String userId;
	
	String[][] activitys = null;
	String[][] person = null;
	String[][] actdouble = null;
	private View contentView;
	// GetActivity getact=new GetActivity();//获取团体赛信息
	// Getpersonal getper=new Getpersonal();//获取单人赛信息
	GetActInfo gai = new GetActInfo();
	//ActivityList actl = new ActivityList();
	ActsListSingle als = new ActsListSingle();
	ActsListDouble ald = new ActsListDouble();
	ActsListTeam alt = new ActsListTeam();
	
	String tid;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinact_listsearch);

		SearchAct.joinActActivityList.add(this);
		
		MapApplication data = ((MapApplication)getApplicationContext()); 
		userId = data.getUserId();
		
		GetTeamID gti =new  GetTeamID();
		tid =gti.GetTID(userId);

		init();
		setListener();
		
		

		listView = (ListView) findViewById(R.id.listView);
		moreView = getLayoutInflater().inflate(R.layout.joinact_load, null);
		// listData = new ArrayList<HashMap<String,String>>();
		listData = new ArrayList<String>();
		this.prepareData(); // 准备数据
		count = listData.size();

		// adapter = new SimpleAdapter(this, listData,R.layout.item,new
		// String[]{"itemText"}, new int[]{R.id.itemText});
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, listData);
		listView.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。

		listView.setAdapter(adapter); // 设置adapter
		listView.setOnScrollListener(this); // 设置listview的滚动事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long index) {
				String sname = listView.getItemAtPosition((int) index)
						.toString();
				String sid = listid.get((int) index).toString();
				Toast.makeText(ListSearch.this,
						"你点击的是第" + sname + "项 编号" + sid, Toast.LENGTH_SHORT)
						.show();
				
				Intent intent = null;
				if (type.equals("double"))
					intent = new Intent(ListSearch.this, ActInfoOfDouble.class);
				else
					intent = new Intent(ListSearch.this, ActInfo.class);
				
				// TODO 传活动ID和类型
				intent.putExtra("sid", sid);
				intent.putExtra("type", type);
				
				startActivityForResult(intent, 1);
				//startActivity(intent);
			}
		});
	}

	private void prepareData() { // 准备数据
		person = als.GetSingleActList("single", userId, "20", "0");
		//person = actl.GetActList("single",userId, "20", "0");// 先获得单人的前十个活动名
		for (int i = 0; i < person.length; i++) {
			// HashMap<String, String> map = new HashMap<String, String>();
			// map.put("itemText", person[i].toString());
			// listData.add(map);
			listid.add(person[i][0].toString());// 添加sid
			listData.add(person[i][1].toString());
		}
		count = listData.size();
	}

	private void loadMoreData() { // 加载更多数据
		count = adapter.getCount();
		if (symbol == 3) {
			String limitrows = String.valueOf(limit);
			String offsetrows = String.valueOf(offsetperson);
			person = als.GetSingleActList("single", userId, limitrows, offsetrows);
			//person = actl.GetActList("single",userId, limitrows, offsetrows);// 单人活动信息
			for (int i = 0; i < person.length; i++) {
				// HashMap<String, String> map = new HashMap<String, String>();
				// map.put("itemText", person[i].toString());
				// listData.add(map);
				listid.add(person[i][0].toString());// 添加sid
				listData.add(person[i][1].toString());
			}
			listView.setAdapter(adapter); // 设置adapter
			offsetperson += 10;// 跨过的人数加5
		} else if (symbol == 2) {
			// offset=10;
			String limitrows = String.valueOf(limit);
			String offsetrows = String.valueOf(offset);
			//activitys = actl.GetActList("team",tid, limitrows, offsetrows);// 团体活动信息
			activitys = alt.GetTeamActList("team", tid, limitrows, offsetrows);
			for (int i = 0; i < activitys.length; i++) {
				// HashMap<String, String> map = new HashMap<String, String>();
				// map.put("itemText", person[i].toString());
				// listData.add(map);
				listid.add(activitys[i][0].toString());// 添加sid
				listData.add(activitys[i][1].toString());
			}
			listView.setAdapter(adapter); // 设置adapter
			offset += 10;
		} else if (symbol == 1) {
			// offset=10;
			String limitrows = String.valueOf(limit);
			String offsetrows = String.valueOf(offset);
			actdouble =ald.GetDoubleActList("double", limitrows, offsetrows);
			//actdouble = actl.GetActList("double",userId, limitrows, offsetrows);// 双人活动信息
			for (int i = 0; i < actdouble.length; i++) {
				// HashMap<String, String> map = new HashMap<String, String>();
				// map.put("itemText", person[i].toString());
				// listData.add(map);
				listid.add(actdouble[i][0].toString());// 添加sid
				listData.add(actdouble[i][1].toString());
			}
			listView.setAdapter(adapter); // 设置adapter
			offset += 10;
		}
		count = listData.size();
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.i(TAG, "firstVisibleItem=" + firstVisibleItem
				+ "\nvisibleItemCount=" + visibleItemCount + "\ntotalItemCount"
				+ totalItemCount);
		lastItem = firstVisibleItem + visibleItemCount - 1; // 减1是因为上面加了个addFooterView
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 下拉到空闲是，且最后一个item的数等于数据的总数时，进行更新
		if (lastItem == count
				&& scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			Log.i(TAG, "拉到最底部");
			moreView.setVisibility(View.VISIBLE);
			mHandler.sendEmptyMessage(0);
		}
	}

	// 声明Handler
	private Handler mHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				loadMoreData(); // 加载更多数据，这里可以使用异步加载
				adapter.notifyDataSetChanged();
				moreView.setVisibility(View.GONE);
				if (count == countold) {
					Toast.makeText(ListSearch.this, "活动已全部显示", 3000).show();
					// listView.removeFooterView(moreView); //移除底部视图
				}
				countold = count;// 把加载完的数据存起来
			}
		};
	};

	private void init() {
		contentView = getLayoutInflater().inflate(R.layout.joinact_popupmenu,
				null, true);
		but_menu = (Button) findViewById(R.id.but_menu);
		refresh = (ImageView) findViewById(R.id.Refresh);
		progress = (LinearLayout) findViewById(R.id.progress);
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
					Toast.makeText(ListSearch.this, e.getMessage(),
							Toast.LENGTH_SHORT);
				}
			}
		});

		refresh.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				refresh.setVisibility(View.GONE);
				progress.setVisibility(View.VISIBLE);
				Toast.makeText(ListSearch.this, "正在刷新列表", Toast.LENGTH_SHORT)
						.show();
				new Handler().postDelayed(new Runnable() {
					public void run() {
						// execute the task
						refresh.setVisibility(View.VISIBLE);
						progress.setVisibility(View.GONE);
					}
				}, 2000);

				if (symbol == 3) {
					offsetperson = 20;
					listData.removeAll(listData);// 移除所有List数据，
					listid.removeAll(listid);
					// person=getper.doInBackground(20, 0);
					person = als.GetSingleActList("single", userId, "20", "0");
					for (int i = 0; i < person.length; i++) {
						listid.add(person[i][0].toString());
						listData.add(person[i][1].toString());
					}
					listView.setAdapter(adapter);
					count = listData.size();
				} else if (symbol == 2) {
					offset = 20;
					listData.removeAll(listData);// 移除所有List数据，
					listid.removeAll(listid);
					// activitys=getact.doInBackground(20, 0);
					activitys = alt.GetTeamActList("team", tid, "20", "0");
					for (int i = 0; i < activitys.length; i++) {
						listid.add(activitys[i][0].toString());
						listData.add(activitys[i][1].toString());
						// myList2.add(activitys[i]);
					}
					listView.setAdapter(adapter);
					count = listData.size();
				} else if (symbol == 1) {
					offset = 20;
					listData.removeAll(listData);// 移除所有List数据，
					listid.removeAll(listid);
					// activitys=getact.doInBackground(20, 0);
					//actdouble = actl.GetActList("double",userId, "20", "0");
					actdouble = ald.GetDoubleActList("double", "20", "0");
					for (int i = 0; i < actdouble.length; i++) {
						listid.add(actdouble[i][0].toString());
						listData.add(actdouble[i][1].toString());
						// myList2.add(activitys[i]);
					}
					listView.setAdapter(adapter);
					count = listData.size();
				}
			}
		});

		single.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				m_popupWindow.dismiss();
				but_menu.setText("单人赛≡");
				symbol = 3;// 条件参数
				offsetperson = 20;// 数据查询重新查询
				type = "single";
				// sport="sport3";
				listData.removeAll(listData);// 移除所有List数据，
				listid.removeAll(listid);
				// person=getper.doInBackground(20, 0);
				person =  als.GetSingleActList(type, userId, "20", "0");
				//person = actl.GetActList(type,userId, "20", "0");
				for (int i = 0; i < person.length; i++) {
					listid.add(person[i][0].toString());
					listData.add(person[i][1].toString());
				}
				listView.setAdapter(adapter);
				count = listData.size();
			}
		});

		team.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				m_popupWindow.dismiss();
				but_menu.setText("团体赛≡");
				symbol = 2;
				offset = 20;
				type = "team";
				// sport="sport2";
				listData.removeAll(listData);// 移除所有List数据
				listid.removeAll(listid);
				activitys = alt.GetTeamActList(type, tid, "20", "0");
				//activitys = actl.GetActList(type,tid, "20", "0");
				// activitys=getact.doInBackground(20, 0);//默认显示的是团体赛
				for (int i = 0; i < activitys.length; i++) {
					listid.add(activitys[i][0].toString());
					listData.add(activitys[i][1].toString());
				}
				listView.setAdapter(adapter);
				count = listData.size();

			}
		});

		doublesearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				m_popupWindow.dismiss();
				but_menu.setText("双人互找≡");
				symbol = 1;
				offset = 20;
				type = "double";
				// sport="sport2";
				listData.removeAll(listData);// 移除所有List数据
				listid.removeAll(listid);
				actdouble = ald.GetDoubleActList(type, "20", "0");
				//activitys = actl.GetActList(type,userId, "20", "0");
				// activitys=getact.doInBackground(20, 0);//默认显示的是团体赛
				for (int i = 0; i < actdouble.length; i++) {
					listid.add(actdouble[i][0].toString());
					listData.add(actdouble[i][1].toString());
				}
				listView.setAdapter(adapter);
				count = listData.size();

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
}