package com.pg.pg.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.tools.ExampleUtil;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.tools.Operaton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	private Pgdr_userApp puser;
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
					if(yanzhengmaReturn.equals(yanZhengMa)){
						Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_SHORT).show();
						puser = (Pgdr_userApp) getApplication();
						puser.setUser_mobile(phoneNumber);
						//获取SharedPreferences对象，路径在/data/data/cn.itcast.preferences/shared_pref/paramater.xml
						SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
						//获取编辑器
						Editor editor=sp.edit();
						//通过editor进行设置
						editor.putString("mobile", phoneNumber);
						//提交修改，将数据写到文件
						editor.commit();
						Intent intent = new Intent();
			          setResult(1001, intent);
			           //调用JPush API设置Alias
			  		   mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, phoneNumber));
			          Log.d("==RegisterActivity===", "====finish====");
						finish();
//						 Intent intent = new Intent();
//						 intent.setClass(RegisterActivity.this, RegisterPasswordActivity.class);
//						 intent.putExtra("phoneNumber", phoneNumber);
//						 startActivity(intent);
					}else{
						Toast.makeText(getApplicationContext(), "验证码错误！", Toast.LENGTH_SHORT).show();
					}
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
					Toast.makeText(getApplicationContext(), "登陆失败请重试！", Toast.LENGTH_SHORT).show();
				}
				else if("false".equals(result)){
					Toast.makeText(getApplicationContext(), "验证码请求失败，请重新请求！", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "验证码请求失败，请重新请求！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
			if(result!=null&&result.startsWith("no")){				
				timer.schedule(task,1, 1000); //延时1000ms后执行，1000ms执行一次
			}
		}
	}
	
	private static final String TAG = "JPush";
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MSG_SET_ALIAS:
                Log.d(TAG, "Set alias in handler.");
                JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                break;
                
            case MSG_SET_TAGS:
                Log.d(TAG, "Set tags in handler.");
                //JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                break;
                
            default:
                Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                if (ExampleUtil.isConnected(getApplicationContext())) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                	Log.i(TAG, "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e(TAG, logs);
            }
            
            //ExampleUtil.showToast(logs, getApplicationContext());
        }
	    
	};
}
