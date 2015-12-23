package com.function.team;

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

public class GetTeamID {
	UrlIP ip = new UrlIP();
	public String GetTID(String uid){
		String url = ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/GetTeamIDServlet";
		String tid ="";
		HttpPost request=new HttpPost(url);
		GetHeaderIP ghp = new GetHeaderIP();
		request.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		if(!uid.equals("")){   //设定用户id长度大于大于4位
			List<NameValuePair>params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uid",uid));
					
		try{
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient httpclient=new DefaultHttpClient();
			HttpResponse response=httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				tid=EntityUtils.toString(response.getEntity());
				System.out.print(tid);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		return tid;
	}
}
