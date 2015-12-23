package com.function.joinact;

//创建活动的测试
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.function.UrlIP.GetHeaderIP;


public class GetTeamTotal {
	
	public String doInBackground(Object... params_obj)
	{
		String result="";
		CharSequence sid="";
		sid=params_obj[0].toString();
		try{
			HttpPost httpRequest =new HttpPost(HttpUtil.GETTEAMTOTAL_URL);
		if(!sid.equals(""))
		{                                                 //&&!sdescribe.equals("")
			
			//请求数据
			
			//创建参数
			//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("sid",sid.toString()));
			//params.add(new BasicNameValuePair("flag","0"));
			
			
			    
			  //对提交数据进行编码，设置编码方式
				httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				GetHeaderIP ghp = new GetHeaderIP();
				httpRequest.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
				httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
				//Toast.makeText(FindpswdActivity, "!!!", Toast.LENGTH_SHORT).show();
				//获得响应服务器的数据
				if(httpResponse.getStatusLine().getStatusCode()==200)
				{
					
					//利用字节数组流和包装的绑定数据
					byte[] data =new byte[2048];
					//先把从服务端传来的数据转化为字节数组
					
					data =EntityUtils.toByteArray((HttpEntity)httpResponse.getEntity());
					
					//再创建字节数组输入流对象
					ByteArrayInputStream bais = new ByteArrayInputStream(data);
					
					//绑定字节流和数据包流
					DataInputStream dis = new DataInputStream(bais);
					result =new String(dis.readUTF());
				}
				
				
			}
		
	}
		catch(ClientProtocolException e){
			e.printStackTrace();
			
		}catch(UnsupportedEncodingException e){ 
			e.printStackTrace();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return result;

}

}

