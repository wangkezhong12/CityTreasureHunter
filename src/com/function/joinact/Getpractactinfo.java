package com.function.joinact;


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



public class Getpractactinfo {
	public String[] doInBackground(String sid){
		String json="";
		CharSequence sidout="";
		String[] rest=null;
		sidout= sid.toString();
		try{
			HttpPost httpRequest =new HttpPost(HttpUtil.GETPRACTACTINFO_URL);
			if(!sidout.equals("")){
				//创建参数
				//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("sid",sidout.toString()));
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
						JSONArray jsonArray = new JSONObject(json).getJSONArray("strings"); 
				        rest=new String[9];
				        JSONObject item = ((JSONObject)jsonArray.opt(0)); 
						   
						   // 获取对象对应的值      
						   rest[0]= item.getString("uname");   
						   rest[1]= item.getString("slat");
						   rest[2]= item.getString("slon");
						   rest[3]= item.getString("sstarttime");   
						   rest[4]= item.getString("slasttime");
						   rest[5]= item.getString("slimittime");
						   rest[6]= item.getString("stotal"); 
						   rest[7]= item.getString("sdescribe");
						   rest[8]= item.getString("salertnum");

		
					
					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //每条记录又由几个Object对象组成 
				}
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

