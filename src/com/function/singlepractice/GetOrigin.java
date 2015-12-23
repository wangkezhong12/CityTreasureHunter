package com.function.singlepractice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.function.UrlIP.GetHeaderIP;

public class GetOrigin {

	public String[] doInBackground(String pro,String sid)
	{
		String json="";
		String sports="";
		CharSequence proinfo="";
		CharSequence sportsid="";
		String[] rest=null;
		proinfo=pro.toString();
		sportsid=sid.toString();
		if(proinfo.equals("single"))
			sports="sport3";
		if(proinfo.equals("team"))
			sports="sport2";
		if(proinfo.equals("double"))
			sports="sport1";
		try{
			//Toast.makeText(a, "3", Toast.LENGTH_SHORT).show();
			HttpPost httpRequest =new HttpPost(HttpUtil.GETORIGIN_URL);
			//请求数据
			
			//创建参数
			//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
			List<NameValuePair> params=new ArrayList<NameValuePair>();
		
			params.add(new BasicNameValuePair("sports",sports.toString()));
			params.add(new BasicNameValuePair("sid",sportsid.toString()));
			
			//对提交数据进行编码，设置编码方式
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//Toast.makeText(a, "2", Toast.LENGTH_SHORT).show();
			GetHeaderIP ghp = new GetHeaderIP();
			httpRequest.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
			httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
				
			//获得响应服务器的数据
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//利用字节数组流和包装的绑定数据				
				try {
					json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");  
						
					//result=json.toString()+""; 
					//下面是获得json数组的解析
					JSONArray jsonArray = new JSONObject(json).getJSONArray("strings"); 
				    rest=new String[2];
					//数据直接为一个数组形式，所以可以直接 用android提供的框架JSONArray读取JSON数据，转换成Array  
						
				    JSONObject item = ((JSONObject)jsonArray.opt(0)); 

				    // 获取对象对应的值      
					rest[0]= item.getString("slat");   
					rest[1]= item.getString("slon");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //每条记录又由几个Object对象组成 
			}	
		}
		catch(ClientProtocolException e){
			e.printStackTrace();
			
		}catch(UnsupportedEncodingException e){ 
			e.printStackTrace();
			//Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
			//Toast.makeText(FindpswdActivity, "2", Toast.LENGTH_SHORT).show();
			
		}catch (IOException e) {
			e.printStackTrace();
			//Toast.makeText(FindpswdActivity, "3", Toast.LENGTH_SHORT).show();
		}
		return rest;
	}	
}
