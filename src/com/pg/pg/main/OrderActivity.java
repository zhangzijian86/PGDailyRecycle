package com.pg.pg.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_user;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.bean.Ppdr_dailyrecycle;
import com.pg.pg.json.WriteJson;
import com.pg.pg.tools.DateTimePickDialogUtil;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.tools.Operaton;
import com.pg.pg.wheel.active.BaseWhellActivity;
import com.pg.pg.wheel.widget.OnWheelChangedListener;
import com.pg.pg.wheel.widget.WheelView;
import com.pg.pg.wheel.widget.adapters.ArrayWheelAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;

public class OrderActivity  extends BaseWhellActivity implements OnClickListener, OnWheelChangedListener {
	private LinearLayout linefanhui;
	private LinearLayout diquxuanze;
	private LinearLayout diqudianji;
	private RadioGroup yuezhou;
	private LinearLayout shijianxuanze;
	private EditText diqutext;
	private EditText xiangxidizhiEditText;
	private EditText shoujiEditText;
	private EditText lianxirenEditText;
	
	private Button  zhucequeding;
	
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;
	
	private LoadingProgressDialog dialog;
	private Pgdr_userApp puser;
	
	private ImageView image;
	private TextView leixing;
	private CheckBox zhouqiCheckBox;
	
	private EditText shijianEditText;
	
	private String jsonString;
	private String type;
	private String dailyrecycle_iscycle;
	
	private String yuezhouflag;
	  
	private String initStartDateTime = "2016年1月12日 22:22"; // 初始化开始时间 
	
	private ImageView fanhui;
	private int times;
	
	@SuppressLint("SimpleDateFormat") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);        
        dailyrecycle_iscycle = "0";
        setContentView(R.layout.activity_order);  
        puser = (Pgdr_userApp) getApplication();
        Intent intent = getIntent(); //用于激活它的意图对象        
        type = intent.getStringExtra("type");
        Log.d("=com.pg.pg.main.OrderActivity=", "==onCreate==type===="+type);
        
        image = (ImageView)findViewById(R.id.tu);
        leixing = (TextView)findViewById(R.id.leixing);
        
        yuezhou = (RadioGroup)findViewById(R.id.yuezhou);
        yuezhou.setVisibility(View.GONE);    
        
        yuezhou.setOnCheckedChangeListener(mChangeRadio);
        
        zhouqiCheckBox = (CheckBox)findViewById(R.id.zhouqiCheckBox);
        //给CheckBox设置事件监听 
        zhouqiCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
                if(isChecked){ 
                	 dailyrecycle_iscycle = "1";
                	 yuezhou.setVisibility(View.VISIBLE);
                }else{ 
                	 dailyrecycle_iscycle = "0";
                	 yuezhou.setVisibility(View.GONE);
                } 
            } 
        }); 
        
        if(type.equals("shouji")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.shouji_hong));
        	leixing.setText("手机回收");
        	type = "手机回收";
        	zhouqiCheckBox.setEnabled(false);
        }else if(type.equals("jiuyifu")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.jiuyifu_hong));
        	leixing.setText("衣服回收");
        	type = "衣服回收";
         }
        else if(type.equals("suliaoping")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.suliaoping_hong));
        	leixing.setText("塑料瓶回收");
        	type = "塑料瓶回收";
         }
        else if(type.equals("yilaguan")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.yilaguan_hong));
        	leixing.setText("易拉罐回收");
        	type = "易拉罐回收";
         }
        else if(type.equals("zhi")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.zhixiang_hong));
        	leixing.setText("纸箱回收");
        	type = "纸箱回收";
         }
        else if(type.equals("dianzi")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.dianzi_h));
        	leixing.setText("电子设备回收");
        	type = "电子设备回收";
         }
        else if(type.equals("jiadian")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.jiujiadian_hong));
        	leixing.setText("家电回收");
        	type = "家电回收";
         }
        else if(type.equals("qita")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.gengduo_hong));
        	leixing.setText("其他回收");
        	type = "其他回收";
         }
        
        linefanhui = (LinearLayout)findViewById(R.id.linefanhui);
        linefanhui.setOnClickListener(this); 
        
        diquxuanze = (LinearLayout)findViewById(R.id.diquxuanze);
        diquxuanze.setVisibility(View.GONE);        
        
        diqudianji  = (LinearLayout)findViewById(R.id.diqudianji);
        diqudianji.setOnClickListener(this); 
        
        diqutext = (EditText) findViewById(R.id.diquEditText);
        diqutext.setInputType(InputType.TYPE_NULL);
