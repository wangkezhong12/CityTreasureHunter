package com.function.findpassword;
//找回密码的异步显示方法，但是，不能在第二个activity上显示进度条，是为什么？
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

import com.activity.main.LoginFindPassword;
import com.function.UrlIP.GetHeaderIP;

import android.os.AsyncTask;
import android.widget.EditText;

public class GetpassWord extends AsyncTask<Object, Object, Object>{
	
	protected void onPreExecute() {
	}

	protected Object doInBackground(Object... params_obj)//(EditText et_ques,EditText tv_ans,Context FindpswdActivity)
	{
		String result="";
		CharSequence username="";//这里用1来试
		CharSequence question="";
		CharSequence answer="";
		username=((EditText) params_obj[0]).getText().toString();
		question=((EditText) params_obj[1]).getText().toString();
		answer=((EditText) params_obj[2]).getText().toString();
		
		try{
			HttpPost httpRequest =new HttpPost(HttpUtil.FINDPASSWORD_URL);
			if(!username.equals("")&&!question.equals("")&&!answer.equals("")){
			
				//请求数据
				//创建参数
				//使用NameValuePair类来保存键值对，使用NameValuePair类是因为下面需要的那个类的参数要求
				List<NameValuePair> params=new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("username",username.toString()));
				params.add(new BasicNameValuePair("question",question.toString()));
				params.add(new BasicNameValuePair("answer",answer.toString()));
				//params.add(new BasicNameValuePair("flag","0"));
    
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
					result =new String(dis.readUTF());
					
					System.out.println(result.toString());
					//Toast.makeText(FindpswdActivity,"!!!", Toast.LENGTH_SHORT).show();
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
	protected void onPostExecute(Object result) {

		if(result.toString().equals(" "))
			LoginFindPassword.adialog.show();
		else
			LoginFindPassword.show.setText("您的密码："+result.toString());
		result=" ";
	}	
}

