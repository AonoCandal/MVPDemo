package com.aono.networklib.fun;


import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Aono on 2018/3/23.
 */

public class StringToJSONObjectFun<String> implements Function<String,JSONObject> {


	@Override
	public JSONObject apply(@NonNull String str) throws Exception {
		JSONObject object = null;
		try{
			object = new JSONObject(str.toString());
		}catch (JSONException e){
			e.printStackTrace();
		}
		return object;
	}
}