//        diqutext.setOnClickListener(this); 
        xiangxidizhiEditText = (EditText) findViewById(R.id.xiangxidizhiEditText);
        
        zhucequeding = (Button) findViewById(R.id.zhucequeding);
        zhucequeding.setOnClickListener(this);
        
        if(puser.getUser_address()!=null&&!puser.getUser_address().equals("")){
     	   String address[] = puser.getUser_address().split(":");
     	   diqutext.setText(address[0]);
     	   if(address.length>=2){
     		   xiangxidizhiEditText.setText(address[1]);
     	   }
        }
        
       shoujiEditText = (EditText) findViewById(R.id.shoujiEditText);
       shoujiEditText.setText( puser.getUser_mobile());
       
       lianxirenEditText = (EditText) findViewById(R.id.lianxirenEditText);
       lianxirenEditText.setText( puser.getUser_name());
       
       //===========时间=============
       shijianxuanze = (LinearLayout)findViewById(R.id.shijianxuanze);
       shijianxuanze.setVisibility(View.GONE);
       shijianEditText  = (EditText) findViewById(R.id.shijianEditText);
       
       Date d = new Date();  
       Log.d("=com.pg.pg.main.MineAddressActivity=","=data="+d);  
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");  
       initStartDateTime = sdf.format(d);  
       shijianEditText.setText(initStartDateTime);  
       shijianEditText.setInputType(InputType.TYPE_NULL);
       shijianEditText.setOnClickListener(this);  
       //===========================
        
		setUpViews();
		setUpListener();
		setUpData();
		mCurrentDistrictName = "昌平区";
		
       fanhui  = (ImageView)findViewById(R.id.fanhui);
       fanhui.setOnClickListener(this);
		
       times = Integer.parseInt(getValue("user_times").trim());
       
		//初始化dialog
		dialog=new LoadingProgressDialog(this,"正在加载...");
    }
	
	private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // TODO Auto-generated method stub
            if (checkedId == R.id.meizhou) {
                // 把mRadio1的内容传到mTextView1
            	yuezhouflag = "0";
            	Log.d("=com.pg.pg.main.MineAddressActivity=", "==OnCheckedChangeListener===yuezhouflag==="+yuezhouflag);
            } else if (checkedId == R.id.meiyue) {
                // 把mRadio2的内容传到mTextView1
            	yuezhouflag = "1";
            	Log.d("=com.pg.pg.main.MineAddressActivity=", "==OnCheckedChangeListener===yuezhouflag==="+yuezhouflag);
            }
        }
    };
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			diquxuanze.setVisibility(View.GONE);
			diqutext.setText(mCurrentProviceName+" "+mCurrentCityName+" "+mCurrentDistrictName);
			break;
