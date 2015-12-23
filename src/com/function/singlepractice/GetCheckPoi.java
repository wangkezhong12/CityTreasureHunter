package com.function.singlepractice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.function.UrlIP.GetHeaderIP;

public class GetCheckPoi {
	
	public String[][] doInBackground(Object... params_obj)
	{
		String[][] rest=null;
		String json="";
		CharSequence sid="";//这里用1来试
		CharSequence stype="";
		sid=params_obj[0].toString();
		stype=params_obj[1].toString();
		
		try{
			HttpPost httpRequest =new HttpPost(HttpUtil.GETCHECKPOINTINFO_URL);
			if(!sid.equals("")&&!stype.equals("")){
			
				//请求数据
			
				//创建参数
				//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
				List<NameValuePair> params=new ArrayList<NameValuePair>();
		
				params.add(new BasicNameValuePair("sid",sid.toString()));
				params.add(new BasicNameValuePair("stype",stype.toString()));
				//对提交数据进行编码，设置编码方式
				httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				GetHeaderIP ghp = new GetHeaderIP();
				httpRequest.setHeader("X-Online-Host", ""+ghp.GetIP()+":8080");
				httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
				//Toast.makeText(FindpswdActivity, "!!!", Toast.LENGTH_SHORT).show();
				//获得响应服务器的数据
				if(httpResponse.getStatusLine().getStatusCode()==200){
					//利用字节数组流和包装的绑定数据
					try {
						json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");  
						JSONArray jsonArray = new JSONObject(json).getJSONArray("strings"); 
					    rest=new String[jsonArray.length()][4];
					
					    //数据直接为一个数组形式，所以可以直接 用android提供的框架JSONArray读取JSON数据，转换成Array  
					    for (int i = 0; i < jsonArray.length(); i++) {  
					    	JSONObject item = ((JSONObject)jsonArray.opt(i)); 

					        // 获取对象对应的值      
							rest[i][0]= item.getString("clat");  
			                rest[i][1]= item.getString("clon");
							rest[i][2]= item.getString("cquestion");  
			                rest[i][3]= item.getString("canswer"); 
		                
					    }   
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //每条记录又由几个Object对象组成 
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
		return rest;
	}	
}

