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

public class Signup {
	UrlIP ip = new UrlIP();
	private String Sigupserve(String username ,String password,String cfpassword){
		String url = ip.GetUtilIP()+"CityTreasureHunterServlet/servlet/SignupServlet";
		String message = null;
		HttpPost request=new HttpPost(url);
		GetHeaderIP ghp = new GetHeaderIP();
		request.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		if(!username.equals("")&&!password.equals("")&&password.equals(cfpassword)){   //设定用户id长度大于大于4位
			List<NameValuePair>params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username",username));
			params.add(new BasicNameValuePair("password",password));
			params.add(new BasicNameValuePair("cfpassword",cfpassword));		
		try{
			request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpClient httpclient=new DefaultHttpClient();
			HttpResponse response=httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				message=EntityUtils.toString(response.getEntity());
				System.out.print(message);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		else
			message = "no";
		return message;	
	}
	
	public boolean Insert(String userid,String password,String cfpassword){
		boolean b=false;
		if(Sigupserve(userid,password,cfpassword).equals("yes")){
			b=true;
		}
		return b;
	}
}