/*	    case R.id.diquEditText:
	    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==diquEditText====");
	    	diquxuanze.setVisibility(View.VISIBLE);
	    	break;*/
	    case R.id.diqudianji:
	    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==diquEditText====");
	    	diquxuanze.setVisibility(View.VISIBLE);
	    	break;
	    case R.id.zhucequeding:
	    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==zhucequeding====");
	    	if(times<=0){
	    		Toast.makeText(getApplicationContext(), "您今日已经预约了10次，不能再预约！", Toast.LENGTH_SHORT).show();
	    	}else{
	    		if(shoujiEditText.getText().toString().trim().equals("")){
	    			Toast.makeText(getApplicationContext(), "请填写手机号码！", Toast.LENGTH_SHORT).show();
	    		}else{
	    			if(shoujiEditText.getText().toString().trim().length()!=11){
	    				Toast.makeText(getApplicationContext(), "请正确填写11位手机号码！", Toast.LENGTH_SHORT).show();
	    			}else{
	    				if(isNumeric(shoujiEditText.getText().toString().trim())){
	    					new UpdateUserAsyncTask().execute(new String[]{});
	    				}else{
	    					Toast.makeText(getApplicationContext(), "手机号码应为数字！", Toast.LENGTH_SHORT).show();
	    				}
	    			}	    		
	    		}
	    	}
	    	break;
	    case R.id.shijianEditText:
	    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==shijianEditText====");
            DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(  
         		   OrderActivity.this, initStartDateTime);  
            dateTimePicKDialog.dateTimePicKDialog(shijianEditText);	
	    	break;
	    case R.id.fanhui:
	    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==fanhui====");
	    	finish();
	    	break;
		case R.id.linefanhui:
			finish();
			break;
		default:
			break;
		}
	}
	
	public boolean isNumeric(String str){
		for (int i = str.length() ; --i>=0 ; ){   
			if (!Character.isDigit(str.charAt ( i ) ) ){
				return false;
			}
		}
		return true;
	}
	
	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
	}
	
	private void setUpListener() {
    	// 添加change事件
    	mViewProvince.addChangingListener(this);
    	// 添加change事件
    	mViewCity.addChangingListener(this);
    	// 添加change事件
    	mViewDistrict.addChangingListener(this);
    	// 添加onclick事件
    	mBtnConfirm.setOnClickListener(this);
    }
	
	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(OrderActivity.this, mProvinceDatas));
		mViewProvince.setCurrentItem(1);
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			Log.d("=com.pg.pg.main.MineAddressActivity=", "==onChanged===mViewProvince===");
			updateCities();
		} else if (wheel == mViewCity) {
			Log.d("=com.pg.pg.main.MineAddressActivity=", "==onChanged===mViewCity===");
			updateAreas();
		} else if (wheel == mViewDistrict) {
			Log.d("=com.pg.pg.main.MineAddressActivity=", "==onChanged===mViewDistrict=newValue=="+newValue);
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
			Log.d("=com.pg.pg.main.MineAddressActivity=", "==onChanged===mCurrentDistrictName==="+mCurrentDistrictName);
		}
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
		mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
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
				Log.d("=com.pg.pg.main.MineAddressActivity=", "==doInBackground===type==="+type);
				Ppdr_dailyrecycle pdr=new Ppdr_dailyrecycle();	
				pdr.setDailyrecycle_name(shoujiEditText.getText().toString());
				pdr.setDailyrecycle_user_mobile(shoujiEditText.getText().toString());
				pdr.setDailyrecycle_date(shijianEditText.getText().toString());
				pdr.setDailyrecycle_week("");
				pdr.setDailyrecycle_iscycle(dailyrecycle_iscycle);
				pdr.setDailyrecycle_cycletype(yuezhouflag);
				pdr.setDailyrecycle_isvalid("1");
				pdr.setDailyrecycle_status("0");
				pdr.setDailyrecycle_recyclingmanphone("");
				pdr.setDailyrecycle_finishtime("");
				pdr.setDailyrecycle_type(type);
				pdr.setDailyrecycle_explain("");
				pdr.setDailyrecycle_name(lianxirenEditText.getText().toString());				
				pdr.setDailyrecycle_address(diqutext.getText().toString()+":"+xiangxidizhiEditText.getText().toString());
				//构造一个user对象
				List<Ppdr_dailyrecycle> list=new ArrayList<Ppdr_dailyrecycle>();
				list.add(pdr);
				WriteJson writeJson=new WriteJson();
				//将user对象写出json形式字符串
				jsonString= writeJson.getJsonData(list);
				Log.d("=com.pg.pg.main.OrderActivity=", "==doInBackground===jsonString==="+jsonString);
				Operaton operaton=new Operaton();
 				String result=operaton.AddRecycle("AddRecycle", jsonString);
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
				OrderActivity.this.finish();
			}else if("".equals(result)){
				Toast.makeText(getApplicationContext(), "预约失败，请重试！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
		}
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
	private String getValue(String name){
		SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
		//若没有数据，返回默认值""
		String value=sp.getString(name, "");
		return value;
	}
}
