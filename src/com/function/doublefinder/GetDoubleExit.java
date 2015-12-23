package com.function.doublefinder;

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


public class GetDoubleExit {
	public String doInBackground(String sid)
	{
		//String json="";
		CharSequence sportid="";
		String rest="";
		sportid=sid.toString();
		try{
			//Toast.makeText(a, "3", Toast.LENGTH_SHORT).show();
			HttpPost httpRequest =new HttpPost(HttpUtil.GETDOUBLEEXIT_URL);
			//请求数据
			
			//创建参数
			//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
			List<NameValuePair> params=new ArrayList<NameValuePair>();
		
			params.add(new BasicNameValuePair("sid",sportid.toString()));
			//对提交数据进行编码，设置编码方式
				httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				//Toast.makeText(a, "2", Toast.LENGTH_SHORT).show();
				GetHeaderIP ghp = new GetHeaderIP();
				httpRequest.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
				httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
				
				//获得响应服务器的数据
				if(httpResponse.getStatusLine().getStatusCode()==200)
				{
					/*try {
						json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");  
						JSONArray jsonArray = new JSONObject(json).getJSONArray("strings"); 
					//数据直接为一个数组形式，所以可以直接 用android提供的框架JSONArray读取JSON数据，转换成Array  	
							   JSONObject item = ((JSONObject)jsonArray.opt(0)); 

						        // 获取对象对应的值      
								rest= item.getString("exitdoublename");  
			         
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //每条记录又由几个Object对象组成 */
					byte[] data =new byte[2048];
					//先把从服务端传来的数据转化为字节数组
					
					data =EntityUtils.toByteArray((HttpEntity)httpResponse.getEntity());
					
					//再创建字节数组输入流对象
					ByteArrayInputStream bais = new ByteArrayInputStream(data);
					
					//绑定字节流和数据包流
					DataInputStream dis = new DataInputStream(bais);
					//Toast.makeText(FindpswdActivity,"!!!", Toast.LENGTH_SHORT).show();
					//将字节数组中的数据还原成原来的各种数据类型，代码如下：
					//result="!!!!";
					rest =new String(dis.readUTF());
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
		return rest;
	}	
}

