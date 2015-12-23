package com.function.personal;

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

public class ChangePasswd {
	UrlIP ip = new UrlIP();

	public String ChangePW(String userid ,String opasswd,String newpasswd){
		String url = ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/ChangePasswdServlet";

		String message = null;
		HttpPost request=new HttpPost(url);
		GetHeaderIP ghp = new GetHeaderIP();
		request.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		if(!userid.equals("")&&!newpasswd.equals("")){
			List<NameValuePair>params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid",userid));
			params.add(new BasicNameValuePair("opasswd",opasswd));
			params.add(new BasicNameValuePair("newpasswd",newpasswd));

			try{
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				HttpClient httpclient=new DefaultHttpClient();
				HttpResponse response=httpclient.execute(request);
				if(response.getStatusLine().getStatusCode()==200){
					message=EntityUtils.toString(response.getEntity());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return message;	
	}
	
	public boolean GetChanged(String userid,String opasswd,String newpasswd){
		boolean b=false;
		if(ChangePW(userid,opasswd,newpasswd).equals("yes")){
			b = true;
		}
		return b;
	}
}
