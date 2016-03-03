package com.pg.pg.init;

import java.util.ArrayList;
import java.util.List;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_user;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.json.JsonUtil;
import com.pg.pg.main.MainActivity;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.tools.Operaton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GuideViewActivity extends Activity {
	
    private ViewPager viewPager;  
    private ArrayList<View> pageViews;  
    private ViewGroup main, group;  
    private ImageView imageView;  
    private ImageView[] imageViews; 
    private Pgdr_userApp puser;
    private LoadingProgressDialog dialog;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = getLayoutInflater();  
        pageViews = new ArrayList<View>();  
        pageViews.add(inflater.inflate(R.layout.item01, null));
        pageViews.add(inflater.inflate(R.layout.item02, null));
        View viewItem03 = inflater.inflate(R.layout.item03, null); 
        Button start_button = (Button) viewItem03.findViewById(R.id.start_button);
        start_button.setOnClickListener(new OnClickListener(){ 
        	public void onClick(View v){
        		saveGuideView();
        	}
        }); 
        pageViews.add(viewItem03);   
        imageViews = new ImageView[pageViews.size()];  
        main = (ViewGroup)inflater.inflate(R.layout.activity_guideview, null);  
        // group是R.layou.main中的负责包裹小圆点的LinearLayout.  
        group = (ViewGroup)main.findViewById(R.id.viewGroup);  
        viewPager = (ViewPager)main.findViewById(R.id.guidePages);  
        for (int i = 0; i < pageViews.size(); i++) {  
            imageView = new ImageView(GuideViewActivity.this);  
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
            lp.setMargins(10, 0, 0, 0);
            imageView.setLayoutParams(lp);  
            imageView.setPadding(0, 0, 0, 0);  
            imageViews[i] = imageView;  
            if (i == 0) {
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.point_focused);  
            } else {  
                imageViews[i].setBackgroundResource(R.drawable.point_unfocused);  
            }  
            group.addView(imageViews[i]);  
        }
        puser = (Pgdr_userApp) getApplication();
        setContentView(main);  
        viewPager.setAdapter(new GuidePageAdapter());  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());  
        dialog=new LoadingProgressDialog(this,"正在加载...");
    }
    
    /** 指引页面Adapter */
    class GuidePageAdapter extends PagerAdapter {
        @Override  
        public int getCount() {  
            return pageViews.size();  
        }  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(pageViews.get(arg1));  
        }  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).addView(pageViews.get(arg1));  
            return pageViews.get(arg1);  
        }
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }
    } 
    
    /** 指引页面改监听器 */
    class GuidePageChangeListener implements OnPageChangeListener {  
  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {  
                imageViews[arg0].setBackgroundResource(R.drawable.point_focused);  
                if (arg0 != i) {  
                    imageViews[i].setBackgroundResource(R.drawable.point_unfocused);  
                }  
            }
        }  
    }
    
	private String getValue(String name){
		SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
		//若没有数据，返回默认值""
		String value=sp.getString(name, "");
		return value;
	}
    
    private void saveGuideView(){
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
			startActivity(new Intent(GuideViewActivity.this, MainActivity.class));
			overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			GuideViewActivity.this.finish();
    	}else{
    		Log.d("GuideViewActivity", "=GuideViewActivity==11111==");
    		new UserLoginAsyncTask().execute(new String[]{getValue("mobile")});			
    	}
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
// 				Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
// 				startActivity(new Intent(getApplication(), MainActivity.class));
// 				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
// 				LoginActivity.this.finish();
 			}else if("false".equals(result)){
 				Toast.makeText(getApplicationContext(), "登陆失败！", Toast.LENGTH_SHORT).show();
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
			startActivity(new Intent(GuideViewActivity.this, MainActivity.class));
			overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			GuideViewActivity.this.finish();
 			dialog.dismiss();//dialog关闭，数据处理完毕
 		}
 	}
    
}