package com.pg.pg.main;

import java.util.ArrayList;
import java.util.List;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_user;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.json.WriteJson;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.tools.Operaton;
import com.pg.pg.wheel.active.BaseWhellActivity;
import com.pg.pg.wheel.widget.OnWheelChangedListener;
import com.pg.pg.wheel.widget.WheelView;
import com.pg.pg.wheel.widget.adapters.ArrayWheelAdapter;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;

public class OrderActivity  extends BaseWhellActivity implements OnClickListener, OnWheelChangedListener {
	private LinearLayout diquxuanze;
	private EditText diqutext;
	private EditText xiangxidizhiEditText;
	private EditText shoujiEditText;
	
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
	
	private String jsonString;
	private String dailyrecycle_iscycle;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dailyrecycle_iscycle = "0";
        setContentView(R.layout.activity_order);  
        puser = (Pgdr_userApp) getApplication();
        Intent intent = getIntent(); //用于激活它的意图对象        
        String type = intent.getStringExtra("type");
        Log.d("=com.pg.pg.main.OrderActivity=", "==onCreate==type===="+type);
        
        image = (ImageView)findViewById(R.id.tu);
        leixing = (TextView)findViewById(R.id.leixing);
        if(type.equals("shouji")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.shouji_hong));
        	leixing.setText("手机回收");
        }else if(type.equals("jiuyifu")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.jiuyifu_hong));
        	leixing.setText("衣服回收");
         }
        else if(type.equals("suliaoping")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.suliaoping_hong));
        	leixing.setText("塑料瓶回收");
         }
        else if(type.equals("yilaguan")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.yilaguan_hong));
        	leixing.setText("易拉罐回收");
         }
        else if(type.equals("zhi")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.zhixiang_hong));
        	leixing.setText("纸箱回收");
         }
        else if(type.equals("dianzi")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.dianzi_h));
        	leixing.setText("电子设备回收");
         }
        else if(type.equals("jiadian")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.jiujiadian_hong));
        	leixing.setText("家电回收");
         }
        else if(type.equals("qita")){
        	image.setImageDrawable(getResources().getDrawable(R.drawable.gengduo_hong));
        	leixing.setText("其他回收");
         }
        
        zhouqiCheckBox = (CheckBox)findViewById(R.id.zhouqiCheckBox);
        //给CheckBox设置事件监听 
        zhouqiCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
                if(isChecked){ 
                	 dailyrecycle_iscycle = "1";
                }else{ 
                	 dailyrecycle_iscycle = "0";
                } 
            } 
        }); 
        
        diquxuanze = (LinearLayout)findViewById(R.id.diquxuanze);
        diquxuanze.setVisibility(View.GONE);
        diqutext = (EditText) findViewById(R.id.diquEditText);
        diqutext.setInputType(InputType.TYPE_NULL);
        diqutext.setOnClickListener(this); 
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
        
		setUpViews();
		setUpListener();
		setUpData();
		mCurrentDistrictName = "昌平区";
		//初始化dialog
		dialog=new LoadingProgressDialog(this,"正在加载...");
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			diquxuanze.setVisibility(View.GONE);
			diqutext.setText(mCurrentProviceName+" "+mCurrentCityName+" "+mCurrentDistrictName);
			break;
	    case R.id.diquEditText:
	    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==diquEditText====");
	    	diquxuanze.setVisibility(View.VISIBLE);
	    	break;
	    case R.id.zhucequeding:
	    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==zhucequeding====");
	    	new UpdateUserAsyncTask().execute(new String[]{});		
	    	break;
		default:
			break;
		}
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
				Log.d("=com.pg.pg.main.MineAddressActivity=", "==doInBackground======");
				Pgdr_user user=new Pgdr_user();				
				user.setUser_password(puser.getUser_password());
				user.setUser_address(diqutext.getText().toString()+":"+xiangxidizhiEditText.getText().toString());
				user.setUser_email(puser.getUser_email());
				user.setUser_status(puser.getUser_status());
				user.setUser_type(puser.getUser_type());
				user.setUser_photo(puser.getUser_photo());
				user.setUser_mobile(puser.getUser_mobile());
				//构造一个user对象
				List<Pgdr_user> list=new ArrayList<Pgdr_user>();
				list.add(user);
				WriteJson writeJson=new WriteJson();
				//将user对象写出json形式字符串
				jsonString= writeJson.getJsonData(list);
				Log.d("=com.pg.pg.main.MineAddressActivity=", "==doInBackground===jsonString==="+jsonString);
				Operaton operaton=new Operaton();
 				String result=operaton.UpdateUser("UpdateUser", jsonString);
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
				Toast.makeText(getApplicationContext(), "更新成功！", Toast.LENGTH_SHORT).show();
				OrderActivity.this.finish();
			}else if("".equals(result)){
				Toast.makeText(getApplicationContext(), "更新失败，请重试！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
		}
	}

}
