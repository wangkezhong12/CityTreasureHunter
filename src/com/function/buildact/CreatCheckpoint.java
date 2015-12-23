package com.function.buildact;
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

public class CreatCheckpoint{
	public String doInBackground(Object... params_obj){
		String result="";
		CharSequence pro="";//此为判断哪个sport表
		CharSequence sports="";//判断完毕后赋值
		
		CharSequence cquestion="";
		CharSequence canswer="";
		CharSequence clat="";
		CharSequence clon="";
		//以上内容为sport表的全部信息
		pro=params_obj[0].toString();
		if(pro.equals("单人赛"))
			sports="sport3";
		if(pro.equals("团体赛"))
			sports="sport2";
		if(pro.equals("双人互找"))
			sports="sport1";
		cquestion=params_obj[1].toString();
		canswer=params_obj[2].toString();
		clat=params_obj[3].toString();
		clon=params_obj[4].toString();
		try	{
			HttpPost httpRequest =new HttpPost(HttpUtil.CREATCHECKPOINT_URL);
			if(!pro.equals("")&&!cquestion.equals("")&&!canswer.equals("")
				&&!clat.equals("")&&!clon.equals("")){
		
				List<NameValuePair> params=new ArrayList<NameValuePair>();
					
				params.add(new BasicNameValuePair("sports",sports.toString()));
				params.add(new BasicNameValuePair("cquestion",cquestion.toString()));
				params.add(new BasicNameValuePair("canswer",canswer.toString()));
				params.add(new BasicNameValuePair("clat",clat.toString()));
				params.add(new BasicNameValuePair("clon",clon.toString()));
		
				httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				GetHeaderIP ghp = new GetHeaderIP();
				httpRequest.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
				httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
				//Toast.makeText(FindpswdActivity, "!!!", Toast.LENGTH_SHORT).show();
				//获得响应服务器的数据
				if(httpResponse.getStatusLine().getStatusCode()==200){
					
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

