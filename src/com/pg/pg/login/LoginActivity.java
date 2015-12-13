package com.pg.pg.login;

import com.pg.pg.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {
	private Button btdenglu;
	private Button btzhuce;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_login);
		 btzhuce = (Button)findViewById(R.id.zhuce);
		 btdenglu = (Button)findViewById(R.id.denglu);
	        btzhuce.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {				
					Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
					startActivity(intent); 
				}
			 });
	 }
}
