package com.pg.pg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.pg.pg.init.GuideViewActivity;;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        String firstOpen = isFirstOpen();
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
}
