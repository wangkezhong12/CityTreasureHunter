package com.activity.joinact;

import java.util.ArrayList;
import java.util.HashMap;

import com.activity.context.MapApplication;
import com.activity.main.R;
import com.function.joinact.GetActTeam;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Team extends Activity implements OnScrollListener{
	private static final String TAG = "Team";	
	private ImageView refresh;	
	private ListView listView;	
	private View moreView; //加载更多页面
	//private SimpleAdapter adapter;
	ArrayAdapter<String> adapter;
	//private ArrayList<HashMap<String, String>> listData;
	private ArrayList<String> listData;
	private ArrayList<String> listid=new ArrayList<String>();//id
	private LinearLayout progress;
	private ImageButton build;
	private int lastItem;
    private int count=20;
    private int countold;
    int offset=20;//跨过十个开始
	int offsetteam=20;
	int limit=10;//最多加载10个
	
	String[][] actteam=null;
	GetActTeam getactteam=new GetActTeam();
    String sid;//活动id
		
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joinact_selectteam);
		
		SearchAct.joinActActivityList.add(this);
		
		MapApplication data = ((MapApplication)getApplicationContext());
		sid = data.getActId();
		
		refresh = (ImageView) this.findViewById(R.id.Refresh);
		progress = (LinearLayout) this.findViewById(R.id.progress);
		listView = (ListView)this.findViewById(R.id.listView);
        moreView = getLayoutInflater().inflate(R.layout.joinact_load, null);
        //listData = new ArrayList<HashMap<String,String>>();
        listData = new ArrayList<String>();
        build = (ImageButton)this.findViewById(R.id.buildteam);
        prepareData(); //准备数据
        count = listData.size();    
        //adapter = new SimpleAdapter(this, listData,R.layout.joinact_item,new String[]{"itemText"}, new int[]{R.id.itemText});
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listData);

        listView.addFooterView(moreView); //添加底部view，一定要在setAdapter之前添加，否则会报错。        
        listView.setAdapter(adapter); //设置adapter
        listView.setOnScrollListener(this); //设置listview的滚动事件		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long index){
				// TODO Auto-generated method stub	
				String tname = listView.getItemAtPosition((int) index).toString();
				String tid = listid.get((int)index).toString();
				Toast.makeText(Team.this, "你点击的是第" + tname + "项 编号"+tid, Toast.LENGTH_SHORT).show();		
				Intent intent=new Intent(Team.this,TeamInfo.class); 
				intent.putExtra("tid",tid);
				intent.putExtra("tname", tname);  
				startActivity(intent);
			}			
		});		
		
		refresh.setOnClickListener(new View.OnClickListener(){			
			@Override
			public void onClick(View arg0){
				// TODO Auto-generated method stub
				refresh.setVisibility(View.GONE);
				progress.setVisibility(View.VISIBLE);
				Toast.makeText(Team.this, "正在刷新列表", Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable(){  
				     public void run(){  
				     //execute the task
				    	 refresh.setVisibility(View.VISIBLE);
				    	 progress.setVisibility(View.GONE);
				     }  
				  }, 2000);	
				offsetteam=20;
				listData.removeAll(listData);//移除所有List数据，
				listid.removeAll(listid);
				actteam=getactteam.doInBackground(sid.toString(),20, 0);//暂时用sid为1来试
				for(int i=0;i<actteam.length;i++)
				{
					listid.add(actteam[i][0].toString());
					listData.add(actteam[i][1].toString());
				}
				listView.setAdapter(adapter);
				count = listData.size();
			}
		});
		
		build.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				//Toast.makeText(Team.this, "11", Toast.LENGTH_SHORT).show();
				Intent build = new Intent(Team.this,BuildTeam.class);
				startActivity(build);
			}
		});
	}

	private void prepareData(){  //准备数据
		actteam=getactteam.doInBackground(sid.toString(),20, 0);//暂时用sid为1来试
    	for(int i=0;i<actteam.length;i++){
    		//HashMap<String, String> map = new HashMap<String, String>();
    		//map.put("itemText", "队伍"+i);
    		//listData.add(map);
    		listid.add(actteam[i][0].toString());//添加sid
    		listData.add(actteam[i][1].toString());
    	} 
    	count = listData.size();
    }
    
    private void loadMoreData(){ //加载更多数据
    	 count = adapter.getCount(); 
    	 actteam=getactteam.doInBackground(sid.toString(),limit, offsetteam);//单人活动信息
		 for(int i=0;i<actteam.length;i++){
	    		//HashMap<String, String> map = new HashMap<String, String>();
	    		//map.put("itemText", person[i].toString());
	    		//listData.add(map);
			 listid.add(actteam[i][0].toString());//添加sid
			 listData.add(actteam[i][1].toString());
	    	}
		 listView.setAdapter(adapter); //设置adapter
		 offsetteam+=10;//跨过的人数加5
    	count = listData.size();
    }

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){		
		Log.i(TAG, "firstVisibleItem="+firstVisibleItem+"\nvisibleItemCount="+visibleItemCount+"\ntotalItemCount"+totalItemCount);	 	
		lastItem = firstVisibleItem + visibleItemCount - 1;  //减1是因为上面加了个addFooterView		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState){ 
		Log.i(TAG, "scrollState="+scrollState);
		//下拉到空闲是，且最后一个item的数等于数据的总数时，进行更新
		if(lastItem == count  && scrollState == this.SCROLL_STATE_IDLE){ 
			Log.i(TAG, "拉到最底部");
			moreView.setVisibility(view.VISIBLE);		 
		    mHandler.sendEmptyMessage(0);			 
		}		
	}
	//声明Handler
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			if(msg.what==0){			     
				try{
					Thread.sleep(3000);
				}catch (InterruptedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    loadMoreData();  //加载更多数据，这里可以使用异步加载
			    adapter.notifyDataSetChanged();
			    moreView.setVisibility(View.GONE); 			    
			    if(count==countold){
			    	Toast.makeText(Team.this, "参赛队伍已全部显示", 3000).show();
			        //listView.removeFooterView(moreView); //移除底部视图
			    }
			    countold=count;//把加载完的数据存起来
			}
		};
	};    
}
