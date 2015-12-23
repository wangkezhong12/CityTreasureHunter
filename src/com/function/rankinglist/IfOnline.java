package com.function.rankinglist;

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

public class IfOnline {

	public String doInBackground(String sid,String sport)
	{
		CharSequence sidout="";
		CharSequence sportout="";
		String result="";
		sidout= sid.toString();
		sportout= sport.toString();
		try{
			//Toast.makeText(a, "3", Toast.LENGTH_SHORT).show();
			HttpPost httpRequest =new HttpPost(HttpUtil.IFONLINE_URL);
			//请求数据
			
			if(!sidout.equals("")){
				//创建参数
				//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
				List<NameValuePair> params=new ArrayList<NameValuePair>();
		
				params.add(new BasicNameValuePair("sid",sidout.toString()));
				params.add(new BasicNameValuePair("sport",sportout.toString()));

				//对提交数据进行编码，设置编码方式
				httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				GetHeaderIP ghp = new GetHeaderIP();
				httpRequest.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
				//Toast.makeText(a, "2", Toast.LENGTH_SHORT).show();
				httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
				
				//获得响应服务器的数据
				if(httpResponse.getStatusLine().getStatusCode()==200){

					//利用字节数组流和包装的绑定数据
					
					//利用字节数组流和包装的绑定数据
					byte[] data =new byte[2048];
					//先把从服务端传来的数据转化为字节数组
					
					data =EntityUtils.toByteArray((HttpEntity)httpResponse.getEntity());
					
					//再创建字节数组输入流对象
					ByteArrayInputStream bais = new ByteArrayInputStream(data);
					//绑定字节流和数据包流
					DataInputStream dis = new DataInputStream(bais);
					//将字节数组中的数据还原成原来的各种数据类型，代码如下：
					result =new String(dis.readUTF());
					//Toast.makeText(MainActivity.this, "!!!", Toast.LENGTH_SHORT).show();					
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
		return result;
	}	
}

