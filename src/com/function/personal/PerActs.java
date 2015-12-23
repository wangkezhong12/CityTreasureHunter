package com.function.personal;


import org.apache.http.client.methods.HttpPost;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.function.UrlIP.GetHeaderIP;
import com.function.UrlIP.UrlIP;

public class PerActs {
	UrlIP ip = new UrlIP();
	private String GetJsonStr(String uid){
	
	
		String jsonstr = null;
		String url = ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/PerActsServlet";

		HttpPost request=new HttpPost(url);
		GetHeaderIP ghp = new GetHeaderIP();
		request.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		List<NameValuePair>params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uid",uid));
	
	
		try {
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			
			HttpClient httpclient=new DefaultHttpClient();
			HttpResponse response=httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				jsonstr=EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonstr;
	}
	
	public String[] GetActList(String uid){
		String json = null;
		
		String[] rest=null;
		
		json = GetJsonStr(uid);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONObject(json).getJSONArray("strings");
			 rest=new String[jsonArray.length()];
			 for (int i = 0; i < jsonArray.length(); i++) {  
				 
				 JSONObject item = ((JSONObject)jsonArray.opt(i)); 

				   // 获取对象对应的值      
				rest[i]= item.getString("sname");   
			
              
          }   
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   
		return rest;
	}
	
	
	
}
