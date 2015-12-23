package com.function.login;

import java.io.IOException;
import java.io.StringReader;
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

import com.function.UrlIP.GetHeaderIP;
import com.function.UrlIP.UrlIP;
import com.google.gson.stream.JsonReader;


public class GetPerAndTeInfo {
	
	UrlIP ip = new UrlIP();
	
	public  String GetUID(String username ,String password){
		String url =ip.GetUtilIP()+ "CityTreasureHunterServlet/servlet/GetUserIDServlet";

		String userid = null;
		HttpPost request=new HttpPost(url);
		GetHeaderIP ghp = new GetHeaderIP();
		request.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		if(!username.equals("")&&!password.equals("")){
			List<NameValuePair>params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username",username));
			params.add(new BasicNameValuePair("password",password));
		
		
		try{
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient httpclient=new DefaultHttpClient();
			HttpResponse response=httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				userid=EntityUtils.toString(response.getEntity());

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		System.out.print(userid);
		return userid;

	}
	

	
	public  String GetTID(String uid){
		String url =ip.GetUtilIP()+ "CityTreasureHunterServlet/servlet/GetTeamIDServlet";

		String teamid = null;
		HttpPost request=new HttpPost(url);
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		if(!uid.equals("")){
			List<NameValuePair>params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uid",uid));
		
		
		try{
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient httpclient=new DefaultHttpClient();
			HttpResponse response=httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				teamid=EntityUtils.toString(response.getEntity());

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		System.out.print(teamid);
		return teamid;

	}
	
}
