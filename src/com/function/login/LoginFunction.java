package com.function.login;

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

public class LoginFunction {
	
	UrlIP ip = new UrlIP();
	public String Loginserve(String username ,String password){
		String url =ip.GetUtilIP()+ "CityTreasureHunterServlet/servlet/LoginServlet";

		String id = "";
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
				id=EntityUtils.toString(response.getEntity());

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		return id;
		
		
	}
	
	public boolean taken(String username,String password){
		boolean b=false;
		if(!(Loginserve(username,password).equals("-1"))){
			b=true;
		}
		return b;
	}
}
