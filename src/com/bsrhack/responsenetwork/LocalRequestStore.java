package com.bsrhack.responsenetwork;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class LocalRequestStore {

	public static List<Request> getRequests(Context context) {
		ArrayList<Request> ret = new ArrayList<Request>();
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		String jsonString = preferences.getString("requests", "");
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray requests = json.getJSONArray("requests");
			for (int i = 0; i < requests.length(); i++) {
				JSONObject obj = requests.getJSONObject(i);
				ret.add(new Request(obj));
			}
			return ret;
		} catch (JSONException e) {
			e.printStackTrace();
			return new ArrayList<Request>();
		}
	}

	public static void putRequest(Context context, Request request) {
		try {
			List<Request> requests = getRequests(context);
			requests.add(request);
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < requests.size(); i++) {
				jsonArray.put(requests.get(i).toJSON());
			}
			JSONObject json = new JSONObject();
			json.put("requests", jsonArray);
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			Editor edit = preferences.edit();
			edit.putString("requests", json.toString());
			edit.commit();		
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearRequests(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = preferences.edit();
		edit.remove("requests");
		edit.commit();
	}
}