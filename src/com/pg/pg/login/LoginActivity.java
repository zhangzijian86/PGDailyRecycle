package com.pg.pg.login;

import java.util.List;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_user;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.json.JsonUtil;
import com.pg.pg.main.MainActivity;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.tools.Operaton;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button btdenglu;
	private Button btzhuce;
	private EditText passwdedit;
	private EditText namededit;
	private TextView nametext;
	private TextView passwdtext;
	private Pgdr_userApp puser;
	private String usermobile;
	private String loginPass;
	private LoadingProgressDialog dialog;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_login);
		 
		 puser = (Pgdr_userApp) getApplication();
		 
		 btzhuce = (Button)findViewById(R.id.zhuce);
		 btdenglu = (Button)findViewById(R.id.denglu);
	     passwdedit = (EditText)findViewById(R.id.passwdedittext);
	     namededit = (EditText)findViewById(R.id.nameedittext);
	     nametext = (TextView)findViewById(R.id.nametext);
	     passwdtext = (TextView)findViewById(R.id.passwdtext);
	     namededit.addTextChangedListener(textWatcher);
	     passwdedit.addTextChangedListener(textWatcher);
		 btzhuce.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {				
				 Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				 startActivity(intent); 
			 }
		 });
		 btdenglu.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 //					Intent intent = new Intent(AnyCareLoginActivity.this,AnyCareMainActivity.class); 
				 //					startActivity(intent);
//				 if(NetUtils.isConnected(getApplicationContext())){
					 //true为记住密码
					 usermobile = namededit.getText().toString().trim();
					 loginPass = passwdedit.getText().toString().trim();
					 if(!TextUtils.isEmpty(usermobile)&&!TextUtils.isEmpty(loginPass)){
						 //执行验证的异步操作
						 new UserLoginAsyncTask().execute(new String[]{usermobile,loginPass});						 
					 }else{
						 Toast.makeText(getApplicationContext(), "手机号或者密码不能为空！", Toast.LENGTH_SHORT).show();
					 }
//				 }else{
//					 NetUtils.openSetting(AnyCareLoginActivity.this);
//				 }
			 }
		 });
		//初始化dialog
		 dialog=new LoadingProgressDialog(this,"正在加载...");
	 }
	 
	    private TextWatcher textWatcher = new TextWatcher() {  
	    	  
	    	@Override  
	    	public void onTextChanged(CharSequence s, int start, int before,  
	    	int count) {  	  
			   if(namededit.getText().toString().equals("")){
				   nametext.setVisibility(View.VISIBLE);
			   }else{
				   nametext.setVisibility(View.INVISIBLE);
			   }
			   if(passwdedit.getText().toString().equals("")){
				   passwdtext.setVisibility(View.VISIBLE);
			   }else{
				   passwdtext.setVisibility(View.INVISIBLE);
			   }
	    	}  
	    	  
	    	@Override  
	    	public void beforeTextChanged(CharSequence s, int start, int count,  
	    	int after) {  
	    	  
	    	}  
	    	  
	    	@Override  
	    	public void afterTextChanged(Editable s) {   
	    		
	    	}  
    	}; 
	 	 
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
 				String result=operaton.login("Login", params[0], params[1]);
 				if(!result.equals("false")){
 	 				JsonUtil jsonUtil=new JsonUtil();
 					List<Pgdr_user> list1=(List<Pgdr_user>) jsonUtil.StringFromJson(result);
 					Pgdr_user user=list1.get(0);  					
 					if(user.isUser_return()){
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
 						Log.d("====com.pg.pg.login.LoginActivity====", "getUser_id()"+puser.getUser_id());
 						Log.d("====com.pg.pg.login.LoginActivity====", "getUser_mobile()"+puser.getUser_mobile());
 						Log.d("====com.pg.pg.login.LoginActivity====", "getUser_password()"+puser.getUser_password());
 						return "success";
 					}else{
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
 				startActivity(new Intent(getApplication(), MainActivity.class));
 				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
 				LoginActivity.this.finish();
 			}else if("false".equals(result)){
 				Toast.makeText(getApplicationContext(), "用户名密码有误，请重新登录！", Toast.LENGTH_SHORT).show();
 			}else if("networkerror".equals(result)){
 				Toast.makeText(getApplicationContext(), "网络加载异常", Toast.LENGTH_SHORT).show();
 			}
 			dialog.dismiss();//dialog关闭，数据处理完毕
 		}
 	}
}
