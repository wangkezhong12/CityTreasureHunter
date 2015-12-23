package com.function.joinact;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
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
import com.function.UrlIP.UrlIP;

public class ActsListSingle {
	UrlIP ip = new UrlIP();
	private String GetJsonStr(String ns ,String uid,String limitrows,String offsetrows){
		
		String jsonstr = null;
		String url =  ip.GetUtilIP()+ "CityTreasureHunterServlet/servlet/ActsListSingleServlet";

		HttpPost request=new HttpPost(url);
		GetHeaderIP ghp = new GetHeaderIP();
		request.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		List<NameValuePair>params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ns",ns));
		params.add(new BasicNameValuePair("uid",uid));
		params.add(new BasicNameValuePair("offsetrows",offsetrows));
		params.add(new BasicNameValuePair("limitrows",limitrows));
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
	
	public String[][] GetSingleActList(String type,String uid,String limitrows,String offsetrows){
		String json = null;
		
		String[][] rest=null;
		String ns  = null;
		if(type.equals("single")){
			ns="3";
		}
		
		json = GetJsonStr(ns,uid,limitrows,offsetrows);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONObject(json).getJSONArray("strings");
			 rest=new String[jsonArray.length()][2];
			 for (int i = 0; i < jsonArray.length(); i++) {  
				 
				 JSONObject item = ((JSONObject)jsonArray.opt(i)); 

				   // 获取对象对应的值      
				rest[i][0]= item.getString("s_id");   
				rest[i][1]= item.getString("s_name");
              
          }   
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   
		return rest;
	}
	
	
	
}

