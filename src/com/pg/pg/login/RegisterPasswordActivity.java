package com.pg.pg.login;

import java.util.ArrayList;
import java.util.List;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_user;
import com.pg.pg.json.WriteJson;
import com.pg.pg.main.MainActivity;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.tools.Operaton;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPasswordActivity extends Activity {
	private Button queding;
	private EditText passwd;
	private EditText passwdqueding;
	String passwdstr;
	String passwdquedingstr;
	String phoneNumber;
	String jsonString;
	private LoadingProgressDialog dialog;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_register_password);
		 queding = (Button)findViewById(R.id.zhucequeding);
	     passwd = (EditText)findViewById(R.id.passwdzhucetext);
	     passwdqueding = (EditText)findViewById(R.id.passwdzhucequedingtext);
	     phoneNumber = this.getIntent().getStringExtra("phoneNumber");
	     queding.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 passwdstr = passwd.getText().toString().trim();
				 passwdquedingstr = passwdqueding.getText().toString().trim();
				 if(!TextUtils.isEmpty(passwdstr)&&!TextUtils.isEmpty(passwdquedingstr)){		
					 if(passwdstr.trim().equals(passwdquedingstr.trim())){
						 new UserRegisterAsyncTask().execute(new String[]{phoneNumber,passwdstr});		
					 }else{
						 Toast.makeText(getApplicationContext(), "密码不一致！", Toast.LENGTH_SHORT).show();
					 }								 
				 }else{
					 Toast.makeText(getApplicationContext(), "密码不能为空！", Toast.LENGTH_SHORT).show();
				 }
			 }
		 });
		//初始化dialog
		 dialog=new LoadingProgressDialog(this,"正在加载...");
	 }
	 
		/**
		 * dis：AsyncTask参数类型：
		 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
		 * 第二个参数表示进度的刻度
		 * 第三个参数表示返回的结果类型
		 * */
		private class UserRegisterAsyncTask extends AsyncTask<String, String, String>{
			//任务执行之前的操作
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				dialog.show();//显示dialog，数据正在处理....
			}
			//完成耗时操作
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				try{
					Pgdr_user user=new Pgdr_user();
					user.setUser_mobile(params[0]);
					user.setUser_password(params[1]);
					//构造一个user对象
					List<Pgdr_user> list=new ArrayList<Pgdr_user>();
					list.add(user);
					WriteJson writeJson=new WriteJson();
					//将user对象写出json形式字符串
					jsonString= writeJson.getJsonData(list);
                    System.out.println(jsonString); 
					Operaton operaton=new Operaton();
	 				String result=operaton.UpData("Register", jsonString);
	 				return result;
				}catch(Exception e){
					e.printStackTrace();
					return "";
				}
			}
			
			@Override
			protected void onProgressUpdate(String... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
				
			}
			
			//数据处理完毕后更新UI操作
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(result.equals("yes")){
					Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplication(), MainActivity.class);
					intent.setClass(RegisterPasswordActivity.this, MainActivity.class);
					startActivity(intent);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);  					
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				}else if("".equals(result)){
					Toast.makeText(getApplicationContext(), "注册失败，请重新注册！", Toast.LENGTH_SHORT).show();
				}
				dialog.dismiss();//dialog关闭，数据处理完毕
			}
		}
}
