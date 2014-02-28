package it.skand.tasker.resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Weather {
		
	@SuppressWarnings("unused")
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	
	public Weather(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
		try {
			jsonArray=jsonObject.getJSONArray("list");
		} catch (JSONException e) {
			jsonArray=null;
			e.printStackTrace();
		}
	}
	
	public String getCityName() {
		String result="";
		try {
			result = ((JSONObject) jsonArray.get(0)).getString("name");
		} catch (JSONException e) {
			Log.e("tasker", e.getMessage());
		}
		return result;
	}
	
	public double getTemperatura() {
		double result=0;
		try {
			result = ((JSONObject) jsonArray.get(0)).getJSONObject("main").getDouble("temp")-273.15;
		} catch (JSONException e) {
			Log.e("tasker", e.getMessage());
		}
		return result;
	}
	
	public double getClouds() {
		double result = 0;
		try {
			result = ((JSONObject) jsonArray.get(0)).getJSONObject("clouds").getDouble("all");
		} catch (JSONException e) {
			Log.e("asdr", e.getMessage());
		}
		return result;
	}
	public double getRain() {
		double result = 0;
		try {
			result = ((JSONObject) jsonArray.get(0)).getJSONObject("rain").getDouble("3h");
		} catch (JSONException e) {
			Log.e("asdfasd", e.getMessage());
		}
		return result;
	}
}
