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

import com.function.UrlIP.GetHeaderIP;
import com.function.UrlIP.UrlIP;

public class GetActInfo {
	UrlIP ip = new UrlIP();
	public String Getactinfo(String sid ,String type){
		String url = ip.GetUtilIP()+ "CityTreasureHunterServlet/servlet/SearchActivityFList";
		String jsonstr=null;
		HttpPost request=new HttpPost(url);
		GetHeaderIP ghp = new GetHeaderIP();
		request.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		String ns  = null;
		if(type.equals("double")){
			ns = "1";
		}
		else if(type.equals("team")){
			ns = "2";
		}
		else if(type.equals("single")){
			ns = "3";
		}
		
		List<NameValuePair>params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sid",sid));
		params.add(new BasicNameValuePair("ns",ns));
		
		try{
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient httpclient=new DefaultHttpClient();
			HttpResponse response=httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				jsonstr=EntityUtils.toString(response.getEntity());	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println(jsonstr);
		return jsonstr;		
		
	}
	
	
	public String GetactinfoDou(String sid ,String type){
		String url = ip.GetUtilIP()+ "CityTreasureHunterServlet/servlet/DouActInfoServlet";
		String jsonstr = null;
		HttpPost request=new HttpPost(url);
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		String ns  = null;
		if(type.equals("double")){
			ns = "1";
		}
		else if(type.equals("team")){
			ns = "2";
		}
		else if(type.equals("single")){
			ns = "3";
		}
		
		List<NameValuePair>params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sid",sid));
		params.add(new BasicNameValuePair("ns",ns));
		
		try{
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient httpclient=new DefaultHttpClient();
			HttpResponse response=httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				jsonstr=EntityUtils.toString(response.getEntity());	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(jsonstr);
		return jsonstr;		
		
	}

	
}