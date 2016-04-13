package com.pg.pg.main;

import java.util.ArrayList;
import java.util.List;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.bean.Ppdr_dailyrecycle;
import com.pg.pg.json.WriteJson;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.tools.Operaton;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AkeyAppointment extends Activity  implements OnClickListener{

	private Button yijianyuyuebt;
	private LoadingProgressDialog dialog;
	private String jsonString;
	private Pgdr_userApp puser;
	private EditText yijianshoujiEditText;
	private ImageView fanhui;
	private LinearLayout linefanhui;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(R.layout.activity_akey);  
        
        puser = (Pgdr_userApp) getApplication();
        
        yijianyuyuebt = (Button) findViewById(R.id.yijianyuyuebt);
        yijianyuyuebt.setOnClickListener(this);
        
        yijianshoujiEditText = (EditText) findViewById(R.id.yijianshoujiEditText);
        yijianshoujiEditText.setText(puser.getUser_mobile());
        
        
       fanhui  = (ImageView)findViewById(R.id.fanhui);
  	   fanhui.setOnClickListener(this);
  	   
       linefanhui = (LinearLayout)findViewById(R.id.linefanhui);
       linefanhui.setOnClickListener(this);
        
        //初始化dialog
        dialog=new LoadingProgressDialog(this,"正在加载...");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.yijianyuyuebt:
			new UpdateUserAsyncTask().execute(new String[]{yijianshoujiEditText.getText().toString()});		
		 	break;
		case R.id.fanhui:
			finish();
		 	break;
		case R.id.linefanhui:
			finish();
			break;
		default:
			break;
		}
	}
	
	/**
	 * dis：AsyncTask参数类型：
	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
	 * 第二个参数表示进度的刻度
	 * 第三个参数表示返回的结果类型
	 * */
	private class UpdateUserAsyncTask extends AsyncTask<String, String, String>{
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
				Log.d("=com.pg.pg.main.AkeyAppointment=", "=AkeyAppointment=doInBackground======");
				Operaton operaton=new Operaton();
				String result = "";
				Ppdr_dailyrecycle pdr=new Ppdr_dailyrecycle();	
				pdr.setDailyrecycle_user_mobile(params[0]);
				pdr.setDailyrecycle_status("0");
				pdr.setDailyrecycle_iscycle("1");
				//构造一个user对象
				List<Ppdr_dailyrecycle> list=new ArrayList<Ppdr_dailyrecycle>();
				list.add(pdr);
				WriteJson writeJson=new WriteJson();
				//将user对象写出json形式字符串
				jsonString= writeJson.getJsonData(list);
				Log.d("=com.pg.pg.main.AkeyAppointment=", "=AkeyAppointment=doInBackground===jsonString==="+jsonString);
 				result=operaton.AddRecycle("AddRecycle", jsonString);
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
				Toast.makeText(getApplicationContext(), "预约成功！", Toast.LENGTH_SHORT).show();
				AkeyAppointment.this.finish();
			}else if("".equals(result)){
				Toast.makeText(getApplicationContext(), "预约失败，请重试！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
		}
	}
}
