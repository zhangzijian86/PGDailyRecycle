package com.pg.pg.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.pg.pg.R;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.tools.Operaton;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private Button btqueding;
	private Button yanzhengmaBtn;
	private EditText shoujihaoma;
	private EditText yanzhengma;
	private ImageView tu;
	private LoadingProgressDialog dialog;
	private String yanzhengmaReturn;
	private Timer timer;
	private int miao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		btqueding = (Button) findViewById(R.id.queding);
		yanzhengmaBtn = (Button) findViewById(R.id.yanzhengmaBtn);
		btqueding.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String phoneNumber = shoujihaoma.getText().toString().trim();
				String yanZhengMa = yanzhengma.getText().toString().trim();
				if(!TextUtils.isEmpty(phoneNumber)&&!TextUtils.isEmpty(yanZhengMa)){
					new UserRegisterAsyncTask().execute(new String[]{phoneNumber,yanZhengMa});
				}else{
					Toast.makeText(getApplicationContext(), "手机号或者验证码不能为空！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		yanzhengmaBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String phoneNumber = shoujihaoma.getText().toString().trim();
				new UserRegisterYanZhengMaAsyncTask().execute(new String[]{phoneNumber});
			}
		});
		
		shoujihaoma = (EditText) findViewById(R.id.shoujihaoma);
		yanzhengma = (EditText) findViewById(R.id.yanzhengma);
		tu = (ImageView) findViewById(R.id.tu);
		tu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		//初始化dialog
		dialog=new LoadingProgressDialog(this,"正在加载...");
		//初始化dialog end
		miao = 60;
		timer = new Timer(true);
	}
	
	final Handler handler = new Handler(){  
		public void handleMessage(Message msg) {  
			switch (msg.what) {      
			case 1:      
				miao = miao - 1;
				if(miao>=0){
					yanzhengmaBtn.setText("    "+miao+"    ");
					yanzhengmaBtn.setEnabled(false);
				}else{
					yanzhengmaBtn.setText("验证码");
					yanzhengmaBtn.setEnabled(true);
					timer.cancel();
					miao = 60;
				}
				break;      
			}      
			super.handleMessage(msg);  
		}    
	};
	
	TimerTask task = new TimerTask(){  
		public void run() {  
			Message message = new Message();      
			message.what = 1;      
			handler.sendMessage(message);    
		}  
	}; 
	
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
//				Map<String,String> userInforMap = new HashMap<String,String>();
//				userInforMap.put("phoneNumber", params[0]);
//				userInforMap.put("yanZhengCode", params[1]);
//				String jsonResult = HttpUtils.doPost("/anyCare/userRegister.action", userInforMap);
//				if(jsonResult!=null&&!"".equals(jsonResult)){
//					return jsonResult;
//				}else{
//					return "";
//				}
				return "";
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
			if(result.length()==32){
				Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
//				saveUserInfor(result);
//				startActivity(new Intent(getApplication(), AnyCareRegister2Activity.class));
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}else if("0".equals(result)){
				Toast.makeText(getApplicationContext(), "手机号已注册！", Toast.LENGTH_SHORT).show();
			}else if("".equals(result)){
				Toast.makeText(getApplicationContext(), "注册失败，请重新注册！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
		}
	}
	
	/**
	 * dis：AsyncTask参数类型：
	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
	 * 第二个参数表示进度的刻度
	 * 第三个参数表示返回的结果类型
	 * */
	private class UserRegisterYanZhengMaAsyncTask extends AsyncTask<String, String, String>{
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
				Operaton operaton=new Operaton();
 				String result=operaton.checkPhoneNumber("Check", params[0]);
 				return result;
//				Map<String,String> userInforMap = new HashMap<String,String>();
//				userInforMap.put("phoneNumber", params[0]);
//				String jsonResult = HttpUtils.doPost("/anyCare/userCaptchaByPhone.action", userInforMap);
//				if(jsonResult!=null&&!"".equals(jsonResult)){
//					return jsonResult;
//				}else{
//					return "";
//				}
			}catch(Exception e){
				e.printStackTrace();
				return "false";
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
			if(result!=null){
				if(result.startsWith("no")){
					Toast.makeText(getApplicationContext(), "请求成功，等待短信验证码发送！", Toast.LENGTH_SHORT).show();
					yanzhengmaReturn = result.substring(2, result.length());
					Log.d("======yanzhengmaReturn========", yanzhengmaReturn);
				}
				else if("yesphoneNumber".equals(result)){
					Toast.makeText(getApplicationContext(), "手机号已注册！", Toast.LENGTH_SHORT).show();
				}
				else if("false".equals(result)){
					Toast.makeText(getApplicationContext(), "验证码请求失败，请重新请求！", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "验证码请求失败，请重新请求！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
			if(result.startsWith("no")){				
				timer.schedule(task,1, 1000); //延时1000ms后执行，1000ms执行一次
			}
		}
	}
}