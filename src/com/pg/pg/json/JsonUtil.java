package com.pg.pg.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pg.pg.bean.Pgdr_price;
import com.pg.pg.bean.Pgdr_user;
import com.pg.pg.bean.Ppdr_dailyrecycle;


public class JsonUtil {
	public List<Pgdr_user> StringFromJson (String jsondata)
	{     
		Type listType = new TypeToken<List<Pgdr_user>>(){}.getType();
		Gson gson=new Gson();
		ArrayList<Pgdr_user> list=gson.fromJson(jsondata, listType);
		return list;
	}
	public List<Ppdr_dailyrecycle> StringFromJsonRecycle (String jsondata)
	{     
		Type listType = new TypeToken<List<Ppdr_dailyrecycle>>(){}.getType();
		Gson gson=new Gson();
		ArrayList<Ppdr_dailyrecycle> list=gson.fromJson(jsondata, listType);
		return list;

	}
	public List<Pgdr_price> StringFromJsonPrice(String jsondata)
	{     
		Type listType = new TypeToken<List<Pgdr_price>>(){}.getType();
		Gson gson=new Gson();
		ArrayList<Pgdr_price> list=gson.fromJson(jsondata, listType);
		return list;

	}
}
