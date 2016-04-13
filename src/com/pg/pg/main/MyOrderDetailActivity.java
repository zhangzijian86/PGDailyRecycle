package com.pg.pg.main;

import java.util.List;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.bean.Ppdr_dailyrecycle;
import com.pg.pg.json.JsonUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyOrderDetailActivity extends Activity {
	private TextView phone;
	private Ppdr_dailyrecycle pdr;
	private LinearLayout linefanhui;
	private TextView lianxirentext;
	private TextView shoujitext;
	private TextView leixingtext;
	private TextView dizhitext;
	private TextView shijiantext;
	private TextView zhuangtaitext;
	private LinearLayout dizhidetail;
	private ImageView fanhui;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_myorderdetail);  
        phone = (TextView) findViewById(R.id.phone);
        phone.setText(((Pgdr_userApp) getApplication()).getUser_mobile());
        Intent intent = getIntent(); 
        String jsonstring = intent.getStringExtra("jsonstring");
//        String type = intent.getStringExtra("type");
        JsonUtil jsonUtil=new JsonUtil();
        
        lianxirentext = (TextView) findViewById(R.id.lianxirentext);
        shoujitext = (TextView) findViewById(R.id.shoujitext);
        leixingtext = (TextView) findViewById(R.id.leixingtext);
        dizhitext = (TextView) findViewById(R.id.dizhitext);
        shijiantext = (TextView) findViewById(R.id.shijiantext);
        zhuangtaitext = (TextView) findViewById(R.id.zhuangtaitext);
        
        linefanhui = (LinearLayout)findViewById(R.id.linefanhui);
        linefanhui.setOnClickListener(new OnClickListener() {			
 			@Override
 			public void onClick(View arg0) {				
 				finish();
 			}
 		});
        
        List<Ppdr_dailyrecycle> list1=(List<Ppdr_dailyrecycle>) jsonUtil.StringFromJsonRecycle(jsonstring);
        if(list1!=null&&list1.size()>0){
        	pdr = list1.get(0);
        	lianxirentext.setText(pdr.getDailyrecycle_name());
        	shoujitext.setText(pdr.getDailyrecycle_user_mobile());
        	leixingtext.setText(pdr.getDailyrecycle_type());
        	dizhitext.setText(pdr.getDailyrecycle_address());
        	shijiantext.setText(pdr.getDailyrecycle_date());
        	if(pdr.getDailyrecycle_status().equals("0")){
        		zhuangtaitext.setText("进行中");
        	}else{
        		zhuangtaitext.setText("已完成");
        	}        	
        }
        dizhidetail = (LinearLayout) findViewById(R.id.dizhidetail);
        if(dizhitext.getLineCount()==1){
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(      
                   40,      
                   LinearLayout.LayoutParams.WRAP_CONTENT      
            );
            dizhitext.setGravity(Gravity.CENTER);
        	//dizhidetail.setLayoutParams(p);
        }else{
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(      
            		LinearLayout.LayoutParams.MATCH_PARENT,      
                    LinearLayout.LayoutParams.WRAP_CONTENT      
             );
            dizhitext.setGravity(Gravity.LEFT);
         	//dizhidetail.setLayoutParams(p);
        }
  	   fanhui  = (ImageView)findViewById(R.id.fanhui);
  	   fanhui.setOnClickListener(new OnClickListener() {			
 			@Override
 			public void onClick(View arg0) {				
 				finish();
 			}
 		});
	}
}
