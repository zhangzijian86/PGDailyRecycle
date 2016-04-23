package com.pg.pg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.pg.pg.bean.Pgdr_user;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.init.GuideViewActivity;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.json.JsonUtil;
import com.pg.pg.main.MainActivity;
import com.pg.pg.tools.ExampleUtil;
import com.pg.pg.tools.Operaton;


public class SplashActivity extends Activity {

	private LoadingProgressDialog dialog;
	private Pgdr_userApp puser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        String firstOpen = isFirstOpen();
        registerMessageReceiver();  // used for receive msg
        JPushInterface.init(getApplicationContext());
		puser = (Pgdr_userApp) getApplication();
		dialog=new LoadingProgressDialog(this,"正在加载...");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String date = df.format(new Date());
		
		Log.d("","=======user_times========000======="+getValue("user_times"));
		Log.d("","=======date===000============"+date);
		Log.d("","=======date===111============"+getValue("user_date"));
		if(getValue("user_times")==null||getValue("user_times").equals("")){
			Log.d("","=======user_times=====111=========="+getValue("user_times"));
			setValue("user_times","10");
		}
		Log.d("","=======user_times=========222======"+getValue("user_times"));
		if(getValue("user_date")==null||getValue("user_date").equals("")){
			Log.d("","=======user_times======333========="+getValue("user_times"));
			setValue("user_date",date);
			setValue("user_times","10");
		}else{
			Log.d("","=======user_times======444========="+getValue("user_times"));
			if(!getValue("user_date").equals(date)){
				Log.d("","=======date===555============"+date);
				setValue("user_date",date);
				setValue("user_times","10");
			}
		}
//        if("".equals(firstOpen)){
//			startActivity(new Intent(getApplication(), GuideViewActivity.class));
//			overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
//			SplashActivity.this.finish();
//		}else{
			setContentView(R.layout.activity_splash);
			Handler x = new Handler();
			x.postDelayed(new splashhandler(), 2000);
//		}

    }
    
	private void setValue(String type,String value){
		//获取SharedPreferences对象，路径在/data/data/cn.itcast.preferences/shared_pref/paramater.xml
		SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
		//获取编辑器
		Editor editor=sp.edit();
		//通过editor进行设置
		editor.putString(type,value);
		//提交修改，将数据写到文件
		editor.commit();	
	}
    
	//for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
    
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}
	
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
              String messge = intent.getStringExtra(KEY_MESSAGE);
              String extras = intent.getStringExtra(KEY_EXTRAS);
              StringBuilder showMsg = new StringBuilder();
              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
              if (!ExampleUtil.isEmpty(extras)) {
            	  showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
              }
			}
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		JPushInterface.onResume(this);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		JPushInterface.onPause(this);
	}
	
	private String getValue(String name){
		SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
		//若没有数据，返回默认值""
		String value=sp.getString(name, "");
		return value;
	}
    
	class splashhandler implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(getValue("mobile")==null||getValue("mobile").equals("")){
	    		Log.d("GuideViewActivity", "=GuideViewActivity==00000==");
	    		puser.setUser_id("");
	    		puser.setUser_name("");
	    		puser.setUser_password("");
	    		puser.setUser_mobile("");
	    		puser.setUser_address("");
	    		puser.setUser_email("");
	    		puser.setUser_status("");
	    		puser.setUser_type("");
	    		puser.setUser_photo("");
	    		puser.setUser_return(false); 
				//获取SharedPreferences对象，路径在/data/data/cn.itcast.preferences/shared_pref/paramater.xml
				SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
				//获取编辑器
				Editor editor=sp.edit();
				//通过editor进行设置
				editor.putString("firstOpen", "1");
				//提交修改，将数据写到文件
				editor.commit();
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				SplashActivity.this.finish();
	    	}else{
	    		Log.d("GuideViewActivity", "=GuideViewActivity==11111==");
	    		new UserLoginAsyncTask().execute(new String[]{getValue("mobile")});			
	    	}
		}
	}
    
	private String isFirstOpen(){
		SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
		//若没有数据，返回默认值""
		String firstOpen=sp.getString("firstOpen", "");
		return firstOpen;
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
	
 	/**
 	 * dis：AsyncTask参数类型：
 	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
 	 * 第二个参数表示进度的刻度
 	 * 第三个参数表示返回的结果类型
 	 * */
 	private class UserLoginAsyncTask extends AsyncTask<String, String, String>{
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
 				String result=operaton.login("Login", params[0]);  
 				if(!result.equals("false")){ 					
 	 				JsonUtil jsonUtil=new JsonUtil();
 					List<Pgdr_user> list1=(List<Pgdr_user>) jsonUtil.StringFromJson(result);
 					Pgdr_user user=list1.get(0);  					
 					if(user.isUser_return()){
 						Log.d("GuideViewActivity", "=GuideViewActivity==22222=="+user.getUser_type());
 						puser.setUser_id(user.getUser_id());
 						puser.setUser_name(user.getUser_name());
 						puser.setUser_password(user.getUser_password());
 						puser.setUser_mobile(user.getUser_mobile());
 						puser.setUser_address(user.getUser_address());
 						puser.setUser_email(user.getUser_email());
 						puser.setUser_status("1");
 						puser.setUser_type(user.getUser_type());
 						puser.setUser_photo(user.getUser_photo());
 						puser.setUser_return(true); 						
 						return "success";
 					}else{
 						Log.d("GuideViewActivity", "=GuideViewActivity==33333==");
 						puser.setUser_id("");
 						puser.setUser_name("");
 						puser.setUser_password("");
 						puser.setUser_mobile("");
 						puser.setUser_address("");
 						puser.setUser_email("");
 						puser.setUser_status("");
 						puser.setUser_type("");
 						puser.setUser_photo("");
 						puser.setUser_return(false); 
 						return "false";
 					} 					
 				}else{
 					return "false";
 				}
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
 			if("success".equals(result)){
 				Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
// 				startActivity(new Intent(getApplication(), MainActivity.class));
// 				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
// 				LoginActivity.this.finish();
 			}else if("false".equals(result)){
 				Toast.makeText(getApplicationContext(), "您现在是未登录状态！", Toast.LENGTH_SHORT).show();
 			}else if("networkerror".equals(result)){
 				Toast.makeText(getApplicationContext(), "网络加载异常", Toast.LENGTH_SHORT).show();
 			}
			//获取SharedPreferences对象，路径在/data/data/cn.itcast.preferences/shared_pref/paramater.xml
			SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
			//获取编辑器
			Editor editor=sp.edit();
			//通过editor进行设置
			editor.putString("firstOpen", "1");
			//提交修改，将数据写到文件
			editor.commit();
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
			overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			SplashActivity.this.finish();
 			dialog.dismiss();//dialog关闭，数据处理完毕
 		}
 	}
}
