package com.pg.pg.main;

import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_user;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.bean.Ppdr_dailyrecycle;
import com.pg.pg.json.JsonUtil;
import com.pg.pg.login.RegisterActivity;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MineActivity extends Activity{
	 
	private TextView dizhi;
	private TextView dingdan;
	TextView phone;
	Button tuichudangqiandenglu;
	private LoadingProgressDialog dialog;
	private Pgdr_userApp puser;
	
	private LinearLayout dizhiLinearLayout;
	private LinearLayout dingdanLinearLayout;
	
    @Override  
    protected void onStart() {  
    	super.onStart();  
    	Log.d("=MineActivity=", "==onStart===");
    	phone.setText(puser.getUser_mobile());
        if(puser.getUser_mobile().equals("")){
     	   tuichudangqiandenglu.setVisibility(View.GONE);
         }else{
         	tuichudangqiandenglu.setVisibility(View.VISIBLE);
         }
    }  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.activity_mine);  
       puser = (Pgdr_userApp) getApplication();
       dizhi = (TextView) findViewById(R.id.dizhi);
       dingdan = (TextView) findViewById(R.id.dingdan);
       dizhi.setOnClickListener(textViewlistener);
       dingdan.setOnClickListener(textViewlistener);
       phone = (TextView) findViewById(R.id.phone);
       phone.setText(puser.getUser_mobile());
       tuichudangqiandenglu  = (Button) findViewById(R.id.tuichudangqiandenglu);
       tuichudangqiandenglu.setOnClickListener(textViewlistener);
       if(puser.getUser_mobile().equals("")){
    	   tuichudangqiandenglu.setVisibility(View.GONE);
        }else{
        	tuichudangqiandenglu.setVisibility(View.VISIBLE);
        }
       dizhiLinearLayout = (LinearLayout) findViewById(R.id.dizhiLinearLayout);
       dizhiLinearLayout.setOnClickListener(textViewlistener);
       dingdanLinearLayout = (LinearLayout) findViewById(R.id.dingdanLinearLayout);
       dingdanLinearLayout.setOnClickListener(textViewlistener);
       dialog=new LoadingProgressDialog(this,"正在加载...");
	}
	
	OnClickListener   textViewlistener = new OnClickListener() {
		@Override  
		public void onClick(View view) {  
			 int buttonid = view.getId();
			 switch (buttonid) {
			 	case R.id.dizhiLinearLayout:
			    	Log.d("=com.pg.pg.main.MineActivity=", "==textViewlistener==dizhi====");
			    	if(puser.getUser_mobile().equals("")){
						 Intent intent = new Intent(MineActivity.this,RegisterActivity.class);
						 startActivityForResult(intent, 1000);
			    	}else{
			    		startActivity(new Intent(getApplication(), MineAddressActivity.class));
			    	}
			    	break;
			    case R.id.dizhi:
			    	Log.d("=com.pg.pg.main.MineActivity=", "==textViewlistener==dizhi====");
			    	if(puser.getUser_mobile().equals("")){
						 Intent intent = new Intent(MineActivity.this,RegisterActivity.class);
						 startActivityForResult(intent, 1000);
			    	}else{
			    		startActivity(new Intent(getApplication(), MineAddressActivity.class));
			    	}
			    	break;
			    case R.id.dingdan:
			    	Log.d("=com.pg.pg.main.MineActivity=", "==textViewlistener==dingdan====");
			    	if(puser.getUser_mobile().equals("")){
						 Intent intent = new Intent(MineActivity.this,RegisterActivity.class);
						 startActivityForResult(intent, 1000);
			    	}else{
			    		new MyOrderRegisterYanZhengMaAsyncTask().execute(new String[]{((Pgdr_userApp) getApplication()).getUser_mobile().toString()});				
			    	}
			    	break;
			    case R.id.dingdanLinearLayout:
			    	Log.d("=com.pg.pg.main.MineActivity=", "==textViewlistener==dingdan====");
			    	if(puser.getUser_mobile().equals("")){
						 Intent intent = new Intent(MineActivity.this,RegisterActivity.class);
						 startActivityForResult(intent, 1000);
			    	}else{
			    		new MyOrderRegisterYanZhengMaAsyncTask().execute(new String[]{((Pgdr_userApp) getApplication()).getUser_mobile().toString()});				
			    	}
			    	break;
			    case R.id.tuichudangqiandenglu:
			    	Log.d("=com.pg.pg.main.MineActivity=", "==textViewlistener==tuichudangqiandenglu====");			
					//获取SharedPreferences对象，路径在/data/data/cn.itcast.preferences/shared_pref/paramater.xml
					SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
					//获取编辑器
					Editor editor=sp.edit();
					//通过editor进行设置
					editor.putString("mobile", "");
					editor.putString("oldmobile", puser.getUser_mobile());
					//提交修改，将数据写到文件
					editor.commit();		
					puser.setUser_mobile("");
					puser.setUser_status("1");
					phone.setText("");		
					tuichudangqiandenglu.setVisibility(View.GONE);
					mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, ""));
			    	break;
			    default:
			       break;
			 
			 }
		}
	};
	
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
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
       super.onActivityResult(requestCode, resultCode, data);
   		Log.d("=MineActivity=", "==onActivityResult==resultCode===="+resultCode);
   		Log.d("=MineActivity=", "==onActivityResult==requestCode===="+requestCode);
       if(requestCode == 1000 && resultCode == 1001)
        {
        	phone.setText(puser.getUser_mobile());
        	tuichudangqiandenglu.setVisibility(View.VISIBLE);
        }
    }
	
	/**
	 * dis：AsyncTask参数类型：
	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
	 * 第二个参数表示进度的刻度
	 * 第三个参数表示返回的结果类型
	 * */
	private class MyOrderRegisterYanZhengMaAsyncTask extends AsyncTask<String, String, String>{
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
 				String result=operaton.getRecycleByPhoneNumber("GetRecycle", params[0]);
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
				if("false".equals(result)){
					Toast.makeText(getApplicationContext(), "数据加载失败，请重新请求！", Toast.LENGTH_SHORT).show();
				}else{
					Intent intentsuliaoping = new Intent(getApplicationContext(), MyOrderActivity.class);        
			    	intentsuliaoping.putExtra("result", result);
			    	startActivity(intentsuliaoping);
				}
			}else{
				Toast.makeText(getApplicationContext(), "数据加载失败，请重新请求！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
		}
	}
}
