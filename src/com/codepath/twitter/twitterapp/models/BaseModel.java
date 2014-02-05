package com.codepath.twitter.twitterapp.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseModel implements Serializable {

	public JSONObject jsonObject;
	private static final long serialVersionUID = 5177222050535318633L;
	
	public String getJSONString(){
		return jsonObject.toString();
	}
	
	protected String getString(String name){
		try{
			return jsonObject.getString(name);
		} catch(JSONException je){
			je.printStackTrace();
			return null;
		}
	}
	
	protected long getLong(String name){
		try{
			return jsonObject.getLong(name);
		} catch(JSONException je){
			je.printStackTrace();
			return 0;
		}
	}
	
	protected int getInt(String name){
		try{
			return jsonObject.getInt(name);
		} catch(JSONException je){
			je.printStackTrace();
			return 0;
		}
	}
	
	protected double getDouble(String name){
		try{
			return jsonObject.getDouble(name);
		} catch(JSONException je){
			je.printStackTrace();
			return 0;
		}
	}
	
	protected boolean getBoolean(String name){
		try{
			return jsonObject.getBoolean(name);
		} catch(JSONException je){
			je.printStackTrace();
			return false;
		}
	}
	
}
