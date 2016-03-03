package com.pg.pg;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import cn.jpush.android.api.JPushInterface;

import com.pg.pg.init.GuideViewActivity;
import com.pg.pg.tools.ExampleUtil;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        String firstOpen = isFirstOpen();
        registerMessageReceiver();  // used for receive msg
        JPushInterface.init(getApplicationContext());
//        if("".equals(firstOpen)){
			startActivity(new Intent(getApplication(), GuideViewActivity.class));
			overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			SplashActivity.this.finish();
//		}else{
//			setContentView(R.layout.activity_splash);
//			Handler x = new Handler();
//			x.postDelayed(new splashhandler(), 2000);
//		}
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
	
    
	class splashhandler implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
/*			startActivity(new Intent(getApplication(), BraceletLoginActivity.class));
			overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			BraceletSplashActivity.this.finish();*/
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
}
