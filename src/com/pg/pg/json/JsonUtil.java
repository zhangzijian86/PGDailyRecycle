package com.pg.pg.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pg.pg.bean.Pgdr_user;


public class JsonUtil {
	public List<Pgdr_user> StringFromJson (String jsondata)
	{     
		Type listType = new TypeToken<List<Pgdr_user>>(){}.getType();
		Gson gson=new Gson();
		ArrayList<Pgdr_user> list=gson.fromJson(jsondata, listType);
		return list;

	}
}
